package vn.elca.training.model.exception;

public class ConcurrentUpdateException extends Exception{
    public ConcurrentUpdateException(){
        super("This project is updated by other user. You need to reload page to update project.");
    }

    public ConcurrentUpdateException(Throwable cause) {
        super(cause);
    }
}
