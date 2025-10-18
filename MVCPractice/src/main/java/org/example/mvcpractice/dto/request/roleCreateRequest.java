package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


public record roleCreateRequest (
    @NotBlank @Size(max = 120) String name,
    @NotBlank  @Size(max = 120) String description
){}
