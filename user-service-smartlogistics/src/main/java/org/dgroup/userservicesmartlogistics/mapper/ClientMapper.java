package org.dgroup.userservicesmartlogistics.mapper;

import org.dgroup.userservicesmartlogistics.dto.response.ClientProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.response.DriverProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.phone", target = "phone")
    ClientProfileResponseDTO toResponse(ClientProfile clientProfile);

    List<ClientProfileResponseDTO> toResponseList(List<ClientProfile> clients);
}
