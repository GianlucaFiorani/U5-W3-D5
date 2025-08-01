package gianlucafiorani.U5_W3_D5.controllers;


import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.payloads.NewUserDTO;
import gianlucafiorani.U5_W3_D5.payloads.RoleUpdate;
import gianlucafiorani.U5_W3_D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PutMapping("/role:{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateRole(@PathVariable UUID userId, @RequestBody RoleUpdate payload) {
        return this.usersService.updateRole(userId, payload);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getByIdAndUpdate(@PathVariable UUID userId, @RequestBody NewUserDTO payload) {
        return this.usersService.findByIdAndUpdate(userId, payload);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID userId) {
        this.usersService.findByIdAndDelete(userId);
    }

    @GetMapping("/me")
    public User getOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated NewUserDTO payload) {
        return this.usersService.findByIdAndUpdate(currentAuthenticatedUser.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.usersService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

}


