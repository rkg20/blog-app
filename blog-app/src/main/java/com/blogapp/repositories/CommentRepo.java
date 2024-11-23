package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.models.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {


}
