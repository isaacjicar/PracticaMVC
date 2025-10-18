package org.example.mvcpractice.service;

import lombok.RequiredArgsConstructor;
import org.example.mvcpractice.dto.response.personRoleReponse;
import org.example.mvcpractice.mapper.personRoleMapper;
import org.example.mvcpractice.model.PersonRole;
import org.example.mvcpractice.repository.personRepository;
import org.example.mvcpractice.repository.personRoleRepository;
import org.example.mvcpractice.repository.roleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class personRoleService {

    private final personRoleRepository personRoleRepo;
    private final personRepository personRepo;
    private final roleRepository roleRepo;
    private final personRoleMapper mapper;

    @Transactional
    public personRoleReponse assign(int personId, int roleId) {


        if (personRoleRepo.existsByPerson_IdAndRole_Id(personId, roleId)) {
            throw new RuntimeException("La persona ya tiene el rol " + roleId);
        }

        var person = personRepo.findById(personId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        var role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        var pr = new PersonRole();
        pr.setPerson(person);
        pr.setRole(role);

        pr = personRoleRepo.save(pr);


        return mapper.toResponse(pr);
    }

    @Transactional
    public personRoleReponse updateRole(int personRoleId, int newRoleId) {
        var pr = personRoleRepo.findById(personRoleId)
                .orElseThrow(() -> new RuntimeException("Asociación persona-rol no encontrada"));

        var role = roleRepo.findById(newRoleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));


        if (personRoleRepo.existsByPerson_IdAndRole_Id(pr.getPerson().getId(), newRoleId)) {
            throw new RuntimeException("La persona ya tiene ese rol");
        }

        pr.setRole(role);
        pr = personRoleRepo.save(pr);
        return mapper.toResponse(pr);
    }
}
