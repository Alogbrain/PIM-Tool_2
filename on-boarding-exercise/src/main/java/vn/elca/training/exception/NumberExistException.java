package vn.elca.training.exception;

public class NumberExistException extends RuntimeException{
    public NumberExistException(Integer id){
//        super(String.format("The project number %d is existed. Please select a different project number", id));
    }
    private String message = "The project number is existed. Please select a different project number";

    @Override
    public String getMessage() {
        return message;
    }
}
