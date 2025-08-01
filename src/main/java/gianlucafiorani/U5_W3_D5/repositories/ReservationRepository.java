package gianlucafiorani.U5_W3_D5.repositories;


import gianlucafiorani.U5_W3_D5.entities.Event;
import gianlucafiorani.U5_W3_D5.entities.Reservation;
import gianlucafiorani.U5_W3_D5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Optional<Reservation> findByEventAndUser(Event event, User user);

    List<Reservation> findByUser(User user);

    List<Reservation> findByEvent(Event event);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.event = :event")
    int placesBooked(Event event);
}
