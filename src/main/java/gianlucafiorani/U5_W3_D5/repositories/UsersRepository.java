package gianlucafiorani.U5_W3_D5.repositories;

import gianlucafiorani.U5_W3_D5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
