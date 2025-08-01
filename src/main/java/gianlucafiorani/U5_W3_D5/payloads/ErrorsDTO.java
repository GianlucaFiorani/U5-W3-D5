package gianlucafiorani.U5_W3_D5.payloads;


import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
