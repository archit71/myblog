package com.myblog7.service;

import com.myblog7.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);




    CommentDto getCommentById(Long postId, Long commentId);



    List<CommentDto> getAllCommentById();

    void deleteCommentById(Long postId, Long commentId);
}

