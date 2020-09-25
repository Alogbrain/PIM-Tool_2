package vn.elca.training.springbatch.model;

public class Person {
	
	private Long personId;
    private String firstName;
    private String lastName;

    public Person() {
    }

    public Person(Long personId, String firstName, String lastName) {
    	this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	@Override
	public String toString() {
		return "personId: " + personId + ", firstName: " + firstName + ", lastName: " + lastName;
	}

}
