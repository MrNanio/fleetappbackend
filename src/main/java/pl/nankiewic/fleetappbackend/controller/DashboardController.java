package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.dto.chart.RefuelingChartWithVehicleDataView;
import pl.nankiewic.fleetappbackend.dto.chart.RefuelingChartWithDateView;
import pl.nankiewic.fleetappbackend.service.DashboardService;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/cost_by_category")
    public List<ChartDataRespondDTO> getCostByCategories(@RequestParam(name = "b") LocalDate beginS,
                                                         @RequestParam(name = "e") LocalDate endS) {
        return dashboardService.getSummaryCostByVehicleOwner(beginS, endS);
    }

    @GetMapping("/vehicle_cost_by_category")
    public List<ChartDataRespondDTO> getVehicleCostByCategory(@RequestParam(name = "v") Long vehicleId,
                                                              @RequestParam(name = "b") LocalDate beginS,
                                                              @RequestParam(name = "e") LocalDate endS) {
        return dashboardService.getSummaryCostByVehicleId(vehicleId, beginS, endS);
    }


    @GetMapping("/fuel_cost_by_vehicle")
    public List<RefuelingChartWithVehicleDataView> getFuelCostByVehicle(@RequestParam(name = "b") LocalDate beginS,
                                                                        @RequestParam(name = "e") LocalDate endS) {
        return dashboardService.getSummaryRefuelingByVehicle(beginS, endS);
    }

    @GetMapping("/cost_fuel_by_vehicle")
    public List<RefuelingChartWithDateView> fuelCostByVehicle(@RequestParam(name = "v") Long vehicleId,
                                                              @RequestParam(name = "b") LocalDate beginS,
                                                              @RequestParam(name = "e") LocalDate endS) {
        return dashboardService.getFuelCostByVehicleAndData(vehicleId, beginS, endS);
    }

    @GetMapping("fuel_cost_by_user")
    public List<RefuelingChartWithVehicleDataView> getFuelCostByUser(@RequestParam(name = "u") Long userId,
                                                                     @RequestParam(name = "b") LocalDate beginS,
                                                                     @RequestParam(name = "e") LocalDate endS) {
        return dashboardService.getFuelCostByVehicleAndDataAndUser(userId, beginS, endS);
    }

    @GetMapping("fuel_cost_by_login_user")
    public List<RefuelingChartWithVehicleDataView> getFuelCostBySuperUser(@RequestParam(name = "b") LocalDate beginS,
                                                                          @RequestParam(name = "e") LocalDate endS) {
        return dashboardService.getFuelCostByVehicleAndDataByLoginUser(beginS, endS);
    }

    //    @GetMapping("/vehicle_trip_by_trip_type")
//    public List<ChartDataRespondDTO> getVehicleTripByTripType(@RequestParam(name = "v") Long vehicle,
//                                                              @RequestParam(name = "b") LocalDate beginS,
//                                                              @RequestParam(name = "e") LocalDate endS) {
//        return dashboardService.getDistanceByVehicleAndDataAndUseType(vehicle, beginS, endS);
//    }

    //    @GetMapping("trip_by_login_user")
//    public List<ChartDataRespondDTO> getVehicleTripBySuperUser(@RequestParam(name = "b") LocalDate beginS,
//                                                               @RequestParam(name = "e") LocalDate endS) {
//        return dashboardService.getDistanceByVehicleAndDataByLoginUser(beginS, endS);
//    }

    //    @GetMapping("trip_by_user")
//    public List<ChartDataRespondDTO> getVehicleTripByUser(@RequestParam(name = "u") Long user,
//                                                          @RequestParam(name = "b") LocalDate beginS,
//                                                          @RequestParam(name = "e") LocalDate endS) {
//
//        return dashboardService.getDistanceByVehicleAndDataAndUser(user, beginS, endS);
//    }


//    @GetMapping("/trip_by_vehicle")
//    public List<ChartDataRespondDTO> getDistanceByVehicle(@RequestParam(name = "v") Long vehicleId,
//                                                          @RequestParam(name = "b") LocalDate beginS,
//                                                          @RequestParam(name = "e") LocalDate endS) {
//        return dashboardService.getDistanceByVehicleAndData(beginS, endS, vehicleId);
//    }

    //    @GetMapping("/use_cost_by_vehicle")
//    public List<ChartDataRespondDTO> getUseCostByVehicle(@RequestParam(name = "b") LocalDate beginS,
//                                                         @RequestParam(name = "e") LocalDate endS) {
//        return dashboardService.getSummaryUseByVehicle(beginS, endS);
//    }
//
//    @GetMapping("/use_number_by_vehicle")
//    public List<ChartDataRespondDTO> getUseByVehicle(@RequestParam(name = "b") LocalDate beginS,
//                                                     @RequestParam(name = "e") LocalDate endS) {
//        return dashboardService.getNumberOfUsesByVehicle(beginS, endS);
//    }

}