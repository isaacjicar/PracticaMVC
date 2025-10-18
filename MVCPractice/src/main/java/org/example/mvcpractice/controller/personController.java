package org.example.mvcpractice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mvcpractice.dto.request.personCreateRequets;
import org.example.mvcpractice.dto.request.personUpdatedRequest;
import org.example.mvcpractice.dto.response.personResponse;
import org.example.mvcpractice.service.personService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class personController {

    private final personService personService;

    @PostMapping
    public personResponse create(@Valid @RequestBody personCreateRequets req) {
        return personService.create(req);
    }

    @PutMapping("/{id}")
    public personResponse update(@PathVariable int id,
                                 @Valid @RequestBody personUpdatedRequest req) {
        return personService.update(id, req);
    }

    // Listado con filtro opcional ?q=
    @GetMapping
    public List<personResponse> findAll(@RequestParam(required = false) String q) {
        return personService.findAll(q);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public personResponse findById(@PathVariable int id) {
        return personService.getPersonId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return personService.deleteById(id);
    }
}


