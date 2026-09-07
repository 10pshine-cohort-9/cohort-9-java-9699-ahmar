package com.tenpshine.contacts.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;
@Entity @Table(name="contacts") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Contact {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=80) private String firstName;
 @Column(nullable=false,length=80) private String lastName;
 @Column(length=100) private String title;
 @ElementCollection @CollectionTable(name="contact_emails",joinColumns=@JoinColumn(name="contact_id")) @Column(name="email",nullable=false) @Builder.Default private List<String> emails=new ArrayList<>();
 @ElementCollection @CollectionTable(name="contact_phones",joinColumns=@JoinColumn(name="contact_id")) @Column(name="phone",nullable=false) @Builder.Default private List<String> phoneNumbers=new ArrayList<>();
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="owner_id") private User owner;
 @Column(nullable=false,updatable=false) @Builder.Default private Instant createdAt=Instant.now();
 @Column(nullable=false) @Builder.Default private Instant updatedAt=Instant.now();
 @PreUpdate void touch(){updatedAt=Instant.now();}
}
