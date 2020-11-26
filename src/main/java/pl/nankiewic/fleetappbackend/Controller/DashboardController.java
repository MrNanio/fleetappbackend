package pl.nankiewic.fleetappbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.DTO.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.Service.DashboardService;

import java.sql.Date;
import java.time.LocalDate;

/*
      -koszty całościowe co miesiać między datami

      StatsTerm {
          vehicleId: string;
          startDate: string;
          endDate: string;
      }

      StatsElement {
          value: number;
          name: string;
      }
*/
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@PreAuthorize("hasRole('ADMIN')")
//@RequestMapping("/admin")
public class DashboardController {
        @Autowired
        DashboardService dashboardService;

        /*
        koszty z podzaialem na wszystkie elementy

        odpowiedz:

         */
        @GetMapping("/dashboard")
        public Iterable<ChartDataRespondDTO> costByCategories(@RequestParam (name = "b") String beginS,
                                                              @RequestParam (name = "e") String endS,
                                                              Authentication authentication){
                Date begin=Date.valueOf(beginS);
                Date end=Date.valueOf(endS);
                //LocalDate begin= LocalDate.parse(beginS);
               // LocalDate end= LocalDate.parse(endS);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return dashboardService.sumCategory(userDetails.getUsername(), begin, end);
        }
        /*
        koszty całościowe co miesiać między datami
         */
        public void allCostByMonth(){

        }

        /*
        koszt paliwa ze względu na pojazd
         */
        public void fuelCostByVehicle(){

        }
        /*
        długość tras ze względu na pojazd
         */
        public void useCostByVehicle(){

        }

}
