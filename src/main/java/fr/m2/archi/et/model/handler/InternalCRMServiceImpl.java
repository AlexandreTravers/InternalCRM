package fr.m2.archi.et.model.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import fr.m2.archi.et.data.reader.CsvReader;
import fr.m2.archi.et.model.thrift.InternalCRMService;
import fr.m2.archi.et.model.thrift.InternalLeadDto;
import fr.m2.archi.et.model.thrift.ModelITO;

@Service
public class InternalCRMServiceImpl implements InternalCRMService.Iface {
	
	//EXEMPLE FACTICE LE TEMPS DE
	private List<InternalLeadDto> leadData;

    public InternalCRMServiceImpl() {
        leadData = CsvReader.readCSVFile("data.csv");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLead(InternalLeadDto lead) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLead(InternalLeadDto leadDto) throws TException {
		// TODO Auto-generated method stub
		
	}

}
