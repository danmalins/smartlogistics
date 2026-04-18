package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.manager.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.DriverProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.DriverMapper;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final DriverMapper driverMapper;

    @PostMapping
    public ResponseEntity<DriverProfileResponseDTO> createDriver(
            @RequestBody CreateDriverRequestDTO dto,
            Authentication authentication) {
        DriverProfile createDriver = managerService.createDriver(dto, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(createDriver));
    }

    @GetMapping("/availible-drivers")
    public ResponseEntity<List<DriverProfileResponseDTO>> getAvailableDrivers(
            Authentication authentication) {
        List<DriverProfile> availableDrivers =
                managerService.getAvailableDrivers(authentication);

        return ResponseEntity.ok(driverMapper.toResponseList(availableDrivers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverProfileResponseDTO> getDriver(
            @PathVariable UUID id,
            Authentication authentication) {
        DriverProfile driver = managerService.getDriver(id, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(driver));
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<DriverProfileResponseDTO>> getDriversByStatus(
            @PathVariable DriverStatus status,
            Authentication authentication) {
        List<DriverProfile> driversByStatus = managerService.getDriversByStatus(status, authentication);
        return ResponseEntity.ok(driverMapper.toResponseList(driversByStatus));
    }

    @PutMapping("truck-info/{id}")
    public ResponseEntity<DriverProfileResponseDTO> updateTruckInfo(
            @PathVariable UUID id,
            @RequestBody UpdateDriverTruckInfoRequestDTO dto,
            Authentication authentication) {
        DriverProfile updatedTruckInfo = managerService.updateTruckInfo(id, dto, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(updatedTruckInfo));
    }

    @PutMapping("license-number/{id}")
    public ResponseEntity<DriverProfileResponseDTO> updateDriverLicenseNumber(
            @PathVariable UUID id,
            @RequestBody UpdateDriverLicenseNumberRequestDTO dto,
            Authentication authentication) {
        DriverProfile updatedLicense = managerService.updateDriverLicenseNumber(id, dto, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(updatedLicense));
    }
}
