package pl.nankiewic.fleetappbackend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.nankiewic.fleetappbackend.DTO.Vehicle.VehicleRequestResponseDTO;

import pl.nankiewic.fleetappbackend.Security.JWTAuthenticationEntryPoint;
import pl.nankiewic.fleetappbackend.Security.JWTokenUtility;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.VehicleService;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private CheckService checkService;

    @MockBean
    private JWTAuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    JWTokenUtility tokenUtility;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    UserDetailsService userDetailsService;

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_add_vehicle() throws Exception {
        //given
        VehicleRequestResponseDTO vehicleRequestResponseDTO = VehicleRequestResponseDTO.builder()
                .id(1L)
                .make("SKODA")
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType("ON")
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus("ACTIVE")
                .build();
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
                .content(asJsonString(vehicleRequestResponseDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //when
        verify(vehicleService, times(1)).createVehicle(any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_update_vehicle() throws Exception {
        //given
        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        VehicleRequestResponseDTO vehicleRequestResponseDTO = VehicleRequestResponseDTO.builder()
                .id(1L)
                .make("SKODA")
                .model("FABIA")
                .year("2009")
                .color("BIAŁY")
                .mileage("209000")
                .vinNumber("GTFRED4567DEY65TG")
                .vehicleRegistrationNumber("LU7654D")
                .fuelType("ON")
                .cityFuelConsumption(BigDecimal.valueOf(5.6))
                .countryFuelConsumption(BigDecimal.valueOf(3.6))
                .averageFuelConsumption(BigDecimal.valueOf(4.6))
                .vehicleStatus("ACTIVE")
                .build();



        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/vehicle")
                .content(asJsonString(vehicleRequestResponseDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
        verify(vehicleService, times(1)).updateVehicle(any());

    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicles_by_vehicle_owner() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(vehicleService, times(1)).getVehiclesDataByUser(any());
    }

    @Test
    @WithMockUser
    void should_not_get_vehicles_by_vehicle_owner() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(403));
        //then
        verify(vehicleService, times(0)).getVehiclesDataByUser(any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_by_id() throws Exception {
        //given
        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        verify(vehicleService, times(1)).getVehicleDataById(any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_delete_vehicle_by_id() throws Exception {
        //given
        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        verify(vehicleService, times(1)).deleteVehicleById(any());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
