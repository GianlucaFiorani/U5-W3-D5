package gianlucafiorani.U5_W3_D5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(@NotEmpty(message = "Il nome è obbligatorio!")
                         @Size(min = 2, max = 40, message = "Il nome deve essere di lunghezza compresa tra 2 e 40")
                         String name,
                         @NotEmpty(message = "Il cognome è obbligatorio!")
                         @Size(min = 2, max = 40, message = "Il cognome deve essere di lunghezza compresa tra 2 e 40")
                         String surname,
                         @NotEmpty(message = "L'indirizzo email è obbligatorio")
                         @Email(message = "L'indirizzo email inserito non è nel formato giusto")
                         String email,
                         @NotEmpty(message = "La password email è obbligatorio")
                         @Size(min = 4)
                         String password

) {
}
