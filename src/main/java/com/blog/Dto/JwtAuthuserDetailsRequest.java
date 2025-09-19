package com.blog.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthuserDetailsRequest
{
private String username;
private String password;

//we give those in paratmeter of authController CreateToken method beacause user olny gives username and password token generates automatically
}
