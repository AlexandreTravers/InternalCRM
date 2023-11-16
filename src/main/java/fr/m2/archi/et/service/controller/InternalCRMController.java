package fr.m2.archi.et.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.m2.archi.et.client.CRMThriftClient;
import fr.m2.archi.et.model.thrift.InternalLeadDto;

@RestController
public class InternalCRMController {
	@Autowired
    private CRMThriftClient crmClient;

    @GetMapping("/internalLeadsWithGet")
    public List<InternalLeadDto> getLeads(
            @RequestParam double lowAnnualRevenue,
            @RequestParam double highAnnualRevenue,
            @RequestParam String state) {
        return crmClient.findLeads(lowAnnualRevenue, highAnnualRevenue, state);
    }
    
    @PostMapping("/internalLeads")
    public List<InternalLeadDto> getLeads(
    		@RequestBody JsonRequestForLeads request) {
    	return crmClient.findLeads(request.getLowAnnualRevenue(), request.getHighAnnualRevenue(), request.getState());
    }
}
