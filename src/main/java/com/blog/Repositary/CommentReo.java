package com.blog.Repositary;

import com.blog.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReo extends JpaRepository<Comments,Integer> {

}
