package org.example.mvcpractice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mvcpractice.dto.response.personRoleReponse;
import org.example.mvcpractice.service.personRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/person-roles", produces = "application/json")
@RequiredArgsConstructor
@Validated
@Slf4j
public class personRoleController {

    private final personRoleService service;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<personRoleReponse> assign(@RequestBody @Valid AssignRequest req) {
        log.info("Assign role: personId={}, roleId={}", req.personId(), req.roleId());
        var resp = service.assign(req.personId(), req.roleId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resp.id())
                .toUri();

        return ResponseEntity.created(location).body(resp);
    }

    // Opción A: actualizar el rol con subrecurso explícito
    @PutMapping(path = "/{personRoleId}/role", consumes = "application/json")
    public ResponseEntity<personRoleReponse> updateRole(@PathVariable int personRoleId,
                                                        @RequestBody @Valid UpdateRoleRequest req) {
        log.info("Update role in association: personRoleId={}, newRoleId={}", personRoleId, req.roleId());
        var resp = service.updateRole(personRoleId, req.roleId());
        return ResponseEntity.ok(resp);
    }

    // DTOs (mover a package dto.request si prefieres)
    public record AssignRequest(
            @NotNull @Positive Integer personId,
            @NotNull @Positive Integer roleId
    ) { }

    public record UpdateRoleRequest(
            @NotNull @Positive Integer roleId
    ) { }
}
