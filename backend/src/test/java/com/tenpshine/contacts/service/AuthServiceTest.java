package com.tenpshine.contacts.service;
import com.tenpshine.contacts.dto.AuthDtos.*;
import com.tenpshine.contacts.model.User;
import com.tenpshine.contacts.repository.*;
import com.tenpshine.contacts.security.JwtService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuthServiceTest{
 @Mock UserRepository users;@Mock ContactRepository contacts;@Mock PasswordEncoder encoder;@Mock JwtService jwt;@InjectMocks AuthService service;
 @BeforeEach void init(){MockitoAnnotations.openMocks(this);}
 @Test void registerHashesPasswordAndReturnsToken(){when(encoder.encode("password123")).thenReturn("hash");when(users.save(any())).thenAnswer(x->{User u=x.getArgument(0);u.setId(1L);return u;});when(jwt.create(any())).thenReturn("token");AuthResponse r=service.register(new RegisterRequest("Ahmar","a@test.com","+923001234567","password123"));assertEquals("token",r.token());verify(encoder).encode("password123");}
}
