package com.blog.Service;

import com.blog.Dto.CateogaryDto;
import com.blog.Dto.PostDto;
import com.blog.Dto.PostResponse;
import com.blog.Dto.UserDto;

import java.util.List;

public interface PostService {

    //created
    public PostDto createPost(PostDto postDto, Integer userId ,Integer cateogaryId);

    //updated
    public PostDto updatePost(PostDto postDto,Integer postId);

    //getting one
    public PostDto getOne(Integer postId);

    //geeting all
    public PostResponse gettingAll(Integer pageSize, Integer pageNumber,String sortBy,String sortDir);

    //deleting Post
    public void delete(Integer postId);

    //CateogaryAllPost
    public List<PostDto> gettingCateogaryAllPost(Integer cateogaryId);

    //userAllPost
    public List<PostDto> gettingUserAllPost(Integer userId);

    //Searching any Post
    public List<PostDto> searchingPost(String keyword);

}
