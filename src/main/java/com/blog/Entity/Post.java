package com.blog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "Title" , length = 100 , nullable = false)
    private String title;

    @Column(name = "Date")
    private Date date;
    private String imageUrl;
    @Column(nullable = false,length = 100)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Cateogary cateogary;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Comments> comments = new HashSet<>();
}
