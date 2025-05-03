package exceptions;

import exceptions.intfs.BadRequest;

public class InvalidPageRequest extends RuntimeException implements BadRequest {
    public InvalidPageRequest(String message) {
        super(message);
    }

    public InvalidPageRequest(String message, Throwable cause) {
        super(message, cause);
    }
}
