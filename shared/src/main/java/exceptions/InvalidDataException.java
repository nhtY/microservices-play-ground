package exceptions;

import exceptions.intfs.BadRequest;

public class InvalidDataException extends RuntimeException implements BadRequest {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
