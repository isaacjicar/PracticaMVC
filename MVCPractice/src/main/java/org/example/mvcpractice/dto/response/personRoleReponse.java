package org.example.mvcpractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.mvcpractice.model.person;
import org.example.mvcpractice.model.role;

import java.time.LocalDateTime;


public record personRoleReponse (
   int id,
   person person,
   role role,
   @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
   LocalDateTime createdAt,
   @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
   LocalDateTime updatedAt
){}
