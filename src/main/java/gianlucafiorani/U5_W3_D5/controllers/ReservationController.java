package gianlucafiorani.U5_W3_D5.controllers;

import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.ValidationException;
import gianlucafiorani.U5_W3_D5.payloads.NewReservationDTO;
import gianlucafiorani.U5_W3_D5.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserv")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewReservationDTO save(@RequestBody @Validated NewReservationDTO payload, @AuthenticationPrincipal User currentAuthenticatedUser, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Reservation newReservation = this.reservationService.save(payload, currentAuthenticatedUser.getId());
            return new NewReservationDTO(newReservation.getId());
        }

    }
}
