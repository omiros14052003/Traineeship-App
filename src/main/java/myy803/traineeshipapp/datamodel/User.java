package myy803.traineeshipapp.datamodel;

import java.util.Collection;
import java.util.Collections;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="username", unique=true, nullable=false)
    private String username;

    @Column(name="password", nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable=false)
    private Role role;

    public User() {}

    @Override
    public String getPassword() { return password; }
    
    public void setPassword(String encodedPassword) {this.password = encodedPassword; }

    @Override
    public String getUsername() { return username; }
    
    public void setUsername(String username) { this.username = username; }

    public Role getRole() { return role; }
    
    public void setRole(Role role) { this.role = role; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}