package com.ecowaste.recycling.entity;

import com.ecowaste.recycling.enums.EmailNotification;
import com.ecowaste.recycling.enums.Role;
import com.ecowaste.recycling.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.IntegerJdbcType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String username;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.ORDINAL)
    @JdbcType(IntegerJdbcType.class)
    private UserStatus userStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(value = EnumType.ORDINAL)
    @JdbcType(IntegerJdbcType.class)
    private EmailNotification emailNotification;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private UserRegistration userRegistration;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private VerifyEmail verifyEmail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Habit> habits;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Goal> goals;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<RecyclingPoint> points;

    @Column(nullable = false)
    private LocalDateTime dateOfRegistration;

    @Column(nullable = false)
    private LocalDateTime lastActivityTime;

    private String refreshTokenKey;

    private String profilePicturePath;

    private String city;

    @Column(columnDefinition = "varchar(60)")
    private String uuid;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}