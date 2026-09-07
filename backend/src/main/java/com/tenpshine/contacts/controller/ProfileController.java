package com.tenpshine.contacts.controller;
import com.tenpshine.contacts.dto.AuthDtos.*;
import com.tenpshine.contacts.model.User;
import com.tenpshine.contacts.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/profile") public class ProfileController{
 private final AuthService auth;public ProfileController(AuthService a){auth=a;}
 @GetMapping UserResponse get(@AuthenticationPrincipal User u){return auth.view(u);}
 @PutMapping UserResponse update(@AuthenticationPrincipal User u,@Valid @RequestBody ProfileRequest r){return auth.update(u,r);}
 @PutMapping("/password") @ResponseStatus(HttpStatus.NO_CONTENT) void password(@AuthenticationPrincipal User u,@Valid @RequestBody PasswordRequest r){auth.password(u,r);}
}
