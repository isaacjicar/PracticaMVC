package org.example.mvcpractice.mapper;



import org.example.mvcpractice.dto.request.roleCreateRequest;
import org.example.mvcpractice.dto.request.roleUpdatedRequest;
import org.example.mvcpractice.dto.response.personRoleReponse;
import org.example.mvcpractice.model.PersonRole;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface personRoleMapper {

    personRoleReponse toResponse(PersonRole entity);

}
