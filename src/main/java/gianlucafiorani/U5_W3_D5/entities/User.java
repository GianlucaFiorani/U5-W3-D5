package gianlucafiorani.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatarURL;
    @Enumerated(EnumType.STRING)
    private Role role;


    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Questo Metodo deve tornare una lista di ruoli dell'utente. Per essere più precisi vuole che venga restituita una collection di oggetti
        // che estendono GrantedAuthority. SimpleGrantedAuthority è una classe che rappresenta i ruoli degli utenti nel mondo Spring Security, essa
        // estende GrantedAuthority. Dobbiamo passare il nostro ruolo (enum), convertito in string al costruttore dell'oggetto
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
