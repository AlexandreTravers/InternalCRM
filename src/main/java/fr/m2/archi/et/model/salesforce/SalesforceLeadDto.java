package fr.m2.archi.et.model.salesforce;

public class SalesforceLeadDto {
	SalesforceUserModel informations;
	
	public SalesforceLeadDto(SalesforceUserModel informations) {
		this.informations = informations;
	}
	
	public SalesforceUserModel getInformations() {
		return this.informations;
	}
}
