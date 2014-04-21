package net.awesomepowered.dogegems;

public class DogeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DogeException() {
        super();
    }

    public DogeException(String message) {
        super(message);
    }

    public DogeException(Throwable cause) {
        super(cause);
    }

    public DogeException(String message, Throwable cause) {
        super(message, cause);
    }

}