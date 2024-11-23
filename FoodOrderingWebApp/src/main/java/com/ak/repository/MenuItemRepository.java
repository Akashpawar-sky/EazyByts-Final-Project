package com.ak.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
