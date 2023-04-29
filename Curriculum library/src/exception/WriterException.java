package exception;

public class WriterException extends Exception{
    public WriterException() {
        super();
    }
    public WriterException(String message) {
        super(message);
    }

    public WriterException(String message, Exception e) {
        super(message, e);
    }
}
