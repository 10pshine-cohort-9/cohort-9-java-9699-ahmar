package com.tenpshine.contacts.dto;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;
public final class ContactDtos {
 private ContactDtos(){}
 public record ContactRequest(@NotBlank @Size(max=80) String firstName,@NotBlank @Size(max=80) String lastName,@Size(max=100) String title,@NotEmpty List<@Email String> emails,@NotEmpty List<@NotBlank String> phoneNumbers){}
 public record ContactResponse(Long id,String firstName,String lastName,String title,List<String> emails,List<String> phoneNumbers,Instant createdAt,Instant updatedAt){}
}
