package gianlucafiorani.U5_W3_D5.services;


import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.UnauthorizedException;
import gianlucafiorani.U5_W3_D5.payloads.LoginDTO;
import gianlucafiorani.U5_W3_D5.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        User found = this.usersService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}