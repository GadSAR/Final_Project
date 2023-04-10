package com.backend.ifm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
The "User" and "Role" are plain old models representing a
Spring Security user and associated role
in the application and database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User
{
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO): JPA annotation to specify the strategy for generating primary key values.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    // Specifies a many-to-many relationship between User and Role entities.
    // FetchType.EAGER loads all roles of a user at once.
    // CascadeType.ALL specifies that all operations (persist, remove, refresh, merge, and detach) should be cascaded.
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    // Specifies the join table name and columns for the relationship.
    // The "joinColumns" element specifies the mapping for the owning side of the association.
    // The "inverseJoinColumns" element specifies the mapping for the non-owning side of the association.
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

    public User(String name, String email, String password, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}

/*
@Getter: Lombok annotation to generate getters for all fields.
@Setter: Lombok annotation to generate setters for all fields.
@NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
@AllArgsConstructor: Lombok annotation to generate a constructor with all arguments.
@Entity: JPA annotation to mark this class as an entity.
@Table(name="users"): JPA annotation to specify the name of the table in the database.
@Id: JPA annotation to specify the primary key field.
@GeneratedValue(strategy = GenerationType.AUTO): JPA annotation to specify the strategy for generating primary key values.
@Column(name = "id", nullable = false): JPA annotation to specify the column name and constraints for the field.
@Column(nullable = false, unique = true, length = 50): JPA annotation to specify the column name and constraints for the field.
@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL): JPA annotation to specify a
 many-to-many relationship between User and Role entities.
@JoinTable: JPA annotation to specify the join table name and columns for the relationship.
private List<Role> roles = new ArrayList<>();: Defines a list of roles associated with this user.
 */