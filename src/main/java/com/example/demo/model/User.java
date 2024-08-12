package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "User")
public class User implements Serializable,UserDetails{

    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));

	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getUsername();
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
	    public boolean isEnabled() {
	        return true;
	    }

		

	

    
}