package com.blog.Controller;

import com.blog.Dto.ApiResponse;
import com.blog.Dto.CommentDto;
import com.blog.Service.ComentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ComentController {

    @Autowired
    public ComentService comentService;

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> creatingComment
            (@RequestBody CommentDto commentDto
            ,@PathVariable("postId") Integer postId

            )
    {
        CommentDto commentDto1 = this.comentService.creatingComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.OK);

    }

    @DeleteMapping("/comments/{commentId}")
   public ResponseEntity<ApiResponse> deleting(@PathVariable("commentId") Integer commentId)
   {
      this.comentService.DeletingComment(commentId);
      return new ResponseEntity<ApiResponse>(new ApiResponse("comment Deleted Succesfully",true),HttpStatus.OK);
   }


}
