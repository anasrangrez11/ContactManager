package com.example.ContactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.ContactManager.entities.User;
import com.example.ContactManager.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not found User!!!!!!");
		}
		
		CustomUserDetails cud = new CustomUserDetails(user);
		return cud;
	}

}
