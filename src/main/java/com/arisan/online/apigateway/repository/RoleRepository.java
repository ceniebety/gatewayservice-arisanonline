package com.arisan.online.apigateway.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.arisan.online.apigateway.domain.Role;


public interface RoleRepository extends JpaRepository<Role,Long>{

	
}
