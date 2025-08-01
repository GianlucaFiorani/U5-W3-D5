package gianlucafiorani.U5_W3_D5.controllers;


import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.ValidationException;
import gianlucafiorani.U5_W3_D5.payloads.LoginDTO;
import gianlucafiorani.U5_W3_D5.payloads.LoginRespDTO;
import gianlucafiorani.U5_W3_D5.payloads.NewUserDTO;
import gianlucafiorani.U5_W3_D5.payloads.NewUserRespDTO;
import gianlucafiorani.U5_W3_D5.services.AuthService;
import gianlucafiorani.U5_W3_D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String accessToken = authService.checkCredentialsAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO save(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            User newUser = this.usersService.save(payload);
            return new NewUserRespDTO(newUser.getId());
        }

    }
}
