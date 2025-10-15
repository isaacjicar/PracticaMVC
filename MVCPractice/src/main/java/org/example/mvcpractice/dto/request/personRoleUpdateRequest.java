package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.NotNull;

public record personRoleUpdateRequest (

    @NotNull Integer roleId,
    @NotNull Integer personId
)
{}
