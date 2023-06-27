package com.example.ContactManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ContactManager.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

	
	@Query("Select c from Contact as c where c.user.id= :userId")
	public Page<Contact> getContactByUser(@Param("userId") int userId, Pageable pageable);
	
}
