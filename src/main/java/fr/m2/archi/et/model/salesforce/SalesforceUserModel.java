package fr.m2.archi.et.model.salesforce;

public class SalesforceUserModel {
	private String firstName;
	private String lastName;
	private double annualRevenue;
	private String phone;
	private String street;
	private String postalCode;
	private String country;
	private String creationDate;
	private String company;
	private String state;
	
	public SalesforceUserModel(String firstName, String lastName, double annualRevenue, String phone, String street, String postalCode, String country, String creationDate, String company, String state) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.annualRevenue = annualRevenue;
		this.phone = phone;
		this.street = street;
		this.postalCode = postalCode;
		this.country = country;
		this.creationDate = creationDate;
		this.company = company;
		this.state = state;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public double getAnnualRevenue() {
		return annualRevenue;
	}

	public String getPhone() {
		return phone;
	}

	public String getStreet() {
		return street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCountry() {
		return country;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getCompany() {
		return company;
	}

	public String getState() {
		return state;
	}
	
}
