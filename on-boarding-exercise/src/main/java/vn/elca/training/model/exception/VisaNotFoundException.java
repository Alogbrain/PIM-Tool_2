package vn.elca.training.model.exception;

import java.util.List;

public class VisaNotFoundException extends RuntimeException {
    public VisaNotFoundException(List<String> allVisa){
        super(String.format(" %s", allVisa));
    }
}
