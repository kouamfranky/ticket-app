package com.kouamfranky.ticketapp.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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
@Table(name = "APP_USER")
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String telephone;
    private String adresse;
    private String nom;
    private String prenom;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Ticket> tickets;

    public static final class UserBuilder {
        private Long id;
        private String email;
        private String username;
        private String password;
        private String telephone;
        private String adresse;
        private String nom;
        private String prenom;

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
            return user;
        }
    }
}
