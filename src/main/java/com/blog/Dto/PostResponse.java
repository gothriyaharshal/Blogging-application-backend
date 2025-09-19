package com.blog.Dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResponse {

    public List<PostDto> content;
    private Integer pageNumber;
    private Long totalElement;
    private Integer pageSize;
    private Integer totalPages;
    private boolean lastPage;
}
