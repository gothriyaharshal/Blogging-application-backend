package com.blog.Controller;

import com.blog.Dto.ApiResponse;
import com.blog.Dto.UserDto;
import com.blog.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> creating(@Valid @RequestBody UserDto userDto) {
        UserDto created = this.userService.createUser(userDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updating(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        UserDto updated = this.userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleting(@PathVariable("userId") Integer userId) {

        this.userService.deleteUserId(userId);
        return new ResponseEntity<>(new ApiResponse("user Deleted" , true) ,HttpStatus.OK);

    }


    @GetMapping("/")
   public ResponseEntity<List<UserDto>> getAlluser()
   {
       List<UserDto> allUser = this.userService.getAllUser();
       return new ResponseEntity<>(allUser , HttpStatus.OK);
   }

   @GetMapping("/{userId}")
   public ResponseEntity<UserDto> getOne(@PathVariable("userId") Integer userId )
   {
       UserDto userById = this.userService.getUserById(userId);
       return  new ResponseEntity<>(userById ,HttpStatus.OK);
   }
}