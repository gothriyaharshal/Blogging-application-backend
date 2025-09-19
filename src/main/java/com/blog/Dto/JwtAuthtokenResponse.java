package com.blog.Dto;

import lombok.*;
@Data
public class JwtAuthtokenResponse {

    private String token;
    private UserDto user;

}
