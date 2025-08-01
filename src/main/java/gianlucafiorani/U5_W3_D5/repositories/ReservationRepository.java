package gianlucafiorani.U5_W3_D5.repositories;


import gianlucafiorani.U5_W3_D5.entities.Event;
import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Optional<Reservation> findByEventAndUser(Event event, User user);

    Page<Reservation> findByUser(User user);

    Page<Reservation> findByEvent(Event event);
}
