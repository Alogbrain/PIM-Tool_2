package vn.elca.training.springbatch.util;

import java.util.Arrays;
import java.util.List;

import vn.elca.training.springbatch.model.Person;

public class CSVUtils {

    public static final String CSV_SEPARATOR = ",";
    public static final int NUM_PERSON_COLUMNS = 3;
    
    private CSVUtils() {};
    
    public static Person fromPersonString(String line) {
    	if (line == null) {
    		return null;
    	}
		String[] personData = line.split(CSVUtils.CSV_SEPARATOR);
		if (personData.length != NUM_PERSON_COLUMNS) {
			return null;
		}
		return new Person(Long.valueOf(personData[0]), personData[1], personData[2]);
    }

    public static String toPersonString(Person person) {
        List<String> values = Arrays.asList(person.getPersonId().toString(), person.getFirstName(), person.getLastName());
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
        	if (first)
				first = false;
			else
				sb.append(CSV_SEPARATOR);
            sb.append(value);
        }
        sb.append("\n");
        return sb.toString();
    }

}
