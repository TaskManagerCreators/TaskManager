package com.model;

import com.secutiry.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * This class describes a user.
 * The class implements special interface what describes authentication object
 * @see UserDetails
 */

@Component
@ComponentScan(basePackages = "com")
@Document(collection = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    //@Id
    //ObjectId id;

    private List<Role> authorities;

    private boolean isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled;

    private String name;

    //@Indexed(unique = true)
    @Id
    private String email;

    private String description;

    private String password;

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;// isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

