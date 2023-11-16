package fr.m2.archi.et.model.salesforce;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesforceUserService {
	private static final String KEY = "3MVG9_kZcLde7U5o7jrBfZ7.55H_Wd9mdQnTiQb4y_VMnFKiZWY7OlquULuXrnnqy2Gu9tB6D_i8lUEZt_fuX";
	private static final String SECRET_KEY = "3D56D61A346F2AD448C2CBEE7ECB256FA4473B2EE3A2DD5DEDE7782E56BB66B3";
	private static final String DOMAIN_NAME = "https://universitangers-dev-ed.develop.my.salesforce.com";
	private static final String SECURITY_TOKEN = "Yy5WQPOvB1NnxH0Q4QLTV1FZQ";
	private static final String LOGIN_URL = "https://login.salesforce.com/services/oauth2/token";
	private static final String USERNAME = "alexandretvs@univ-angers.fr";
	private static final String PASSWORD = "*LePlusGrandMDPJamaisVueAvant2023*";
	
	private static SalesforceUserService instance;
	
	private SalesforceUserService() {}
	
	public static SalesforceUserService getInstance() {
		if(instance == null) {
			instance = new SalesforceUserService();
		}
		return instance;
	}
	
	private String getAccessToken() {
		String requestBody = "grant_type=password"
				+ "&client_id=" + KEY
	            + "&client_secret=" + SECRET_KEY
	            + "&username=" + USERNAME
	            + "&password=" + PASSWORD + SECURITY_TOKEN;
		
		HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        	    .uri(URI.create(LOGIN_URL))
        	    .header("Content-Type", "application/x-www-form-urlencoded")
        	    .POST(BodyPublishers.ofString(requestBody))
        	    .build();

        HttpResponse<String> response;
        String accessToken = "";
        
        try {
        	response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Réponse de l'authentification : " + response.body());
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            accessToken = jsonNode.get("access_token").asText();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return accessToken;
	}
	
	private JsonNode getAllUsersInformationsInJSON() {
		String accessToken = this.getAccessToken();
        String apiUrl = DOMAIN_NAME + "/services/data/v59.0/query/?q=SELECT+FirstName,LastName,annualRevenue__c,Phone,Address,PostalCode,Country,CreatedDate,CompanyName+FROM+User";
		
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        JsonNode jsonObject = null;
		try {
			HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();
            
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Réponse de la requête : " + response.body());
            
            ObjectMapper objectMapper = new ObjectMapper();

            jsonObject = objectMapper.readValue(response.body(), JsonNode.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public List<SalesforceLeadDto> getUsersInformations() {
		JsonNode jsonObject = this.getAllUsersInformationsInJSON();
		List<SalesforceLeadDto> listInformations = new ArrayList<SalesforceLeadDto>();

		
		if (jsonObject.has("records") && jsonObject.get("records").isArray()) {
		    JsonNode records = jsonObject.get("records");
            for (JsonNode record : records) {
                String firstName = record.get("FirstName").asText();
                String lastName = record.get("LastName").asText();
                double annualRevenue = record.get("annualRevenue__c").asDouble();
                String phone = record.get("Phone").asText();

                JsonNode address = record.get("Address");
                String street = address.get("street").asText();
                String postalCode = address.get("postalCode").asText();
                String city = address.get("city").asText();
                String country = address.get("country").asText();
                String state = address.get("state").asText();

                String creationDate = record.get("CreatedDate").asText();
                String company = record.get("CompanyName").asText();
                listInformations.add(new SalesforceLeadDto(new SalesforceUserModel(firstName, lastName, annualRevenue, phone, street, postalCode, city, country, creationDate, company, state)));
            }
        }
		
		return listInformations;
	}
}
