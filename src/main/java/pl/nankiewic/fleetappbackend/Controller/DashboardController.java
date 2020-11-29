package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.Service.DashboardService;

import java.sql.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/dashboard")
public class DashboardController {
        DashboardService dashboardService;
        @Autowired
        public DashboardController(DashboardService dashboardService) {
                this.dashboardService = dashboardService;
        }
        /*
        WYKRESY DLA FLOTY
         */
        /*
        koszty z podzaialem na wszystkie elementy
         */
        @GetMapping("/cost_by_category")
        public Iterable<ChartDataRespondDTO> costByCategories(@RequestParam (name = "b") String beginS,
                                                              @RequestParam (name = "e") String endS,
                                                              Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.fleetCostByCategory(userDetails.getUsername(), begin, end);
        }
        /*
        koszt paliwa ze względu na pojazd
         */
        @GetMapping("/fuel_cost_by_vehicle")
        public Iterable<ChartDataRespondDTO> fuelCostByVehicle(@RequestParam (name = "b") String beginS,
                                                               @RequestParam (name = "e") String endS,
                                                               Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.sumRefuelingByVehicle(userDetails.getUsername(), begin, end);

        }
        /*
        długość tras ze względu na pojazd
         */
        @GetMapping("/use_cost_by_vehicle")
        public Iterable<ChartDataRespondDTO> useCostByVehicle(@RequestParam (name = "b") String beginS,
                                                              @RequestParam (name = "e") String endS,
                                                              Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.sumUseByVehicle(userDetails.getUsername(), begin, end);
        }
         /*
        WYKRESY DLA POJAZDÓW
         */
        /*
        Wykres numer 1
        name: data tankowania
        value: koszt danego tankowania
         */
         @GetMapping("/cost_fuel_by_vehicle")
         public Iterable<ChartDataRespondDTO> fuelCostByVehicle(@RequestParam (name = "v") String vehicle,
                                                                @RequestParam (name = "b") String beginS,
                                                                @RequestParam (name = "e") String endS,
                                                                Authentication authentication){
                 Date begin=Date.valueOf(beginS);
                 Date end=Date.valueOf(endS);
                 UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                 return dashboardService.fuelCostByVehicleAndData(userDetails.getUsername(), begin, end, vehicle);
         }
        /*
        Wykres numer 2
        name: data przebiegu
        value: długość tego przebiegu
          */
        @GetMapping("/trip_by_vehicle")
        public Iterable<ChartDataRespondDTO> distanceByVehicle(@RequestParam (name = "v") String vehicle,
                                                               @RequestParam (name = "b") String beginS,
                                                               @RequestParam (name = "e") String endS,
                                                               Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.distanceByVehicleAndData(userDetails.getUsername(), begin, end, vehicle);
        }
        /*
        Wykres numer 3
        koszty utrzymania pojazdu całościowe tak jak było dla foloty tylko dla
        pojedyńczego pojazdu (ubezpiecznia, naprawy itd) to ten wykres kołowy
         */
        @GetMapping("/vehicle_cost_by_category")
        public Iterable<ChartDataRespondDTO> vehicleCostByCategory(@RequestParam (name = "v") String vehicle,
                                                                   @RequestParam (name = "b") String beginS,
                                                                   @RequestParam (name = "e") String endS,
                                                                   Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.vehicleCostByCategory(userDetails.getUsername(), vehicle, begin, end);
        }
        /*
        Wykres numer 4
        name: miejski, pozamiejski, mieszny
        value: długość tego przebiegu
         */
        @GetMapping("/vehicle_trip_by_trip_type")
        public Iterable<ChartDataRespondDTO> vehicleTripByTripType(@RequestParam (name = "v") String vehicle,
                                                                   @RequestParam (name = "b") String beginS,
                                                                   @RequestParam (name = "e") String endS,
                                                                   Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.distanceByVehicleAndDataAndUseType(userDetails.getUsername(), begin, end, vehicle);
        }
}
