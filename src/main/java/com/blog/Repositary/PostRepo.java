package com.blog.Repositary;

import com.blog.Entity.Cateogary;
import com.blog.Entity.Post;
import com.blog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    public List<Post> findByUser(User user);
   public List<Post> findByCateogary(Cateogary cateogary);

   public List<Post> findByTitleContaining(String title);


  }
