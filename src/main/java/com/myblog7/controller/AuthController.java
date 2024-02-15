package com.myblog7.controller;



import com.myblog7.entity.User;
import com.myblog7.payload.JWTAuthResponse;
import com.myblog7.payload.LoginDto;
import com.myblog7.payload.SignUpDto;
import com.myblog7.repository.RoleRepository;
import com.myblog7.repository.UserRepository;
import com.myblog7.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")




public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired

    private JwtTokenProvider tokenProvider;


    @PostMapping("/signin")

    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);


// get token form tokenProvider

        String token = tokenProvider.generateToken(authentication);


        return ResponseEntity.ok(new JWTAuthResponse(token));

    }





//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate( new
//                UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.",
//                HttpStatus.OK);
//    }




//    //http://localhost:8080/api/auth/signup
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUDto) {
//
//        Boolean emailExist = userRepo.existsByEmail(signUDto.getEmail());
//        if (emailExist){
//            return new ResponseEntity<>("email Id already exist", HttpStatus.BAD_REQUEST);
//        }
//        Boolean emailUserName = userRepo.existsByUsername(signUDto.getUsername());
//        if (emailUserName){
//            return new ResponseEntity<>("User Name already exist", HttpStatus.BAD_REQUEST);
//        }
//
//        User user = new User();
//        user.setName(signUDto.getName());
//        user.setEmail(signUDto.getEmail());
//        user.setUsername(signUDto.getUsername());
//        user.setPassword(passwordEncoder.encode(signUDto.getPassword()));
//
//        userRepo.save(user);
//        return new ResponseEntity<>("user is registered", HttpStatus.CREATED);
//
//
//
//    }
}


