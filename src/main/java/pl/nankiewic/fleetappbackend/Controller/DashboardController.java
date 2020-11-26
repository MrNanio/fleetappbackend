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
        koszty z podzaialem na wszystkie elementy
         */
        @GetMapping("/cost_by_category")
        public Iterable<ChartDataRespondDTO> costByCategories(@RequestParam (name = "b") String beginS,
                                                              @RequestParam (name = "e") String endS,
                                                              Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.sumCategory(userDetails.getUsername(), begin, end);
        }
        /*
        koszty całościowe co miesiać między datami
         */
       /* public void allCostByMonth(){

        }*/

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

}
