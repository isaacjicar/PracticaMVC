package org.example.mvcpractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

public record personResponse(
     int id,
     String name,
     String lastName,
     String email,
     String number,
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
     LocalDateTime createdAt,
     @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
     LocalDateTime updatedAt
)
{}
