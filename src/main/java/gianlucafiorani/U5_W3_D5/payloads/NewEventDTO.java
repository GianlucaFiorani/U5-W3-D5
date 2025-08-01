package gianlucafiorani.U5_W3_D5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(@NotEmpty(message = "Il titolo è obbligatorio!")
                          @Size(min = 2, max = 40, message = "Il titolo deve essere di lunghezza compresa tra 2 e 40")
                          String title,
                          String description,
                          @NotNull(message = "La data è obbligatorio!")
                          LocalDate date,
                          @NotNull(message = "Il luogo è obbligatorio!")
                          String location,
                          @NotNull(message = "La capienza è obbligatorio!")
                          int capacity
) {
}
