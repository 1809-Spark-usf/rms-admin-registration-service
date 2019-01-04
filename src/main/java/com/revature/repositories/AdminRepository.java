package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	
	public List<Admin> findAll();

	public Admin findByUsername(String username);
	public Admin findByFirstname(String firstname);
	
	
	@Query(value = "UPDATE admin SET approve = true WHERE id = ?1 RETURNING id", nativeQuery = true)
	public int setapprove(int id);
	
	
	
	

}
