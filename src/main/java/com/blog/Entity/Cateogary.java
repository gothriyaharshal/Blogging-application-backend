package com.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Cateogary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cateogary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cateogaryId;

    @Column(name = "title" , length = 100 ,nullable = false)
    private String cateogaryTitle;

    @Column(name = "description" , nullable = false)
    private String cateogaryDescription;

    @OneToMany(mappedBy = "cateogary" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Post> posts;

}
