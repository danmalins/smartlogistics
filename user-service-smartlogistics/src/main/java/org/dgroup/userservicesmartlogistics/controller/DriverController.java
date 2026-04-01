package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverLocationRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverStatusRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.DriverProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.DriverMapper;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @GetMapping("/profile")
    public ResponseEntity<DriverProfileResponseDTO> getDriverProfile(
            Authentication authentication) {
        DriverProfile driverInfo = driverService.getDriverProfile(authentication);
        return ResponseEntity.ok(driverMapper.toResponse(driverInfo));
    }

    @PutMapping("/location")
    public ResponseEntity<DriverProfileResponseDTO> updateDriverLocation (
            @RequestBody UpdateDriverLocationRequestDTO dto,
            Authentication authentication) {
        DriverProfile driverLocation = driverService.updateDriverLocation(dto.getLatitude(), dto.getLongitude(), authentication);
        return ResponseEntity.ok(driverMapper.toResponse(driverLocation));
    }

    @PutMapping("/status")
    public ResponseEntity<DriverProfileResponseDTO> updateDriverStatus (
            @RequestBody UpdateDriverStatusRequestDTO dto,
            Authentication authentication) {
        DriverProfile driverStatus = driverService.updateDriverStatus(dto.getDriverStatus(), authentication);
        return ResponseEntity.ok(driverMapper.toResponse(driverStatus));
    }
}
