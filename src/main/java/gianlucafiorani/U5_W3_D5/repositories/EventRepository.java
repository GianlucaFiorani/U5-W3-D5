package gianlucafiorani.U5_W3_D5.repositories;

import gianlucafiorani.U5_W3_D5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
}
