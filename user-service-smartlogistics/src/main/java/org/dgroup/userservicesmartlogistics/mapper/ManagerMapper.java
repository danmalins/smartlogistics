package org.dgroup.userservicesmartlogistics.mapper;

import org.dgroup.userservicesmartlogistics.dto.response.ManagerProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.phone", target = "phone")
    ManagerProfileResponseDTO toResponse (ManagerProfile managerProfile);
}
