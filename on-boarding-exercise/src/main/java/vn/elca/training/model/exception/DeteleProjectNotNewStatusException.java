package vn.elca.training.model.exception;

public class DeteleProjectNotNewStatusException extends RuntimeException {
    public DeteleProjectNotNewStatusException() {
        super("Project status must be new to delete");
    }
}
