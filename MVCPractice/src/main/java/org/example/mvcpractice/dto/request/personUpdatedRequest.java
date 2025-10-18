package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record personUpdatedRequest (
   @NotBlank @Size(max = 80 ) String name,
   @NotBlank  @Size(max = 120) String lastName,
   @NotBlank  @Email @Size (max = 255) String email,
   @NotNull @Size (max = 30 ) String number
    )
{}
