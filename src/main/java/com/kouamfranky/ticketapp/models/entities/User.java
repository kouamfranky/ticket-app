package com.kouamfranky.ticketapp.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:44
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.entities
 **/
@Entity
@Getter
@Setter
@Table(name = "APP_USER",indexes = {
        @Index(name = "UK_EMAIL_USER", columnList = "email", unique = true),
        @Index(name = "UK_USERNAME_USER", columnList = "username", unique = true),
})
public class User extends AuditModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 255)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(min = 5, max = 20)
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String telephone;
    private String adresse;
    private String nom;
    private String prenom;
    @Column(name = "active")
    private Boolean active = true;
    @Column(name = "roles", nullable = false, length = 500)
    private String roles;

    @Transient
    private Collection<String> roleCollection = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Ticket> tickets;

    public Map<String, Object> toMap() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", getId());
        map.put("username", this.username);
        map.put("email", this.email);
        map.put("nom", this.nom);
        map.put("password", "[PROTECTED]");
        return map;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getActive();
    }



    @Override
    public boolean isCredentialsNonExpired() {
        return this.getActive();
    }

    @Override
    public boolean isEnabled() {
        return this.getActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String rolesString = getRoles();
        if (rolesString != null && !rolesString.isEmpty()) {
            String[] roleNames = rolesString.split(",");
            for (String roleName : roleNames) {
                String trimmedRole = roleName.trim();
                if (!trimmedRole.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority(trimmedRole));
                }
            }
        }
        return authorities;
    }

    public User(){
    }
    public User(User user) {
        this.setId(user.getId());
        this.nom = user.getNom();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.setActive(user.getActive());
        this.roles = user.getRoles();

        String rolesString = user.getRoles();
        if (rolesString != null && !rolesString.isEmpty()) {
            String[] roleNames = rolesString.split(",");
            for (String roleName : roleNames) {
                String trimmedRole = roleName.trim();
                if (!trimmedRole.isEmpty()) {
                    roleCollection.add(trimmedRole);
                }
            }
        }
    }
    public static final class UserBuilder {
        private Long id;
        private String email;
        private String username;
        private String password;
        private String telephone;
        private String adresse;
        private String nom;
        private String prenom;
        private Boolean active;
        private String roles;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public UserBuilder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        public UserBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public UserBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public UserBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public UserBuilder roles(String roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setTelephone(telephone);
            user.setAdresse(adresse);
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setActive(active);
            user.setRoles(roles);
            return user;
        }
    }
}
