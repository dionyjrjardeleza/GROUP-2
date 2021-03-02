package com.oopclass.breadapp.repository;


import com.oopclass.breadapp.models.MTPartea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MTParteaRepository extends JpaRepository<MTPartea, Long> {

	//User findByEmail(String email);
}
