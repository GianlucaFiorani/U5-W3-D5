package gianlucafiorani.U5_W3_D5.controllers;

import gianlucafiorani.U5_W3_D5.entities.Event;
import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.ValidationException;
import gianlucafiorani.U5_W3_D5.payloads.NewEventDTO;
import gianlucafiorani.U5_W3_D5.payloads.NewEventRespDTO;
import gianlucafiorani.U5_W3_D5.services.EventService;
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
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Event> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.eventService.findAll(page, size, sortBy);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventRespDTO save(@RequestBody @Validated NewEventDTO payload, BindingResult validationResult, @AuthenticationPrincipal User currentAuthenticatedUser) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Event newEvent = this.eventService.save(payload, currentAuthenticatedUser.getId());
            return new NewEventRespDTO(newEvent.getId());
        }
    }

    @GetMapping("/{eventId}")
    public Event getById(@PathVariable UUID eventId) {
        return this.eventService.findById(eventId);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public Event getByIdAndUpdate(@PathVariable UUID eventId, @AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody NewEventDTO payload) {
        return this.eventService.findByIdAndUpdate(eventId, currentAuthenticatedUser.getId(), payload);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID eventId, @AuthenticationPrincipal User currentAuthenticatedUser) {
        this.eventService.findByIdAndDelete(eventId, currentAuthenticatedUser.getId());
    }
}

