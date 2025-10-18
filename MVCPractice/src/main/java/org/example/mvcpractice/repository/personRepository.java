package org.example.mvcpractice.repository;


import org.example.mvcpractice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface personRepository  extends JpaRepository<Person,Integer> {
    Optional<Person> findByEmailIgnoreCase(String email);

    @Query("""
           select p
           from Person p
           where lower(p.name) like lower(concat('%', :q, '%'))
              or lower(p.lastName) like lower(concat('%', :q, '%'))
              or lower(p.email) like lower(concat('%', :q, '%'))
           """)
    List<Person> search(@Param("q") String q);

}
