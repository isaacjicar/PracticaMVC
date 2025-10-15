package org.example.mvcpractice.repository;

import org.example.mvcpractice.model.role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<role,Integer> {
    boolean existsByNameIgnoreCase(String name);
}
