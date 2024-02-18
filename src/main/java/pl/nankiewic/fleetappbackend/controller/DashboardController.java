package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.service.DashboardService;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/cost_by_category")
    public List<ChartDataRespondDTO> getCostByCategories(@RequestParam(name = "b") String beginS,
                                                         @RequestParam(name = "e") String endS) {
        return dashboardService.getFleetCostByCategory(beginS, endS);
    }

    @GetMapping("/fuel_cost_by_vehicle")
    public List<ChartDataRespondDTO> getFuelCostByVehicle(@RequestParam(name = "b") String beginS,
                                                          @RequestParam(name = "e") String endS) {
        return dashboardService.getSummaryRefuelingByVehicle(beginS, endS);
    }

    @GetMapping("/use_cost_by_vehicle")
    public List<ChartDataRespondDTO> getUseCostByVehicle(@RequestParam(name = "b") String beginS,
                                                         @RequestParam(name = "e") String endS) {
        return dashboardService.getSummaryUseByVehicle(beginS, endS);
    }

    @GetMapping("/use_number_by_vehicle")
    public List<ChartDataRespondDTO> getUseByVehicle(@RequestParam(name = "b") String beginS,
                                                     @RequestParam(name = "e") String endS) {
        return dashboardService.getNumberOfUsesByVehicle(beginS, endS);
    }

    @GetMapping("/cost_fuel_by_vehicle")
    public List<ChartDataRespondDTO> fuelCostByVehicle(@RequestParam(name = "v") String vehicle,
                                                       @RequestParam(name = "b") String beginS,
                                                       @RequestParam(name = "e") String endS) {
        return dashboardService.getFuelCostByVehicleAndData(vehicle, beginS, endS);
    }

    @GetMapping("/trip_by_vehicle")
    public List<ChartDataRespondDTO> getDistanceByVehicle(@RequestParam(name = "v") String vehicle,
                                                          @RequestParam(name = "b") String beginS,
                                                          @RequestParam(name = "e") String endS) {
        return dashboardService.getDistanceByVehicleAndData(beginS, endS, vehicle);
    }

    @GetMapping("/vehicle_cost_by_category")
    public List<ChartDataRespondDTO> getVehicleCostByCategory(@RequestParam(name = "v") String vehicle,
                                                              @RequestParam(name = "b") String beginS,
                                                              @RequestParam(name = "e") String endS) {

        return dashboardService.getVehicleCostByCategory(vehicle, beginS, endS);
    }

    @GetMapping("/vehicle_trip_by_trip_type")
    public List<ChartDataRespondDTO> getVehicleTripByTripType(@RequestParam(name = "v") String vehicle,
                                                              @RequestParam(name = "b") String beginS,
                                                              @RequestParam(name = "e") String endS) {
        return dashboardService.getDistanceByVehicleAndDataAndUseType(vehicle, beginS, endS);
    }

    @GetMapping("trip_by_user")
    public List<ChartDataRespondDTO> getVehicleTripByUser(@RequestParam(name = "u") String user,
                                                          @RequestParam(name = "b") String beginS,
                                                          @RequestParam(name = "e") String endS) {

        return dashboardService.getDistanceByVehicleAndDataAndUser(user, beginS, endS);
    }

    @GetMapping("fuel_cost_by_user")//new
    public List<ChartDataRespondDTO> getFuelCostByUser(@RequestParam(name = "u") String user,
                                                       @RequestParam(name = "b") String beginS,
                                                       @RequestParam(name = "e") String endS) {

        return dashboardService.getFuelCostByVehicleAndDataAndUser(user, beginS, endS);
    }

    @GetMapping("trip_by_login_user")
    public List<ChartDataRespondDTO> getVehicleTripBySuperUser(@RequestParam(name = "b") String beginS,
                                                               @RequestParam(name = "e") String endS) {
        return dashboardService.getDistanceByVehicleAndDataByLoginUser(beginS, endS);
    }

    @GetMapping("fuel_cost_by_login_user")
    public List<ChartDataRespondDTO> getFuelCostBySuperUser(@RequestParam(name = "b") String beginS,
                                                            @RequestParam(name = "e") String endS) {
        return dashboardService.getFuelCostByVehicleAndDataByLoginUser(beginS, endS);
    }

}