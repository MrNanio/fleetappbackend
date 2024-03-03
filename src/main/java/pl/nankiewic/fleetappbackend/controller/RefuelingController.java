package pl.nankiewic.fleetappbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingModifyDTO;
import pl.nankiewic.fleetappbackend.dto.refueling.RefuelingView;
import pl.nankiewic.fleetappbackend.service.RefuelingService;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/refueling")
public class RefuelingController {

    private final RefuelingService refuelingService;

    @PostMapping
    public RefuelingModifyDTO addRefueling(@Validated @RequestBody RefuelingModifyDTO refuelingModifyDTO) {
        return refuelingService.createVehicleRefueling(refuelingModifyDTO);
    }

    @PutMapping
    public RefuelingModifyDTO updateRefueling(@Validated @RequestBody RefuelingModifyDTO refuelingModifyDTO) {
        return refuelingService.updateVehicleRefueling(refuelingModifyDTO);
    }

    @GetMapping("/{id}")
    public RefuelingView getRefuelingById(@PathVariable Long id) {
        return refuelingService.getRefuelingById(id);
    }

    @GetMapping("/v/{id}")
    public List<RefuelingView> getRefuelingByVehicle(@PathVariable Long id) {
        return refuelingService.getRefuelingByVehicle(id);
    }

    @GetMapping
    public List<RefuelingView> getRefuelingByUser() {
        return refuelingService.getRefuelingByUser();
    }

    @GetMapping("/my")
    public List<RefuelingView> getRefuelingByAuthor() {
        return refuelingService.getRefuelingByAuthor();
    }

    @GetMapping("/list")
    public List<RefuelingView> getRefuelingByUserIdAndVehicleId(@RequestParam(name = "u") Long userId,
                                                                @RequestParam(name = "v") Long vehicleId) {
        return refuelingService.getRefuelingByUserAndVehicle(userId, vehicleId);
    }

    @DeleteMapping("/{id}")
    public void deleteRefueling(@PathVariable Long id) {
        refuelingService.deleteRefuelingById(id);
    }

}