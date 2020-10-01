package vn.elca.training.model.exception;

public class StartDateGreaterThanEndDateException extends Exception{
    public StartDateGreaterThanEndDateException(){
        super("End date must be greater than start date");
    }
}
