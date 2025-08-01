package gianlucafiorani.U5_W3_D5.services;

import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.exceptions.BadRequestException;
import gianlucafiorani.U5_W3_D5.payloads.NewReservationDTO;
import gianlucafiorani.U5_W3_D5.repositories.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (this.reservationRepository.findByEventAndUser(eventService.findById(payload.eventId()), usersService.findById(userId)).isEmpty()) {
            Reservation newReservation = new Reservation(eventService.findById(payload.eventId()), usersService.findById(userId));
            Reservation savedReservation = this.reservationRepository.save(newReservation);
            log.info("La prenotazione con id: " + savedReservation.getId() + " è stata salvata correttamente!");
            return savedReservation;
        } else {
            throw new BadRequestException("Hai già effettuato una prenotazione per questo evento");
        }
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
}
