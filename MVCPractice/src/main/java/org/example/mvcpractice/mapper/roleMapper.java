package org.example.mvcpractice.mapper;


import org.example.mvcpractice.dto.request.roleCreateRequest;
import org.example.mvcpractice.dto.request.roleUpdatedRequest;
import org.example.mvcpractice.dto.response.personResponse;
import org.example.mvcpractice.dto.response.roleResponse;
import org.example.mvcpractice.model.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface roleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Role toEntity(roleCreateRequest dto);

    roleResponse toResponse(Role entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Role entity, roleUpdatedRequest dto);
}
