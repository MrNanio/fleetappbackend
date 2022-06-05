package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.service.DashboardService;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/dashboard")
public class DashboardController {

        private final DashboardService dashboardService;

        @GetMapping("/cost_by_category")
        public Iterable<ChartDataRespondDTO> getCostByCategories(@RequestParam(name = "b") String beginS,
                                                                 @RequestParam(name = "e") String endS,
                                                                 Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getFleetCostByCategory(userDetails.getUsername(), beginS, endS);
        }

        @GetMapping("/fuel_cost_by_vehicle")
        public Iterable<ChartDataRespondDTO> getFuelCostByVehicle(@RequestParam(name = "b") String beginS,
                                                                  @RequestParam(name = "e") String endS,
                                                                  Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getSummaryRefuelingByVehicle(userDetails.getUsername(), beginS, endS);
        }

        @GetMapping("/use_cost_by_vehicle")
        public Iterable<ChartDataRespondDTO> getUseCostByVehicle(@RequestParam(name = "b") String beginS,
                                                                 @RequestParam(name = "e") String endS,
                                                                 Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getSummaryUseByVehicle(userDetails.getUsername(), beginS, endS);
        }

        @GetMapping("/use_number_by_vehicle")
        public Iterable<ChartDataRespondDTO> getUseByVehicle(@RequestParam(name = "b") String beginS,
                                                             @RequestParam(name = "e") String endS,
                                                             Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getNumberOfUsesByVehicle(userDetails.getUsername(), beginS, endS);
        }

        @GetMapping("/cost_fuel_by_vehicle")
        public Iterable<ChartDataRespondDTO> fuelCostByVehicle(@RequestParam(name = "v") String vehicle,
                                                               @RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS,
                                                               Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getFuelCostByVehicleAndData(vehicle, beginS, endS, userDetails.getUsername());
        }

        @GetMapping("/trip_by_vehicle")
        public Iterable<ChartDataRespondDTO> getDistanceByVehicle(@RequestParam(name = "v") String vehicle,
                                                                  @RequestParam(name = "b") String beginS,
                                                                  @RequestParam(name = "e") String endS,
                                                                  Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getDistanceByVehicleAndData(beginS, endS, vehicle, userDetails.getUsername());
        }

        @GetMapping("/vehicle_cost_by_category")
        public Iterable<ChartDataRespondDTO> getVehicleCostByCategory(@RequestParam(name = "v") String vehicle,
                                                                      @RequestParam(name = "b") String beginS,
                                                                      @RequestParam(name = "e") String endS,
                                                                      Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getVehicleCostByCategory(vehicle, beginS, endS, userDetails.getUsername());
        }

        @GetMapping("/vehicle_trip_by_trip_type")
        public Iterable<ChartDataRespondDTO> getVehicleTripByTripType(@RequestParam(name = "v") String vehicle,
                                                                      @RequestParam(name = "b") String beginS,
                                                                      @RequestParam(name = "e") String endS,
                                                                      Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getDistanceByVehicleAndDataAndUseType(vehicle, beginS, endS, userDetails.getUsername());
        }

        @GetMapping("trip_by_user")
        public Iterable<ChartDataRespondDTO> getVehicleTripByUser(@RequestParam(name = "u") String user,
                                                                  @RequestParam(name = "b") String beginS,
                                                                  @RequestParam(name = "e") String endS) {

                return dashboardService.getDistanceByVehicleAndDataAndUser(user, beginS, endS);
        }

        @GetMapping("fuel_cost_by_user")//new
        public Iterable<ChartDataRespondDTO> getFuelCostByUser(@RequestParam(name = "u") String user,
                                                               @RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS) {

                return dashboardService.getFuelCostByVehicleAndDataAndUser(user, beginS, endS);
        }

        @GetMapping("trip_by_login_user")
        public Iterable<ChartDataRespondDTO> getVehicleTripBySuperUser(@RequestParam(name = "b") String beginS,
                                                                       @RequestParam(name = "e") String endS,
                                                                       Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getDistanceByVehicleAndDataByLoginUser(userDetails.getUsername(), beginS, endS);
        }

        @GetMapping("fuel_cost_by_login_user")
        public Iterable<ChartDataRespondDTO> getFuelCostBySuperUser(@RequestParam(name = "b") String beginS,
                                                                    @RequestParam(name = "e") String endS,
                                                                    Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.getFuelCostByVehicleAndDataByLoginUser(userDetails.getUsername(), beginS, endS);
        }
}
