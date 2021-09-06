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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.nankiewic.fleetappbackend.DTO.UseDTO;
import pl.nankiewic.fleetappbackend.Security.JWTAuthenticationEntryPoint;
import pl.nankiewic.fleetappbackend.Security.JWTokenUtility;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.UseService;

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

        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/use")
                .content(asJsonString(useDTO))
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
                .vehicleId(1L)
                .trip(Short.parseShort("23"))
                .tripType("tripppii")
                .description("ghg")
                .tripDate(LocalDate.now())
                .build();

        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/use")
                .content(asJsonString(useDTO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //when
        verify(useService, times(1)).updateVehicleUse(any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_use_by_vehicle_id() throws Exception {
        //given
        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use/v/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByVehicle(any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_get_vehicle_use_by_id() throws Exception {
        //given
        when(checkService.accessToUse(any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByUseId(any());
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
        when(checkService.accessToVehicle(any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/use/list?u=1&v=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).getUseByUserAndVehicle(any(), any());
    }

    @Test
    @WithMockUser(roles = {"SUPERUSER"})
    void should_delete_vehicle_use_by_id() throws Exception {
        //given
        when(checkService.accessToUse(any(), any())).thenReturn(true);
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/use/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
        //then
        verify(useService, times(1)).deleteUseById(any());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}