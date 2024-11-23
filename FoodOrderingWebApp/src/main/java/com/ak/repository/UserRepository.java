package com.ak.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

   
}



// CartItem Repository



