package gianlucafiorani.U5_W3_D5.services;

import gianlucafiorani.U5_W3_D5.entities.Event;
import gianlucafiorani.U5_W3_D5.exceptions.BadRequestException;
import gianlucafiorani.U5_W3_D5.exceptions.NotFoundException;
import gianlucafiorani.U5_W3_D5.payloads.NewEventDTO;
import gianlucafiorani.U5_W3_D5.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UsersService usersService;

    public Event findById(UUID eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public Event save(NewEventDTO payload, UUID organizerId) {
        Event newEvent = new Event(payload.title(), payload.description(), payload.date(), payload.location(), payload.capacity(), usersService.findById(organizerId));
        Event savedEvent = this.eventRepository.save(newEvent);
        log.info("L'evento con id: " + savedEvent.getId() + " è stato salvato correttamente!");
        return savedEvent;
    }

    public void findByIdAndDelete(UUID eventId, UUID organizerId) {
        Event found = this.findById(eventId);
        if (found.getOrganizer().getId() != organizerId)
            throw new BadRequestException("Stai cercando di modificare un evento non tuo");
        else {
            this.eventRepository.delete(found);
        }
    }


    public Event findByIdAndUpdate(UUID eventId, UUID organizerId, NewEventDTO payload) {
        Event found = this.findById(eventId);
        if (found.getOrganizer().getId() != organizerId)
            throw new BadRequestException("Stai cercando di modificare un evento non tuo");
        else {
            found.setTitle(payload.title());
            found.setDescription(payload.description());
            found.setDate(payload.date());
            found.setLocation(payload.location());
            Event modifiedEvent = this.eventRepository.save(found);
            log.info("L'evento con id " + found.getId() + " è stato modificato!");
            return modifiedEvent;
        }
    }
}
