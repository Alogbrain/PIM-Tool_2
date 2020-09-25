package vn.elca.training.model.dto;

public class GroupDto {
    private Long groupId;

    public Long getId() {
        return groupId;
    }

    public void setId(Long id) {
        this.groupId = id;
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "groupId=" + groupId +
                '}';
    }
}
