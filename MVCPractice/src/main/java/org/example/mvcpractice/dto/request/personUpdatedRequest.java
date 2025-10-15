package org.example.mvcpractice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record personUpdatedRequest (
    @Size(max = 80 ) String name,
    @Size(max = 120) String lastName,
    @Email @Size (max = 255) String email,
    @Size (max = 30 ) String number
    )
{}
