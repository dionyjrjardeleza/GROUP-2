package com.oopclass.breadapp.repository;


import com.oopclass.breadapp.models.MTAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface MTAppointmentRepository extends JpaRepository<MTAppointment, Long> {

	//User findByEmail(String email);
}
