package org.example.mvcpractice.repository;

import org.example.mvcpractice.model.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface personRoleRepository extends JpaRepository<PersonRole,Integer> {
    boolean existsByPerson_IdAndRole_Id(Integer personId, Integer roleId);

}
