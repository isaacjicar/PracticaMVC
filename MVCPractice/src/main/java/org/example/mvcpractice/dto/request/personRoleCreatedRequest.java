package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.mvcpractice.model.person;
import org.example.mvcpractice.model.role;


public record personRoleCreatedRequest (

     @NotNull Integer personId,
     @NotNull Integer roleId
)
{}
