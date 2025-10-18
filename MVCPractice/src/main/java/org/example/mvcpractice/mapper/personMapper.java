package org.example.mvcpractice.mapper;

import org.example.mvcpractice.dto.request.personCreateRequets;
import org.example.mvcpractice.dto.request.personUpdatedRequest;
import org.example.mvcpractice.dto.response.personResponse;
import org.example.mvcpractice.model.Person;
import org.mapstruct.*;

@Mapper(componentModel =  "spring")
public interface personMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Person toEntity(personCreateRequets dto);

    personResponse toResponse(Person entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Person entity, personUpdatedRequest dto);
}
