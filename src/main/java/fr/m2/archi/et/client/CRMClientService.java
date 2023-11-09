package fr.m2.archi.et.client;

import java.util.List;

import fr.m2.archi.et.model.thrift.InternalLeadDto;

public interface CRMClientService {
    List<InternalLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state);
}
