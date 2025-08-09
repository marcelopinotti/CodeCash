package br.com.project.expction;

public class PixInUseException extends RuntimeException {
    public PixInUseException(String message) {
        super(message);
    }
}
