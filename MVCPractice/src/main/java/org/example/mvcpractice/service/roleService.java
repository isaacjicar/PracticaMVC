package org.example.mvcpractice.service;

import lombok.RequiredArgsConstructor;
import org.example.mvcpractice.dto.request.roleCreateRequest;
import org.example.mvcpractice.dto.request.roleUpdatedRequest;
import org.example.mvcpractice.dto.response.roleResponse;
import org.example.mvcpractice.mapper.roleMapper;
import org.example.mvcpractice.model.Role;
import org.example.mvcpractice.repository.roleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class roleService {

    private final roleRepository repo;
    private final roleMapper mapper;

    @Transactional
    public roleResponse craete(roleCreateRequest req) {
        Role rol = mapper.toEntity(req);
        repo.save(rol);
        return mapper.toResponse(rol);
    }

    @Transactional
    public roleResponse updated (int id, roleUpdatedRequest req) {
        Role rol = repo.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrada con el " + id));
        mapper.updateEntity(rol, req);
        return mapper.toResponse(rol);
    }

    @Transactional(readOnly = true)
    public roleResponse getRolId(int id) {
        Role rol = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrada con el " + id));
        return mapper.toResponse(rol);
    }

    @Transactional
    public roleResponse delete (int id) {
        repo.deleteById(id);
        return mapper.toResponse(repo.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrada con el " + id)));

    }

    public List<roleResponse> findAll(String q, Sort.Direction dir) {
        Sort sort = Sort.by(dir == null ? Sort.Direction.ASC : dir, "name");
        List<Role> data = (q == null || q.isBlank())
                ? repo.findAll(sort)
                : repo.findByNameContainingIgnoreCase(q.trim(), sort);
        return data.stream().map(mapper::toResponse).toList();
    }
}
