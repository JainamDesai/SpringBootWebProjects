package com.smart.SmartContactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.SmartContactManager.entity.User;
import com.smart.SmartContactManager.repo.UserRepo;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepo userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		CustomeUserDetails customeUserDetails = null;
			if (username != null) {
				User user = userrepo.findByEmail(username);
				
				if (user == null) {
					throw new UsernameNotFoundException("Username is not found");
				} else {
				
					customeUserDetails = new CustomeUserDetails(user);
				}
			} else {
				throw new UsernameNotFoundException("Username is not found");
			}
		return customeUserDetails;
	}

}
