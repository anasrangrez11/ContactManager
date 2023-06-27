package com.example.ContactManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ContactManager.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

	@Query("Select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);
	
	@Query("Select u from User u where u.name=:name")
	public User getUserByName(@Param("name") String name);
	
}
