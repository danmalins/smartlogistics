package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.driver.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.ClientProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.response.DriverProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.ClientMapper;
import org.dgroup.userservicesmartlogistics.mapper.DriverMapper;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final DriverMapper driverMapper;
    private final ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<DriverProfileResponseDTO> createDriver(
            @RequestBody CreateDriverRequestDTO dto,
            Authentication authentication) {
        DriverProfile createDriver = managerService.createDriver(dto, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(createDriver));
    }

    @GetMapping("/client/{email}")
    public ResponseEntity<ClientProfileResponseDTO> getClient(
            @PathVariable String email,
            Authentication authentication) {
        ClientProfile client = managerService.getClient(email, authentication);
        return ResponseEntity.ok(clientMapper.toResponse(client));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientProfileResponseDTO>> getAllClients(
            Authentication authentication) {
        List<ClientProfile> clients = managerService.getAllClients(authentication);
        return ResponseEntity.ok(clientMapper.toResponseList(clients));
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<DriverProfileResponseDTO>> getAllDrivers(
            Authentication authentication) {
        List<DriverProfile> drivers = managerService.getAllDrivers(authentication);
        return ResponseEntity.ok(driverMapper.toResponseList(drivers));
    }

    @GetMapping("/availible-drivers")
    public ResponseEntity<List<DriverProfileResponseDTO>> getAvailableDrivers(
            Authentication authentication) {
        List<DriverProfile> availableDrivers =
                managerService.getAvailableDrivers(authentication);

        return ResponseEntity.ok(driverMapper.toResponseList(availableDrivers));
    }

    @GetMapping("/driver/{email}")
    public ResponseEntity<DriverProfileResponseDTO> getDriver(
            @PathVariable String email,
            Authentication authentication) {
        DriverProfile driver = managerService.getDriver(email, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(driver));
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<DriverProfileResponseDTO>> getDriversByStatus(
            @PathVariable DriverStatus status,
            Authentication authentication) {
        List<DriverProfile> driversByStatus = managerService.getDriversByStatus(status, authentication);
        return ResponseEntity.ok(driverMapper.toResponseList(driversByStatus));
    }

//    @PutMapping("truck-info/{email}")
//    public ResponseEntity<DriverProfileResponseDTO> updateTruckInfo(
//            @PathVariable String email,
//            @RequestBody UpdateDriverTruckInfoRequestDTO dto,
//            Authentication authentication) {
//        DriverProfile updatedTruckInfo = managerService.updateTruckInfo(email, dto, authentication);
//        return ResponseEntity.ok(driverMapper.toResponse(updatedTruckInfo));
//    }

    @PutMapping("license-number/{email}")
    public ResponseEntity<DriverProfileResponseDTO> updateDriverLicenseNumber(
            @PathVariable String email,
            @RequestBody UpdateDriverLicenseNumberRequestDTO dto,
            Authentication authentication) {
        DriverProfile updatedLicense = managerService.updateDriverLicenseNumber(email, dto, authentication);
        return ResponseEntity.ok(driverMapper.toResponse(updatedLicense));
    }
}
