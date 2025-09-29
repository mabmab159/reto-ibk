package mabmab.retoibk.reto.domain.exceptions;

public class ConcurrenciaException extends RuntimeException {
    public ConcurrenciaException(String message) {
        super(message);
    }
}