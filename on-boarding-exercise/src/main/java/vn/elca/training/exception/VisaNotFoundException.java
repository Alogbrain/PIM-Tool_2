package vn.elca.training.exception;

public class VisaNotFoundException extends RuntimeException{
    public VisaNotFoundException(){}
    private String message = "The following visas do not exist: {visa list}";

    @Override
    public String getMessage() {
        return message;
    }
}
