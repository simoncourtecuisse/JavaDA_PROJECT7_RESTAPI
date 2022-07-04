package com.nnk.springboot.domain;

import com.nnk.springboot.services.ValidPassword;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "User")
@DynamicUpdate
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password")
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "full_name")
    @NotBlank(message = "FullName is mandatory")
    private String fullName;

    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && password.equals(user.password) && fullName.equals(user.fullName) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fullName, role);
    }
}
