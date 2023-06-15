package com.mindlease.fa.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mindlease.fa.model.Company;
import com.mindlease.fa.model.Role;
import com.mindlease.fa.model.User;
import com.mindlease.fa.repository.CompanyRepository;
import com.mindlease.fa.repository.RoleRepository;
import com.mindlease.fa.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	User user;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			log.info("--------------username::::{}"+userName);
			user = userRepository.findByEmail(userName)
					.orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
			log.info("--------------user::::{}"+user);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					getAuthorities(user));
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		//log.info("{} - {} - {}  ", user.getName(), user.getEmail(), user.getRoles());
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	public User getUser() {
		return user != null ? user : new User();
	}

	public User save(User user) {
		User saveduser = null;
		if (user.getId() == null) {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			user.setRoles(new HashSet(Arrays.asList(user.getRole())));
			saveduser = userRepository.saveAndFlush(user);
			userRepository.flush();
			log.info("Registration completed successful for user - {}", user.getEmail());
		} else {
			//user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			log.info("Befor update - {}", user.getPassword());
			user.setRoles(new HashSet(Arrays.asList(user.getRole())));
			saveduser = userRepository.save(user);
			userRepository.flush();
			log.info("Update successful for user - {}", user.getEmail());
		}
		return saveduser;
	}

	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	public User findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.isPresent() ? user.get() : null;
	}
	
	public Optional<User> findById(Optional<Integer> id) {
		return userRepository.findById(id);
	}

	public void deleteById(Integer id) {
		userRepository.deleteById(id);
		
	}

	public boolean checkPasswod(String oldPassword,User user) {
		return new BCryptPasswordEncoder().matches(oldPassword, user.getPassword());
	}
	
	public User saveChangePassword(User user) {
		log.info("------------user:::{}", user);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getNewPassword()));
		log.info("------------user:::{}", user);
		return userRepository.save(user);
	}
	
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}
	public List<Company> findAllCompanies() {
		return companyRepository.findAll();
	}
}