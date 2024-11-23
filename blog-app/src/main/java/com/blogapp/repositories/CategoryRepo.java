package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.models.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    
}
