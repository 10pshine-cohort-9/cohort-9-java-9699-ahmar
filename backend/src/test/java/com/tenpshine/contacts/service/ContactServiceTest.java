package com.tenpshine.contacts.service;
import com.tenpshine.contacts.dto.ContactDtos.*;
import com.tenpshine.contacts.model.*;
import com.tenpshine.contacts.repository.ContactRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ContactServiceTest{
 @Mock ContactRepository repo;@InjectMocks ContactService service;
 User user;@BeforeEach void setUp(){MockitoAnnotations.openMocks(this);user=User.builder().id(7L).build();}
 @Test void createsContactOwnedByAuthenticatedUser(){when(repo.save(any())).thenAnswer(x->{Contact c=x.getArgument(0);c.setId(1L);return c;});ContactResponse result=service.create(user,new ContactRequest("Ali","Khan","Manager",List.of("ali@example.com"),List.of("+923001234567")));assertEquals("Ali",result.firstName());verify(repo).save(argThat(c->c.getOwner()==user));}
 @Test void rejectsAccessToAnotherUsersContact(){when(repo.findByIdAndOwnerId(1L,7L)).thenReturn(Optional.empty());assertThrows(RuntimeException.class,()->service.get(user,1L));}
}
