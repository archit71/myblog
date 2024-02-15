package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = maptoEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = maptoDto(savedComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentDto> commentDtos = comments.stream().map(comment -> maptoDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment not found with id: " + commentId)
        );

        CommentDto commentDto = maptoDto(comment);
        return commentDto;
    }







    @Override
    public List<CommentDto> getAllCommentById() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> maptoDto(comment)).collect(Collectors.toList());

    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment not found with id: " + commentId)
        );
        commentRepository.deleteById(commentId);
    }


    private CommentDto maptoDto(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);

        return dto;
    }

    private Comment maptoEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
