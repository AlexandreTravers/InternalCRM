package fr.m2.archi.et.data.reader;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.m2.archi.et.model.thrift.InternalLeadDto;
import fr.m2.archi.et.model.thrift.ModelITO;

public class CsvReader {
	public static List<InternalLeadDto> readCSVFile(String filePath) {
        List<InternalLeadDto> instances = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.nextLine(); // Ignorer la première ligne (titres)

            while (scanner.hasNextLine()) {
            	
                //InternalLeadDto instance = new InternalLeadDto(firstName, lastName, annualRevenue, phone, street, postalCode, country, creationDate, company, state);
                InternalLeadDto instance = new InternalLeadDto();
            	ModelITO model = new ModelITO();
            	
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                
                if(parts.length >= 10) {

	                String firstName = parts[0].trim(); model.setFirstName(firstName);
	                String lastName = parts[1].trim(); model.setLastName(lastName);
	                double annualRevenue = Double.parseDouble(parts[2].trim()); model.setAnnualRevenue(annualRevenue);
	                String phone = parts[3].trim(); model.setPhone(phone);
	                String street = parts[4].trim(); model.setStreet(street);
	                String postalCode = parts[5].trim(); model.setPostalCode(postalCode);
	                String city = parts[6].trim(); model.setCity(city);
	                String country = parts[7].trim(); model.setCountry(country);
	                String creationDate = parts[8].trim(); model.setCreationDate(creationDate);
	                String company = parts[9].trim(); model.setCompany(company);
	                String state = parts[10].trim(); model.setState(state);
	
	                instance.setInformations(model);
	                instances.add(instance);
	                
                } else {
                	System.out.println("Une ligne a mal été initialisé : " + line);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return instances;
    }
}
