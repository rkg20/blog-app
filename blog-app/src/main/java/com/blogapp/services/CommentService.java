package com.blogapp.services;

import com.blogapp.payloads.CommentDto;

public interface CommentService {
    public CommentDto createComment(CommentDto commentDto,Integer postId);
    public void deleteComment(Integer commentId);
}
