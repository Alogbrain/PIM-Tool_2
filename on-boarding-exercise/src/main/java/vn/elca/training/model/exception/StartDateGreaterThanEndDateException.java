package vn.elca.training.model.exception;

public class StartDateGreaterThanEndDateException extends RuntimeException{
    public StartDateGreaterThanEndDateException(){
        super("End date must is greater than start date");
    }
}
