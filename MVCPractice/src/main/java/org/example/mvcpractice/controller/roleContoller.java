package org.example.mvcpractice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mvcpractice.dto.request.roleCreateRequest;
import org.example.mvcpractice.dto.request.roleUpdatedRequest;
import org.example.mvcpractice.dto.response.roleResponse;
import org.example.mvcpractice.service.roleService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class roleContoller {

    private final roleService service;

    @PostMapping
    public roleResponse create(@Valid @RequestBody roleCreateRequest req) {
        return service.craete(req);
    }

    @PutMapping("/{id}")
    public roleResponse update(@PathVariable int id,
                               @Valid @RequestBody roleUpdatedRequest req) {
        return service.updated(id, req);
    }


    @GetMapping
    public List<roleResponse> list(@RequestParam(required = false) String q,
                                   @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.findAll(q, dir);
    }

    @GetMapping("/{id}")
    public roleResponse findById(@PathVariable int id) {
        return service.getRolId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
