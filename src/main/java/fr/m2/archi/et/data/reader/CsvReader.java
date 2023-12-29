package fr.m2.archi.et.data.reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
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
            scanner.nextLine();

            while (scanner.hasNextLine()) {
            	
                InternalLeadDto instance = new InternalLeadDto();
            	ModelITO model = new ModelITO();
            	
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                
                if(parts.length >= 9) {

	                String name = parts[0].trim().replace("\"", ""); model.setName(name);
	                double annualRevenue = Double.parseDouble(parts[1].trim()); model.setAnnualRevenue(annualRevenue);
	                String phone = parts[2].trim(); model.setPhone(phone);
	                String street = parts[3].trim(); model.setStreet(street);
	                String postalCode = parts[4].trim(); model.setPostalCode(postalCode);
	                String city = parts[5].trim(); model.setCity(city);
	                String country = parts[6].trim(); model.setCountry(country);
	                String creationDate = parts[7].trim(); model.setCreationDate(creationDate);
	                String company = parts[8].trim(); model.setCompany(company);
	                String state = parts[9].trim(); model.setState(state);
	
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
	
	
	//Les trois prochaines méthodes : réservé à la supressions d'informations.
	public static void processCsvFile(String filePath, String informationToFilter) throws IOException {
        List<String> lines = readAllLines(filePath);
        List<String> filteredLines = new ArrayList<>();
        for (String line : lines) {
            if (!line.contains(informationToFilter)) {
                filteredLines.add(line);
            }
        }

        writeLinesToFile(filePath, filteredLines);
    }

    private static List<String> readAllLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static void writeLinesToFile(String filePath, List<String> lines) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                pw.println(line);
            }
        }
    }
}
