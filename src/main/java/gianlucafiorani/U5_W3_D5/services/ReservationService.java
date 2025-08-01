package gianlucafiorani.U5_W3_D5.services;

import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.payloads.NewReservationDTO;
import gianlucafiorani.U5_W3_D5.repositories.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Reservation newReservation = new Reservation(eventService.findById(payload.eventId()), usersService.findById(userId));
        Reservation savedReservation = this.reservationRepository.save(newReservation);
        log.info("La prenotazione con id: " + savedReservation.getId() + " è stata salvata correttamente!");
        return savedReservation;
    }


    public List<Reservation> findByUser(UUID userId) {
        return this.reservationRepository.findByUser(usersService.findById(userId));
    }

    public List<Reservation> findByEvent(UUID eventId) {
        return this.reservationRepository.findByEvent(eventService.findById(eventId));
    }
}
