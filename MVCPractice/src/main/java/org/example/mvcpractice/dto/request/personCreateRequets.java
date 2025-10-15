package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record personCreateRequets(

        @NotBlank @Size (max =80) String name,
        @NotBlank @Size(max = 120) String lastName,
        @NotBlank @Email @Size(max = 220) String email,
        @NotBlank @Size(max = 30) String number
) {
}
