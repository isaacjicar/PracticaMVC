package org.example.mvcpractice.repository;

import org.example.mvcpractice.model.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface roleRepository extends JpaRepository<Role,Integer> {
    List<Role> findByNameContainingIgnoreCase(String q, Sort sort);

}
