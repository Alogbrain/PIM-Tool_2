package vn.elca.training.model.exception;

import java.util.List;

public class ConcurrentUpdateException extends RuntimeException{
    public ConcurrentUpdateException(){
        super("Need to reload page to update project");
    }
}
