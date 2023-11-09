package fr.m2.archi.et;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest.BodyPublishers;

public class TestSalesforceAPI {
	private static final String KEY = "3MVG9_kZcLde7U5o7jrBfZ7.55H_Wd9mdQnTiQb4y_VMnFKiZWY7OlquULuXrnnqy2Gu9tB6D_i8lUEZt_fuX";
	private static final String SECRET_KEY = "3D56D61A346F2AD448C2CBEE7ECB256FA4473B2EE3A2DD5DEDE7782E56BB66B3";
	private static final String DOMAIN_NAME = "https://universitangers-dev-ed.develop.my.salesforce.com";
	private static final String SECURITY_TOKEN = "Yy5WQPOvB1NnxH0Q4QLTV1FZQ";
	
	
	public static void main(String[] args) {
        String username = "alexandretvs@univ-angers.fr";
        String password = "*LePlusGrandMDPJamaisVueAvant2023*";
        String loginUrl = "https://login.salesforce.com/services/oauth2/token";
        //String apiUrl = DOMAIN_NAME + "/services/data/";
        String apiUrl = DOMAIN_NAME + "/services/data/v59.0/query/?q=SELECT+FirstName,LastName,annualRevenue__c,Phone,Address,PostalCode,Country,CreatedDate,CompanyName+FROM+User";
        //String apiUrl = DOMAIN_NAME + "/services/data/v59.0/query/?q=SELECT+*+FROM+User";
        
        String requestBody = "grant_type=password"
                + "&client_id=" + KEY
                + "&client_secret=" + SECRET_KEY
                + "&username=" + username
                + "&password=" + password + SECURITY_TOKEN;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        	    .uri(URI.create(loginUrl))
        	    .header("Content-Type", "application/x-www-form-urlencoded")
        	    .POST(BodyPublishers.ofString(requestBody))
        	    .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Réponse de l'authentification : " + response.body());
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            String accessToken = jsonNode.get("access_token").asText();
            System.out.println("Access Token : " + accessToken);
            
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();
            
            response = client.send(request2, HttpResponse.BodyHandlers.ofString());
            System.out.println("Réponse de la requête : " + response.body());
            
            ObjectMapper objectMapper2 = new ObjectMapper();

            Object jsonObject = objectMapper.readValue(response.body(), Object.class);

            String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            
            System.out.println("JSON structuré : ");
            System.out.println(formattedJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
