package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.Exception.PermissionDeniedException;
import pl.nankiewic.fleetappbackend.Service.CheckService;
import pl.nankiewic.fleetappbackend.Service.DashboardService;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/dashboard")
public class DashboardController {
        private final DashboardService dashboardService;
        private final CheckService checkService;

        @Autowired
        public DashboardController(DashboardService dashboardService, CheckService checkService) {
                this.dashboardService = dashboardService;
                this.checkService = checkService;
        }

        @GetMapping("/cost_by_category")
        public Iterable<ChartDataRespondDTO> costByCategories(@RequestParam(name = "b") String beginS,
                                                              @RequestParam(name = "e") String endS,
                                                              Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.fleetCostByCategory(userDetails.getUsername(), begin, end);
        }

        @GetMapping("/fuel_cost_by_vehicle")
        public Iterable<ChartDataRespondDTO> fuelCostByVehicle(@RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS,
                                                               Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.sumRefuelingByVehicle(userDetails.getUsername(), begin, end);

        }

        @GetMapping("/use_cost_by_vehicle")
        public Iterable<ChartDataRespondDTO> useCostByVehicle(@RequestParam(name = "b") String beginS,
                                                              @RequestParam(name = "e") String endS,
                                                              Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.sumUseByVehicle(userDetails.getUsername(), begin, end);
        }

        @GetMapping("/use_number_by_vehicle")//new
        public Iterable<ChartDataRespondDTO> useByVehicle(@RequestParam(name = "b") String beginS,
                                                          @RequestParam(name = "e") String endS,
                                                          Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.numberOfUsesByVehicle(userDetails.getUsername(), begin, end);
        }

        @GetMapping("/cost_fuel_by_vehicle")
        public Iterable<ChartDataRespondDTO> fuelCostByVehicle(@RequestParam(name = "v") String vehicle,
                                                               @RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS,
                                                               Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (checkService.accessToVehicle(userDetails.getUsername(), Long.parseLong(vehicle))) {
                        return dashboardService.fuelCostByVehicleAndData(begin, end, vehicle);
                } else throw new PermissionDeniedException("Odmowa dostępu");

        }

        @GetMapping("/trip_by_vehicle")
        public Iterable<ChartDataRespondDTO> distanceByVehicle(@RequestParam(name = "v") String vehicle,
                                                               @RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS,
                                                               Authentication authentication) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (checkService.accessToVehicle(userDetails.getUsername(), Long.parseLong(vehicle))) {
                        return dashboardService.distanceByVehicleAndData(beginS, endS, vehicle);
                } else throw new PermissionDeniedException("Odmowa dostępu");

        }

        @GetMapping("/vehicle_cost_by_category")
        public Iterable<ChartDataRespondDTO> vehicleCostByCategory(@RequestParam(name = "v") String vehicle,
                                                                   @RequestParam(name = "b") String beginS,
                                                                   @RequestParam(name = "e") String endS,
                                                                   Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (checkService.accessToVehicle(userDetails.getUsername(), Long.parseLong(vehicle))) {
                        return dashboardService.vehicleCostByCategory(vehicle, begin, end);
                } else throw new PermissionDeniedException("Odmowa dostępu");
        }

        @GetMapping("/vehicle_trip_by_trip_type")
        public Iterable<ChartDataRespondDTO> vehicleTripByTripType(@RequestParam(name = "v") String vehicle,
                                                                   @RequestParam(name = "b") String beginS,
                                                                   @RequestParam(name = "e") String endS,
                                                                   Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (checkService.accessToVehicle(userDetails.getUsername(), Long.parseLong(vehicle))) {
                        return dashboardService.distanceByVehicleAndDataAndUseType(begin, end, vehicle);
                } else throw new PermissionDeniedException("Odmowa dostępu");

        }

        @GetMapping("trip_by_user")
        public Iterable<ChartDataRespondDTO> vehicleTripByUser(@RequestParam(name = "u") String user,
                                                               @RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                return dashboardService.distanceByVehicleAndDataAndUser(begin, end, user);
        }

        @GetMapping("fuel_cost_by_user")//new
        public Iterable<ChartDataRespondDTO> fuelCostByUser(@RequestParam(name = "u") String user,
                                                            @RequestParam(name = "b") String beginS,
                                                            @RequestParam(name = "e") String endS) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                return dashboardService.fuelCostByVehicleAndDataAndUser(begin, end, user);
        }

        @GetMapping("trip_by_login_user")
        public Iterable<ChartDataRespondDTO> vehicleTripBySuperUser(@RequestParam(name = "b") String beginS,
                                                                    @RequestParam(name = "e") String endS,
                                                                    Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.distanceByVehicleAndDataByLoginUser(userDetails.getUsername(), begin, end);
        }

        /*
        name: model | numer rejestracyjny
        value koszty paliwa dodane prez tego menagera do danego pojazdu
         */
        @GetMapping("fuel_cost_by_login_user")
        public Iterable<ChartDataRespondDTO> fuelCostBySuperUser(@RequestParam(name = "b") String beginS,
                                                                 @RequestParam(name = "e") String endS,
                                                                 Authentication authentication) {
                LocalDate begin = LocalDate.parse(beginS);
                LocalDate end = LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.fuelCostByVehicleAndDataByLoginUser(userDetails.getUsername(), begin, end);
        }
}
