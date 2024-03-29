package fr.m2.archi.et.model.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import fr.m2.archi.et.data.reader.CsvReader;
import fr.m2.archi.et.model.thrift.InternalCRMService;
import fr.m2.archi.et.model.thrift.InternalLeadDto;
import fr.m2.archi.et.model.thrift.ModelITO;

@Service
public class InternalCRMServiceImpl implements InternalCRMService.Iface {
	
	private List<InternalLeadDto> leadData;
	private static InternalCRMServiceImpl internalCRMServiceImpl;

    private InternalCRMServiceImpl() {
        leadData = CsvReader.readCSVFile("data.csv");
    }
    
    public static InternalCRMServiceImpl getInstance() {
    	if(internalCRMServiceImpl == null) {
    		internalCRMServiceImpl = new InternalCRMServiceImpl();
    	}
    	return internalCRMServiceImpl;
    }

	@Override
	public List<InternalLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state)
			throws TException {
		List<InternalLeadDto> leads = new ArrayList<>();

	    for (InternalLeadDto lead : leadData) {
	        ModelITO info = lead.getInformations();
	        double annualRevenue = info.getAnnualRevenue();
	        String leadState = info.getState();

	        if (annualRevenue >= lowAnnualRevenue && annualRevenue <= highAnnualRevenue && leadState.equals(state)) {
	            leads.add(lead);
	        }
	    }

	    return leads;
	}

	@Override
	public List<InternalLeadDto> findLeadsByDate(String startDate, String endDate) throws TException {
		List<InternalLeadDto> leads = new ArrayList<InternalLeadDto>();

		String newStartDate = startDate.substring(0, startDate.indexOf('T'));
		String newEndDate = endDate.substring(0, endDate.indexOf('T'));

		for(InternalLeadDto lead : leadData) {
			ModelITO info = lead.getInformations();
			String[] date = info.getCreationDate().split("-");
			String[] startDateSplit = newStartDate.split("-");
			String[] endDateSplit = newEndDate.split("-");

			int[] intDate = new int[date.length]; int[] intStartDate = new int[startDateSplit.length]; int[] intEndDate = new int[endDateSplit.length];

			//1 = Année, 2 = Mois, 3 = Jours
			for (int i = 0; i < date.length; i++) {
			    intDate[i] = Integer.parseInt(date[i]);
			    intStartDate[i] = Integer.parseInt(startDateSplit[i]);
			    intEndDate[i] = Integer.parseInt(endDateSplit[i]);
			}
			
	        LocalDate startDatef = LocalDate.of(intStartDate[0], intStartDate[1], intStartDate[2]);
	        LocalDate endDatef = LocalDate.of(intEndDate[0], intEndDate[1], intEndDate[2]);

	        // Dates to check
	        LocalDate userDate = LocalDate.of(intDate[0], intDate[1], intDate[2]);

	        if(!userDate.isBefore(startDatef) && !userDate.isAfter(endDatef)) {
	        	leads.add(lead);
	        }
		}
		
		return leads;
	}

	@Override
	public void deleteLead(String phoneNumber) throws TException {
		for(int i = 0; i < leadData.size(); i++) {
			if(leadData.get(i).getInformations().getPhone().equals(phoneNumber)) {
				leadData.remove(i);
				break;
			}
		}
        try {
            CsvReader.processCsvFile("data.csv", phoneNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void addLead(InternalLeadDto lead) throws TException {
		leadData.add(lead);
		String[] leadToAddToCsv =
				{
						"\"" + lead.informations.getName() + "\"",
				  		"" + lead.informations.getAnnualRevenue(),
				  		lead.informations.getPhone(),
						lead.informations.getStreet(),
						lead.informations.getPostalCode(),
						lead.informations.getCity(),
						lead.informations.getCountry(),
						lead.informations.getCreationDate(),
						lead.informations.getCompany(),
						lead.informations.getState()
				};

		String leadToAddToCsvToString = String.join(";", leadToAddToCsv);

		File csvOutputFile = new File("data.csv");
		try (PrintWriter pw = new PrintWriter(new FileWriter(csvOutputFile, true))) {
		    pw.println(leadToAddToCsvToString);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	@Override
	public List<InternalLeadDto> getUsers() throws TException {
		return leadData;
	}

}
