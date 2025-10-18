package org.example.mvcpractice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.mvcpractice.model.Person;
import org.example.mvcpractice.model.Role;


import java.time.LocalDateTime;


public record personRoleReponse (
   int id,
   Person person,
   Role role,
   @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
   LocalDateTime createdAt,
   @JsonFormat(pattern = "yyyy-MM_dd'T'HH:mm:ss")
   LocalDateTime updatedAt
){}
