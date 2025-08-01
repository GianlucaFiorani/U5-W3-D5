package gianlucafiorani.U5_W3_D5.payloads;

import java.util.UUID;

public record NewReservationDTO(UUID eventId, UUID userId) {
}
