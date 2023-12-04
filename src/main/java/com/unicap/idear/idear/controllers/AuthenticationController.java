package com.unicap.idear.idear.controllers;

import com.unicap.idear.idear.dtos.AuthenticationDTO;
import com.unicap.idear.idear.dtos.RegisterDTO;
import com.unicap.idear.idear.dtos.UserResponseDTO;
import com.unicap.idear.idear.infra.TokenService;
import com.unicap.idear.idear.models.UserModel;
import com.unicap.idear.idear.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @PostMapping ("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new UserResponseDTO(token));
    }

    @PostMapping ("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        if(this.userRepository.findByUsername(registerDTO.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserModel userModel = new UserModel (registerDTO.username(),registerDTO.email(), encryptedPassword,registerDTO.role());
        this.userRepository.save(userModel);
        return ResponseEntity.ok().build();
    }
}
