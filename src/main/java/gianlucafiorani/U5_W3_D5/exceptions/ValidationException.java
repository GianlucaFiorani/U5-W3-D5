package gianlucafiorani.U5_W3_D5.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {

    private List<String> errorMessages;

    public ValidationException(List<String> errorMessages) {
        super("Errori vari di validazione!");
        this.errorMessages = errorMessages;
    }
}
