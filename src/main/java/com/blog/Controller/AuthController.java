package com.blog.Controller;

import com.blog.Dto.JwtAuthtokenResponse;
import com.blog.Dto.JwtAuthuserDetailsRequest;
import com.blog.Dto.UserDto;
import com.blog.Entity.User;
import com.blog.Exception.ApiException;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Security.JwtTokenHelper;
import com.blog.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthtokenResponse> createdToken(@RequestBody JwtAuthuserDetailsRequest jwtAuthuserDetailsRequest)
    {
    //we recives jwtAuthtokenResponse(token present in this class ) by pass JwtAuthuserdetailsRequest(username and pass present in this class)
     //the data which comes from Request body first we Authicate it from Authenticate manager first make this method
//authentication happens by authenticate method provided by AuthenticationManager

    this.authenticate(jwtAuthuserDetailsRequest.getUsername(), jwtAuthuserDetailsRequest.getPassword());

    //we get now user detail because we put it into generateToken method
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthuserDetailsRequest.getUsername());

        // 4️⃣ Fetch user from DB and map to UserDto
        UserDto userDto = this.userService.getAllUser()
                .stream()
                .filter(u -> u.getEmail().equals(jwtAuthuserDetailsRequest.getUsername()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User", "email",0L));


        //now put this intogenerateToken method
    String Token = this.jwtTokenHelper.generateToken(userDetails);

    //now we set this token in jwtAuthToken and send it into jwtauthresponse
    JwtAuthtokenResponse jwtAuthtokenResponse = new JwtAuthtokenResponse();
    jwtAuthtokenResponse.setToken(Token);
    jwtAuthtokenResponse.setUser(userDto);



    return new ResponseEntity<JwtAuthtokenResponse>(jwtAuthtokenResponse, HttpStatus.OK);
    }

    //Authenticatw method which authenticate username and password ...when any user send from frontend
    private void authenticate(String username,String password) throws ApiException
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        try {

            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }

        //this ApiException handeled globally if pass incorrect
    }





    //login ka hogya auth controller m ab register ka krege

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.RegisterNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }



}
