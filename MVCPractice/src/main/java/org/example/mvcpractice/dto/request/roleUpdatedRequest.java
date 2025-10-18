package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record roleUpdatedRequest  (

    @NotBlank @Size(max = 120) String name,
    @NotBlank  @Size(max = 120) String description
){}

