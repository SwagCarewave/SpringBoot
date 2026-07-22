package com.carewave.domain.room.controller;

import com.carewave.domain.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository.deleteAll();
    }

    @Test
    void createRoom() throws Exception {
        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "roomNumber": "101",
                                  "description": "First floor room"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.roomNumber").value("101"))
                .andExpect(jsonPath("$.result.description").value("First floor room"))
                .andExpect(jsonPath("$.result.status").value("ACTIVE"))
                .andExpect(jsonPath("$.result.assignedDeviceCount").value(0));
    }

    @Test
    void createRoomRejectsDuplicateRoomNumber() throws Exception {
        createRoom("101", "Original room");

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "roomNumber": "101",
                                  "description": "Duplicate room"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("ROOM_409_1"));
    }

    @Test
    void getRoomsReturnsOnlyActiveRoomsByDefault() throws Exception {
        createRoom("101", "Active room");
        Long inactiveRoomId = createRoom("102", "Inactive room");
        updateRoomStatus(inactiveRoomId, "INACTIVE");

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(1)))
                .andExpect(jsonPath("$.result[0].roomNumber").value("101"))
                .andExpect(jsonPath("$.result[0].status").value("ACTIVE"));
    }

    @Test
    void getRoomsCanIncludeInactiveRooms() throws Exception {
        createRoom("101", "Active room");
        Long inactiveRoomId = createRoom("102", "Inactive room");
        updateRoomStatus(inactiveRoomId, "INACTIVE");

        mockMvc.perform(get("/api/rooms")
                        .param("activeOnly", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].roomNumber").value("101"))
                .andExpect(jsonPath("$.result[1].roomNumber").value("102"));
    }

    @Test
    void getRoom() throws Exception {
        Long roomId = createRoom("101", "Detail room");

        mockMvc.perform(get("/api/rooms/{roomId}", roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.roomId").value(roomId))
                .andExpect(jsonPath("$.result.roomNumber").value("101"))
                .andExpect(jsonPath("$.result.assignedDeviceCount").value(0));
    }

    @Test
    void updateRoom() throws Exception {
        Long roomId = createRoom("101", "Before");

        mockMvc.perform(patch("/api/rooms/{roomId}", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "roomNumber": "201",
                                  "description": "After"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.roomNumber").value("201"))
                .andExpect(jsonPath("$.result.description").value("After"));
    }

    @Test
    void updateRoomRejectsDuplicateRoomNumber() throws Exception {
        createRoom("101", "Original");
        Long roomId = createRoom("102", "Target");

        mockMvc.perform(patch("/api/rooms/{roomId}", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "roomNumber": "101",
                                  "description": "Duplicate"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("ROOM_409_1"));
    }

    @Test
    void updateRoomStatus() throws Exception {
        Long roomId = createRoom("101", "Status room");

        mockMvc.perform(patch("/api/rooms/{roomId}/status", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": "INACTIVE"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.status").value("INACTIVE"));

        mockMvc.perform(patch("/api/rooms/{roomId}/status", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": "ACTIVE"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.status").value("ACTIVE"));
    }

    @Test
    void getRoomReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/rooms/{roomId}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ROOM_404_1"));
    }

    private Long createRoom(String roomNumber, String description) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "roomNumber": "%s",
                                  "description": "%s"
                                }
                                """.formatted(roomNumber, description)))
                .andExpect(status().isOk())
                .andReturn();

        return Long.valueOf(JsonPathValue.read(result.getResponse().getContentAsString(), "$.result.roomId"));
    }

    private void updateRoomStatus(Long roomId, String status) throws Exception {
        mockMvc.perform(patch("/api/rooms/{roomId}/status", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": "%s"
                                }
                                """.formatted(status)))
                .andExpect(status().isOk());
    }

    private static class JsonPathValue {

        private static String read(String json, String path) {
            return com.jayway.jsonpath.JsonPath.read(json, path).toString();
        }
    }
}
