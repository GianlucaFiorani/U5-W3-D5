package gianlucafiorani.U5_W3_D5.controllers;

import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.ValidationException;
import gianlucafiorani.U5_W3_D5.payloads.NewReservationDTO;
import gianlucafiorani.U5_W3_D5.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reserv")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Reservation> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.reservationService.findAll(page, size, sortBy);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewReservationDTO save(@RequestBody @Validated NewReservationDTO payload, BindingResult validationResult, @AuthenticationPrincipal User currentAuthenticatedUser) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Reservation newReservation = this.reservationService.save(payload, currentAuthenticatedUser.getId());
            return new NewReservationDTO(newReservation.getId());
        }

    }

    @GetMapping("/me")
    public Page<Reservation> getReservationList(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return reservationService.findByUser(currentAuthenticatedUser.getId());
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID reservationId, @AuthenticationPrincipal User currentAuthenticatedUser) {
        this.reservationService.findByIdAndDelete(reservationId, currentAuthenticatedUser.getId());
    }

}
