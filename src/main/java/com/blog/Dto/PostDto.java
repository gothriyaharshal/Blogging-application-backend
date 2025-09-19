package com.blog.Dto;

import com.blog.Entity.Cateogary;
import com.blog.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private Date date;
    private String imageUrl;
    private String content;

    private UserDto user;

    private CateogaryDto cateogary;

    private Set<CommentDto> comments = new HashSet<>();



}
