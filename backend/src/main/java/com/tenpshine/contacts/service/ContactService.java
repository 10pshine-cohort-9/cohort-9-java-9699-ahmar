package com.tenpshine.contacts.service;
import com.tenpshine.contacts.dto.ContactDtos.*;
import com.tenpshine.contacts.exception.ApiException;
import com.tenpshine.contacts.model.*;
import com.tenpshine.contacts.repository.ContactRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service public class ContactService{
 private final ContactRepository contacts;public ContactService(ContactRepository c){contacts=c;}
 @Transactional(readOnly=true) public Page<ContactResponse> list(User u,String q,int page,int size){return contacts.search(u.getId(),q==null?"":q.trim(),PageRequest.of(page,Math.min(size,50),Sort.by("firstName").ascending())).map(this::view);}
 @Transactional(readOnly=true) public ContactResponse get(User u,Long id){return view(entity(u,id));}
 @Transactional public ContactResponse create(User u,ContactRequest r){Contact c=Contact.builder().owner(u).build();apply(c,r);return view(contacts.save(c));}
 @Transactional public ContactResponse update(User u,Long id,ContactRequest r){Contact c=entity(u,id);apply(c,r);return view(contacts.save(c));}
 @Transactional public void delete(User u,Long id){contacts.delete(entity(u,id));}
 private Contact entity(User u,Long id){return contacts.findByIdAndOwnerId(id,u.getId()).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Contact not found"));}
 private void apply(Contact c,ContactRequest r){c.setFirstName(r.firstName().trim());c.setLastName(r.lastName().trim());c.setTitle(r.title()==null?"":r.title().trim());c.setEmails(r.emails().stream().map(String::trim).filter(x->!x.isBlank()).distinct().toList());c.setPhoneNumbers(r.phoneNumbers().stream().map(String::trim).filter(x->!x.isBlank()).distinct().toList());}
 private ContactResponse view(Contact c){return new ContactResponse(c.getId(),c.getFirstName(),c.getLastName(),c.getTitle(),List.copyOf(c.getEmails()),List.copyOf(c.getPhoneNumbers()),c.getCreatedAt(),c.getUpdatedAt());}
}
