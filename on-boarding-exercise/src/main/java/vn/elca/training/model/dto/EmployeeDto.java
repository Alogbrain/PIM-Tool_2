package vn.elca.training.model.dto;

public class EmployeeDto {
    private Long id;
    private String Visa;

    public String getVisa() {
        return Visa;
    }

    public void setVisa(String visa) {
        Visa = visa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
