package com.epam.hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@ToString(exclude = {"roles", "patient"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Integer id;


    @Column(name = "username",
            nullable = false,
            length = -1)
    private String username;


    @Column(name = "password",
            nullable = false,
            length = -1)
    private String password;

    @Transient
    private String confirmPassword;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Patient patient;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            schema = "public",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    nullable = false))
    private Set<Role> roles;

}