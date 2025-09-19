package com.blog.ServiceImpl;

import com.blog.Dto.CateogaryDto;
import com.blog.Dto.PostDto;
import com.blog.Dto.PostResponse;
import com.blog.Dto.UserDto;
import com.blog.Entity.Cateogary;
import com.blog.Entity.Post;
import com.blog.Entity.User;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Repositary.CateogaryRepo;
import com.blog.Repositary.PostRepo;
import com.blog.Repositary.UserRepo;
import com.blog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    public PostRepo postRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public CateogaryRepo cateogaryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer cateogaryId) {

        //userID
        User userIdd = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userID", "Not Found", userId));

        //cateogaryId
        Cateogary cateogaryidd = this.cateogaryRepo.findById(cateogaryId).orElseThrow(() -> new ResourceNotFoundException("CateogaryID", "Not found", cateogaryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageUrl("default.jpg");
        post.setDate(new Date());
        post.setUser(userIdd);
        post.setCateogary(cateogaryidd);

        //saving post into database
        Post savedPost = postRepo.save(post);


        //converting post into postDto
        return this.modelMapper.map(savedPost,PostDto.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", " Not Found", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Post save = this.postRepo.save(post);

        PostDto map = this.modelMapper.map(save, PostDto.class);

        return map;
    }

    @Override
    public PostDto getOne(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postID", "not found", postId));
        PostDto map = this.modelMapper.map(post, PostDto.class);

        return map;
    }

    @Override
    public PostResponse gettingAll(Integer pageSize, Integer pageNumber,String sortBy,String sortDir) {


        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
            sort= Sort.by(sortBy).ascending();
        }

        else {
            sort=Sort.by(sortBy).descending();
        }

        PageRequest pageRequest = PageRequest.of(pageSize, pageNumber,sort);
        Page<Post> all = this.postRepo.findAll(pageRequest);

        List<Post> content = all.getContent();

        List<PostDto> dtos = content.stream().map((postDto) -> this.modelMapper.map(postDto, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse =new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNumber(pageRequest.getPageNumber());
        postResponse.setPageSize(pageRequest.getPageSize());
        return postResponse;
    }

    @Override
    public void delete(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", "not Found", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> gettingCateogaryAllPost(Integer cateogaryId) {

        Cateogary cateogary = this.cateogaryRepo.findById(cateogaryId).orElseThrow(() -> new ResourceNotFoundException("cateogaryId not", "not Found ", cateogaryId));
        List<Post> byCateogary = this.postRepo.findByCateogary(cateogary);

        List<PostDto> postDtos = byCateogary.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> gettingUserAllPost(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("not ", "find id", userId));

        List<Post> byUser = this.postRepo.findByUser(user);

        List<PostDto> dtos = byUser.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<PostDto> searchingPost(String keyword) {
        List<Post> byTitleContaining = this.postRepo.findByTitleContaining(keyword);
        return (List<PostDto>) byTitleContaining.stream().map((postDto) -> this.modelMapper.map(postDto,PostDto.class));

    }
}
