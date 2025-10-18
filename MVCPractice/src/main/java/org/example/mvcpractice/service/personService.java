package org.example.mvcpractice.service;

import org.example.mvcpractice.model.Person;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.mvcpractice.dto.request.personCreateRequets;
import org.example.mvcpractice.dto.request.personUpdatedRequest;
import org.example.mvcpractice.dto.response.personResponse;
import org.example.mvcpractice.mapper.personMapper;
import org.example.mvcpractice.model.Person;
import org.example.mvcpractice.repository.personRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class personService {

    private final personRepository repo;
    private final personMapper mapper;

    @Transactional
    public personResponse create(personCreateRequets req){
        Person entity = mapper.toEntity(req);
        repo.save(entity);
        return mapper.toResponse(entity);
    }

    @Transactional
    public personResponse update(int id, personUpdatedRequest req) {
        Person entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con el id " + id));
        mapper.updateEntity(entity, req);

        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public personResponse getPersonId(int id) {
        Person p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con el id " + id));
        return mapper.toResponse(p);
    }

    @Transactional(readOnly = true)
    public List<personResponse> findAll(String q) {
        List<Person> data = (q == null || q.isBlank())
                ? repo.findAll(Sort.by("lastName", "name"))
                : repo.search(q.trim());
        return data.stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public ResponseEntity<Void> deleteById(int id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Persona no encontrada con el id " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}

