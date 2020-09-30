package vn.elca.training.model.exception;

import java.util.List;

public class ConcurrentUpdateException extends RuntimeException{
    public ConcurrentUpdateException(){
        super("this project is already updated by other user. you need to reload page to update project");
    }
}
