package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/*
The "Role" class has a list of users, which means a user can have multiple roles,
and a role can be associated with a number of users.

The "User" and "Role" are plain old models representing a Spring Security user
and associated role in the application and database.
 */
/*
 * This is the entity class for Role.
 * It has a one-to-many relationship with the User class.
 */

// Lombok annotations to generate getters, setters, constructors and toString method
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity // Declares the class as an entity
@Table(name="roles") // Maps the entity to a table named "roles" in the database
public class Role {

    @Id // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.AUTO) // Specifies the automatic generation of the primary key
    @Column(name = "id", nullable = false) // Maps the column "id" in the table to the field "id" in the entity
    private Long id;

    @Column(nullable=false, unique=true) // Maps the column "name" in the table to the field "name" in the entity
    private String name;

    @ManyToMany(mappedBy="roles") // Declares the many-to-many relationship with the User class
    private List<User> users; // Stores the list of users associated with the role

    // Constructor to set the name of the role
    public Role(String name) {
        this.name = name;
    }
}
