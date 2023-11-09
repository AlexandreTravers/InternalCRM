package fr.m2.archi.et.service.controller;

public class JsonRequestForLeads {
	private double lowAnnualRevenue;
	private double highAnnualRevenue;
	private String state;
	
	public double getLowAnnualRevenue() {
		return this.lowAnnualRevenue;
	}
	public double getHighAnnualRevenue() {
		return this.highAnnualRevenue;
	}
	public String getState() {
		return this.state;
	}
}
