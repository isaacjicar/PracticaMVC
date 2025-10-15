package org.example.mvcpractice.repository;


import org.example.mvcpractice.model.person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface personRepository  extends JpaRepository<person,Integer> {
    Optional<person> findByEmailIgnoreCase(String email);
}
