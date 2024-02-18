package pl.nankiewic.fleetappbackend.controller;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static pl.nankiewic.fleetappbackend.util.vehicle.VehicleRequestResponseDTOFactory.buildVehicleRequestResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehicleControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void should_add_vehicle() throws Exception {
//        //given
//        var vehicle = objectMapper.writeValueAsString(buildVehicleRequestResponseDTO());
//        var user = buildUser();
//        var savedUser = userRepository.save(user);
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().getRole().name()));
//        var jwtUser = CustomUserDetails.builder()
//                .username(savedUser.getEmail())
//                .id(savedUser.getId())
//                .isEnabled(true)
//                .authorities(authorities)
//                .build();
//
//        //then
//        mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
//                .content(vehicle)
//                .header("Authorization", "Bearer " + JWTokenUtility.generateJwtToken(jwtUser))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void should_update_vehicle() throws Exception {
//        //given
//        var savedUser = userRepository.save(buildUser());
//        var savedVehicle = vehicleRepository.save(buildVehicle(savedUser.getId()));
//        var vehicle = objectMapper.writeValueAsString(buildVehicleRequestResponseDTO(savedVehicle.getId()));
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().getRole().name()));
//        var jwtUser = CustomUserDetails.builder()
//                .username(savedUser.getEmail())
//                .id(savedUser.getId())
//                .isEnabled(true)
//                .authorities(authorities)
//                .build();
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.put("/vehicle")
//                .content(vehicle)
//                .header("Authorization", "Bearer " + JWTokenUtility.generateJwtToken(jwtUser))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void should_get_vehicles_by_vehicle_owner() throws Exception {
//        //given
//        var savedUser = userRepository.save(buildUser());
//        vehicleRepository.save(buildVehicle(savedUser.getId()));
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().getRole().name()));
//        var jwtUser = CustomUserDetails.builder()
//                .username(savedUser.getEmail())
//                .id(savedUser.getId())
//                .isEnabled(true)
//                .authorities(authorities)
//                .build();
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle")
//                .header("Authorization", "Bearer " + JWTokenUtility.generateJwtToken(jwtUser)))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().is(200));
//    }
//
//    @Test
//    void should_not_get_vehicles_by_vehicle_owner() throws Exception {
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().is(401));
//    }
//
//    @Test
//    void should_get_vehicle_by_id() throws Exception {
//        //given
//        var savedUser = userRepository.save(buildUser());
//        var savedVehicle = vehicleRepository.save(buildVehicle(savedUser.getId()));
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().getRole().name()));
//        var jwtUser = CustomUserDetails.builder()
//                .username(savedUser.getEmail())
//                .id(savedUser.getId())
//                .isEnabled(true)
//                .authorities(authorities)
//                .build();
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle/" + savedVehicle.getId())
//                .header("Authorization", "Bearer " + JWTokenUtility.generateJwtToken(jwtUser)))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().is(200))
//                .andReturn();
//    }
//
//    @Test
//    void should_delete_vehicle_by_id() throws Exception {
//        //given
//        var savedUser = userRepository.save(buildUser());
//        var savedVehicle = vehicleRepository.save(buildVehicle(savedUser.getId()));
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().getRole().name()));
//        var jwtUser = CustomUserDetails.builder()
//                .username(savedUser.getEmail())
//                .id(savedUser.getId())
//                .isEnabled(true)
//                .authorities(authorities)
//                .build();
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/" + savedVehicle.getId())
//                .header("Authorization", "Bearer " + JWTokenUtility.generateJwtToken(jwtUser)))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().is(200))
//                .andReturn();
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        vehicleRepository.deleteAll();
//        userRepository.deleteAll();
//    }

}