package com.blog.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CateogaryDto {
    private Integer cateogaryId;
    @NotEmpty(message = "Please fill cateogary title")
    private String cateogaryTitle;
    @NotEmpty(message = "please give description ")
    private String cateogaryDescription;
}
