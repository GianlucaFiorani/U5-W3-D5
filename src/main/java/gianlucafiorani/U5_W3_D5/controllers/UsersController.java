package gianlucafiorani.U5_W3_D5.controllers;


import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.usersService.findAll(page, size, sortBy);
    }
}


