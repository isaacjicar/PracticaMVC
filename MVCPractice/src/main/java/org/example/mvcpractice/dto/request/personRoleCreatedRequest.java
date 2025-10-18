package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.NotNull;


public record personRoleCreatedRequest (

     @NotNull Integer personId,
     @NotNull Integer roleId
)
{}
