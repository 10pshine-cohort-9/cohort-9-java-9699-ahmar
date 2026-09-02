package com.tenpshine.contacts.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
@Entity @Table(name="app_users",uniqueConstraints={@UniqueConstraint(columnNames="email"),@UniqueConstraint(columnNames="phone")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String fullName;
 @Column(nullable=false,length=150) private String email;
 @Column(nullable=false,length=30) private String phone;
 @JsonIgnore @Column(nullable=false) private String password;
 @Column(nullable=false,updatable=false) @Builder.Default private Instant createdAt=Instant.now();
}
