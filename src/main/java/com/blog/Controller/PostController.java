package com.blog.Controller;

import com.blog.Dto.ApiResponse;
import com.blog.Dto.CateogaryDto;
import com.blog.Dto.PostDto;
import com.blog.Dto.PostResponse;
import com.blog.Service.PostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class PostController {
    @Autowired
    public PostService postService;

    @Autowired
    public ModelMapper modelMapper;

    //creating RestApi
    @PostMapping("/user/{userId}/cateogary/{cateogaryId}/posts")
    public ResponseEntity<PostDto> creating(@RequestBody PostDto postDto , @PathVariable("userId") Integer userId , @PathVariable("cateogaryId") Integer cateogaryId)
    {
        PostDto post = this.postService.createPost(postDto, userId, cateogaryId);
        return new  ResponseEntity<PostDto>(post,HttpStatus.OK);
    }

    //getting all posts by userId
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> gettingPostByUser(@PathVariable("userId") Integer userId)
    {
        List<PostDto> postDtos = this.postService.gettingUserAllPost(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    //getting all posts by cateogaryId
    @GetMapping("/cateogary/{cateogaryId}/posts")
    public ResponseEntity<List<PostDto>> gettingPostByCateogary(@PathVariable("cateogaryId") Integer cateogaryId)
    {
        List<PostDto> postDtos = this.postService.gettingCateogaryAllPost(cateogaryId);
        return new ResponseEntity<List<PostDto>>(postDtos , HttpStatus.OK);
    }


    //getting All Post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> gettingAllPost(@RequestParam(value ="pageSize",
            defaultValue = "1",
            required = false)
            Integer pageSize ,

    @RequestParam(value ="pageNumber",
            defaultValue = "1",
            required =false) Integer pageNumber,

    @RequestParam(value ="sortBy",
            defaultValue = "postId",
            required =false) String sortBy,

   @RequestParam(value ="sortDir",
            defaultValue = "asc",
            required =false) String sortDir


    )
    {

        PostResponse postResponse = this.postService.gettingAll(pageSize, pageNumber,sortBy,sortDir);
        return new  ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);


    }

    //getting one post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> gettingOne(@PathVariable("postId") Integer postId)
    {
        PostDto one = this.postService.getOne(postId);
        return new ResponseEntity<PostDto>(one,HttpStatus.OK);
    }

    //deleting Post by id
    @DeleteMapping("posts/{postId}")
    public ResponseEntity<ApiResponse> deleting(@PathVariable("postId") Integer postId)
    {
        this.postService.delete(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted SuccesFully",true),HttpStatus.OK);
    }

    //updating post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatingPosts(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId)
    {
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return  new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }

    //searching
    @GetMapping("posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchin(@PathVariable("keyword") String keyword)
    {

        List<PostDto> postDtos = this.postService.searchingPost(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }


}
