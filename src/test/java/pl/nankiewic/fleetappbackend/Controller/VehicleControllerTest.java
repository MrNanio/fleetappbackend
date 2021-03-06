package pl.nankiewic.fleetappbackend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.nankiewic.fleetappbackend.DTO.VehicleDTO;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    @WithMockUser(password = "admin1234", username = "superuser@superuser.xd", roles = {"SUPERUSER"})
    public void should_add_vehicle() throws Exception {

        VehicleDTO vehicleDTO=new VehicleDTO(33L,
                "FORD", "KUGA", "2020", "BIAŁY", "20200", "12345678998765432",
                "LU3421E", "ON", "12",
                "10", "11", "Aktywny");

        mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
                .content(asJsonString(vehicleDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "superuser@superuser.xd", roles = {"SUPERUSER"})
    void should_get_vehicle_by_id() throws Exception {

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/vehicle/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andReturn();

        VehicleDTO vehicleDTO=objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VehicleDTO.class);
        assertThat(vehicleDTO).isNotNull();
        assertThat(vehicleDTO.getId()).isEqualTo(1L);
        assertThat(vehicleDTO);
    }
    @Test
    @WithMockUser(password = "admin1234", username = "superuser@superuser.xd", roles = {"SUPERUSER"})
    void should_get_vehicles_by_vehicle_owner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
                //.andExpect(jsonPath("$", hasSize(31)))

    }
    @Test
    @WithMockUser(password = "admin1234", username = "superuser@superuser.xd")
    void should_not_get_vehicles_by_vehicle_owner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(403));
    }
    @Test
    @WithMockUser(password = "admin1234", username = "superuser@superuser.xd", roles = {"SUPERUSER"})
    public void should_update_vehicle() throws Exception {

        VehicleDTO vehicleDTO=new VehicleDTO(33L,
                "FORD", "KUGAa", "2020", "BIAŁY", "20200", "12345678998765432",
                "LU3421E", "ON", "12",
                "10", "11", "Aktywny");

        mockMvc.perform(MockMvcRequestBuilders.put("/vehicle")
                .content(asJsonString(vehicleDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
    @Test
    @WithMockUser(password = "admin1234", username = "superuser@superuser.xd", roles = {"SUPERUSER"})
    public void should_delete_vehicle() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
    }

}
