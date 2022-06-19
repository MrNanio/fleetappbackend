package pl.nankiewic.fleetappbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.config.security.JWTAuthenticationEntryPoint;
import pl.nankiewic.fleetappbackend.config.security.JWTokenUtility;
import pl.nankiewic.fleetappbackend.service.UseService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UseController.class)
class UseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UseService useService;

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
    void should_add_vehicle_use() throws Exception {
        //given
        UseDTO useDTO = UseDTO.builder()
                .id(12L)
                .userId(1L)
                .vehicleId(1L)
                .trip(Short.parseShort("23"))
                .tripType("tripppiii")
                .description("ghg")
                .tripDate(LocalDate.now())
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/use")
                .content(objectMapper.writeValueAsString(useDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //when
        verify(useService, times(1)).createVehicleUse(any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_update_vehicle_use() throws Exception {
        //given
        UseDTO useDTO = UseDTO.builder()
                .id(1L)
                .userId(1L)
                .vehicleId(2L)
                .trip(Short.parseShort("23"))
                .tripType("miejski")
                .description("ghghuhuhuhu")
                .tripDate(LocalDate.of(2021, 12, 12))
                .build();

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/use")
                .content(objectMapper.writeValueAsString(useDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //when
        verify(useService, times(1)).updateVehicleUse(any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_use_by_vehicle_id() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use/v/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByVehicle(any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_use_by_id() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByUseId(any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_use_by_user() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByUser(any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_use_by_user_id_and_vehicle_id() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use/list?u=1&v=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByUserAndVehicle(any(), any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_delete_vehicle_use_by_id() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/use/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).deleteUseById(any(), any());
    }

}