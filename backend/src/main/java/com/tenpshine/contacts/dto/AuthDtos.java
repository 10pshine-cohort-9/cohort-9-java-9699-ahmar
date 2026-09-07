package com.tenpshine.contacts.dto;
import jakarta.validation.constraints.*;
public final class AuthDtos {
 private AuthDtos(){}
 public record RegisterRequest(@NotBlank @Size(max=100) String fullName,@NotBlank @Email String email,@NotBlank @Pattern(regexp="^[+0-9() -]{7,30}$") String phone,@NotBlank @Size(min=8,max=100) String password){}
 public record LoginRequest(@NotBlank String identifier,@NotBlank String password){}
 public record AuthResponse(String token,UserResponse user){}
 public record UserResponse(Long id,String fullName,String email,String phone,long contactCount){}
 public record ProfileRequest(@NotBlank @Size(max=100) String fullName,@NotBlank @Email String email,@NotBlank String phone){}
 public record PasswordRequest(@NotBlank String currentPassword,@NotBlank @Size(min=8,max=100) String newPassword){}
}
