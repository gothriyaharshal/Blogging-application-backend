package com.blog.Service;

import com.blog.Dto.CommentDto;

public interface ComentService {

    public CommentDto creatingComment(CommentDto commentDto,Integer postId);

    public void DeletingComment(Integer commentId);


}
