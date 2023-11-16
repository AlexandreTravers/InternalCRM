package fr.m2.archi.et.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.m2.archi.et.client.CRMClientService;
import fr.m2.archi.et.model.thrift.InternalLeadDto;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;




@RestController
public class CoordinatesController {
	@Autowired
    private CRMClientService crmClient;

    @GetMapping("/searchForCoordinates")
    public List<InternalLeadDto> getCoordinates(
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String postal,
            @RequestParam String state,
    		@RequestParam String country) {
        //return crmClient.findCoordinates(address, city, postal, state, country);
    	return null;
    }
    
    public void findCoordinates(String address, String city, String postal, String state, String country) 
    {
        Object jsonObject = null;

        try {
            // Construire l'URL de requête
            String query = String.format(
                    "https://nominatim.openstreetmap.org/search?format=json&street=%s&city=%s&postalcode=%s&state=%s&country=%s&addressdetails=1",
                    URLEncoder.encode(address, "UTF-8"),
                    URLEncoder.encode(city, "UTF-8"),
                    URLEncoder.encode(postal, "UTF-8"),
                    URLEncoder.encode(state, "UTF-8"),
                    URLEncoder.encode(country, "UTF-8"));

            
            HttpRequest HttpQuery = HttpRequest.newBuilder()
            .uri(URI.create(query))
            .GET()
            .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(HttpQuery, HttpResponse.BodyHandlers.ofString());            String line;
            


            ObjectMapper objectMapper = new ObjectMapper();

            jsonObject = objectMapper.readValue(response.body(), Object.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}


