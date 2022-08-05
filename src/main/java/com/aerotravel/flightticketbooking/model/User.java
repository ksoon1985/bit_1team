package com.aerotravel.flightticketbooking.model;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable=true)
    private String firstname;

    @Column(name="middlename", nullable=true)
    private String middlename;

    @Column(nullable=true)
    private String lastname;

    @Column(nullable=false, unique=true)
    @NotBlank(message = "* Username is required")
    private String username;

    @Column(nullable=false)
    @NotBlank(message = "* Email is required")
    @Email(message="{errors.invalid_email}")
    private String email;

    @Column(nullable=false)
    @Size(min=8)
    private String password;

    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값


    @ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getFirstname()
    {
        return firstname;
    }
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }
    public String getMiddlename()
    {
        return firstname;
    }
    public void setMiddlename(String middlename)
    {
        this.middlename = middlename;
    }
    public String getLastnamename()
    {
        return lastname;
    }
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public List<Role> getRoles()
    {
        return roles;
    }
    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public User(String username, String password, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastname = lastname;

    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String username, String password, String email,String lastname, String provider, String providerId) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.lastname = lastname;
        this.provider = provider;
        this.providerId = providerId;
    }
    public User(){}

}
