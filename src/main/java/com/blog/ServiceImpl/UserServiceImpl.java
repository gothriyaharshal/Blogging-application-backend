package com.blog.ServiceImpl;

import com.blog.Dto.UserDto;
import com.blog.Entity.Role;
import com.blog.Entity.User;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Repositary.RoleRepo;
import com.blog.Repositary.UserRepo;
import com.blog.Service.UserService;
import com.blog.config.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoTouser(userDto);
        User save = this.userRepo.save(user);


        return this.userTodto(save);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user" , "id" , userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User save = this.userRepo.save(user);

        return this.userTodto(save);



    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user" , "id" , userId));

        return this.userTodto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> all = this.userRepo.findAll();
        List<UserDto> collect = all.stream().map(users -> this.userTodto(users)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void deleteUserId(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user" , "id" , userId));
        this.userRepo.delete(user);

    }

    @Override
    public UserDto RegisterNewUser(UserDto userDto) {
        //first converting UserDto to user by modelmapper\
        User user = this.modelMapper.map(userDto, User.class);

        //before saving this user to database first we encode userpass
        //for this autowired passwordEncoder
        //and declare it's role(admin/normal)
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //bydefault if anyone comes form ("api/register") assign role as normal user
        //Autowired RoleRepo for saving role in database
        //role_admin=876 and role_normal=987 updated from workbench only for testing
        //this.roleRepo.findById(876) passing here for testing
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        //user m pass encode krdia or hr baar usse normal user ki id de rhe h


        //ab db m save kr dege
        User user1 = this.userRepo.save(user);

        return this.modelMapper.map(user1,UserDto.class);
    }


    public UserDto userTodto(User user)
    {
        UserDto userDto=new UserDto();
        UserDto map = this.modelMapper.map(user, UserDto.class);
       return map;
    }

    public User dtoTouser(UserDto userDto)
    {
       User user=new User();
        User map = this.modelMapper.map(userDto, User.class);
        return map;
    }





}
