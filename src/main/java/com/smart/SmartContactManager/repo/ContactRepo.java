package com.smart.SmartContactManager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smart.SmartContactManager.entity.Contacts;

@Repository
@EnableJpaRepositories
public interface ContactRepo extends JpaRepository<Contacts, Integer> {
	
	@Query(value = "from Contacts  where user_id=:id")
	List<Contacts> getcontactlist(@Param("id") int id);
	
	Contacts findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value = "delete Contacts where contact_id=:contact_id")
	void deleteById(@Param("contact_id") int contact_id); 

}
