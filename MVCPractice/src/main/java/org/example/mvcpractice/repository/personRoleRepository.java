package org.example.mvcpractice.repository;

import org.example.mvcpractice.model.person_role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface personRoleRepository extends JpaRepository<person_role,Integer> {
    boolean existsByPerson_IdAndRole_Id(Integer personId,Integer roleId);
}
