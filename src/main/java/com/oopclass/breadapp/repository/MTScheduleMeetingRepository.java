package com.oopclass.breadapp.repository;


import com.oopclass.breadapp.models.MTScheduleMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MTScheduleMeetingRepository extends JpaRepository<MTScheduleMeeting, Long> {

	//User findByEmail(String email);
}
