package exceptions;

public class NotSuchAlgorithmException extends Exception {

    public NotSuchAlgorithmException() {
        super("No such algorithm");
    }
}
