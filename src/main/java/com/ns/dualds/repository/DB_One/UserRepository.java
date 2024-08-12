package com.ns.dualds.repository.DB_One;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ns.dualds.model.DB_One.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
