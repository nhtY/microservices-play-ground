package exceptions;

import exceptions.intfs.BadRequest;

public class AlreadyExistsException extends RuntimeException implements BadRequest {
    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
