package vn.elca.training.model.exception;

public class DeteleProjectNotNewStatusException extends Exception {
    public DeteleProjectNotNewStatusException(Integer number) {
        super(String.format("Project %d have status is not new", number));
    }
}
