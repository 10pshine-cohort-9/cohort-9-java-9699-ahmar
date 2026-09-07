package com.tenpshine.contacts.controller;
import com.tenpshine.contacts.dto.ContactDtos.*;
import com.tenpshine.contacts.model.User;
import com.tenpshine.contacts.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/contacts") public class ContactController{
 private final ContactService service;public ContactController(ContactService s){service=s;}
 @GetMapping Page<ContactResponse> list(@AuthenticationPrincipal User u,@RequestParam(defaultValue="")String search,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="10")int size){return service.list(u,search,page,size);}
 @GetMapping("/{id}") ContactResponse get(@AuthenticationPrincipal User u,@PathVariable Long id){return service.get(u,id);}
 @PostMapping ResponseEntity<ContactResponse> create(@AuthenticationPrincipal User u,@Valid @RequestBody ContactRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(u,r));}
 @PutMapping("/{id}") ContactResponse update(@AuthenticationPrincipal User u,@PathVariable Long id,@Valid @RequestBody ContactRequest r){return service.update(u,id,r);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) void delete(@AuthenticationPrincipal User u,@PathVariable Long id){service.delete(u,id);}
}
