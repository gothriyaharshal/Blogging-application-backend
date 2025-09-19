package com.blog.ServiceImpl;

import com.blog.Dto.CommentDto;
import com.blog.Entity.Comments;
import com.blog.Entity.Post;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Repositary.CommentReo;
import com.blog.Repositary.PostRepo;
import com.blog.Service.ComentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements ComentService {

    @Autowired
    public PostRepo postRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public CommentReo commentReo;

    @Override
    public CommentDto creatingComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", "not found", postId));

        Comments comment= this.modelMapper.map(commentDto, Comments.class);

        //we don't store this comment in database we store in post id
        // Comments save = this.commentReo.save(map);
        comment.setPost(post);

        //now we store it in database
        Comments save = this.commentReo.save(comment);

       //converting it in commnetDto
        CommentDto map = this.modelMapper.map(save, CommentDto.class);
       return map;

    }

    @Override
    public void DeletingComment(Integer commentId) {


        Comments comments = this.commentReo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("postID", "not found", commentId));

        this.commentReo.delete(comments);
    }
}
