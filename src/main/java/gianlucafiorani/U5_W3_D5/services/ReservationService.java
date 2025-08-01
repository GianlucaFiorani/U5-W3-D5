package gianlucafiorani.U5_W3_D5.services;

import gianlucafiorani.U5_W3_D5.entities.Event;
import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.BadRequestException;
import gianlucafiorani.U5_W3_D5.exceptions.NotFoundException;
import gianlucafiorani.U5_W3_D5.payloads.NewReservationDTO;
import gianlucafiorani.U5_W3_D5.repositories.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private EventService eventService;

    public Reservation save(NewReservationDTO payload, UUID userId) {
        User currentUser = usersService.findById(userId);
        Event currentEvent = eventService.findById(payload.eventId());
        if (this.reservationRepository.findByEventAndUser(currentEvent, currentUser).isEmpty()) {
            if (this.placesAvailable(payload.eventId()) > 0) {
                Reservation newReservation = new Reservation(currentEvent, currentUser);
                Reservation savedReservation = this.reservationRepository.save(newReservation);
                log.info("La prenotazione con id: " + savedReservation.getId() + " è stata salvata correttamente!");
                return savedReservation;
            } else {
                throw new BadRequestException("Non ci sono più posti disponibili per questo evento");
            }
        } else {
            throw new BadRequestException("Hai già effettuato una prenotazione per questo evento");
        }
    }

    public Reservation findById(UUID reservationId) {
        return this.reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    public Page<Reservation> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.reservationRepository.findAll(pageable);
    }

    public List<Reservation> findByUser(UUID userId) {
        return this.reservationRepository.findByUser(usersService.findById(userId));
    }

    public List<Reservation> findByEvent(UUID eventId) {
        return this.reservationRepository.findByEvent(eventService.findById(eventId));
    }

    public Optional<Reservation> findByEventAndUser(UUID eventId, UUID userId) {
        return this.reservationRepository.findByEventAndUser(eventService.findById(eventId), usersService.findById(userId));
    }

    public void findByIdAndDelete(UUID reservationId, UUID organizerId) {
        Reservation found = this.findById(reservationId);
        if (found.getUser().getId() != organizerId)
            throw new BadRequestException("Errore stai cercando di eliminare una prenotazione non tua");
        else {
            this.reservationRepository.delete(found);
        }
    }

    public int placesAvailable(UUID eventId) {
        return eventService.findById(eventId).getCapacity() - this.reservationRepository.placesBooked(eventService.findById(eventId));
    }
}
