package com.arisan.online.apigateway.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arisan.online.apigateway.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	List<User> findByUsername(String username);
}
