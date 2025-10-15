package org.example.mvcpractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


public record roleResponse (
     int id,
     String name,
     String description,
     @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
     LocalDateTime createdAt,
     @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
     LocalDateTime updatedAt
){}
