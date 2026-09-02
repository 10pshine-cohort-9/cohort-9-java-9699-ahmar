package com.tenpshine.contacts.service;
import com.tenpshine.contacts.dto.AuthDtos.*;
import com.tenpshine.contacts.exception.ApiException;
import com.tenpshine.contacts.model.User;
import com.tenpshine.contacts.repository.*;
import com.tenpshine.contacts.security.JwtService;
import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service public class AuthService{
 private static final Logger log=LoggerFactory.getLogger(AuthService.class);private final UserRepository users;private final ContactRepository contacts;private final PasswordEncoder passwords;private final JwtService jwt;
 public AuthService(UserRepository u,ContactRepository c,PasswordEncoder p,JwtService j){users=u;contacts=c;passwords=p;jwt=j;}
 @Transactional public AuthResponse register(RegisterRequest r){if(users.existsByEmailIgnoreCase(r.email()))throw new ApiException(HttpStatus.CONFLICT,"Email is already registered");if(users.existsByPhone(r.phone()))throw new ApiException(HttpStatus.CONFLICT,"Phone is already registered");User u=users.save(User.builder().fullName(r.fullName().trim()).email(r.email().trim().toLowerCase()).phone(r.phone().trim()).password(passwords.encode(r.password())).build());log.info("Registered user {}",u.getId());return new AuthResponse(jwt.create(u.getEmail()),view(u));}
 public AuthResponse login(LoginRequest r){User u=users.findByEmailIgnoreCase(r.identifier().trim()).or(()->users.findByPhone(r.identifier().trim())).orElseThrow(()->new BadCredentialsException("Invalid credentials"));if(!passwords.matches(r.password(),u.getPassword()))throw new BadCredentialsException("Invalid credentials");log.info("Successful login for user {}",u.getId());return new AuthResponse(jwt.create(u.getEmail()),view(u));}
 public UserResponse view(User u){return new UserResponse(u.getId(),u.getFullName(),u.getEmail(),u.getPhone(),contacts.countByOwnerId(u.getId()));}
 @Transactional public UserResponse update(User u,ProfileRequest r){if(!u.getEmail().equalsIgnoreCase(r.email())&&users.existsByEmailIgnoreCase(r.email()))throw new ApiException(HttpStatus.CONFLICT,"Email is already in use");if(!u.getPhone().equals(r.phone())&&users.existsByPhone(r.phone()))throw new ApiException(HttpStatus.CONFLICT,"Phone is already in use");u.setFullName(r.fullName().trim());u.setEmail(r.email().trim().toLowerCase());u.setPhone(r.phone().trim());return view(users.save(u));}
 @Transactional public void password(User u,PasswordRequest r){if(!passwords.matches(r.currentPassword(),u.getPassword()))throw new ApiException(HttpStatus.BAD_REQUEST,"Current password is incorrect");u.setPassword(passwords.encode(r.newPassword()));users.save(u);}
}
