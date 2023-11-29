package fr.m2.archi.et.service.controller;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.m2.archi.et.client.CRMThriftClient;
import fr.m2.archi.et.model.thrift.InternalLeadDto;

@RestController
@RequestMapping("/api")
public class InternalCRMController {
	@Autowired
    private CRMThriftClient crmClient;

    @GetMapping("/getLeads")
    public List<InternalLeadDto> getLeads(
            @RequestParam double lowAnnualRevenue,
            @RequestParam double highAnnualRevenue,
            @RequestParam String state) throws TException {
        return crmClient.findLeads(lowAnnualRevenue, highAnnualRevenue, state);
    }
    
    @PostMapping("/getLeads")
    public List<InternalLeadDto> getLeads(
    		@RequestBody JsonRequestForLeads request) throws TException {
    	return crmClient.findLeads(request.getLowAnnualRevenue(), request.getHighAnnualRevenue(), request.getState());
    }
}
