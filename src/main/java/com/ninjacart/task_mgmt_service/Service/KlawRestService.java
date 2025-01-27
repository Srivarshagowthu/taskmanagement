package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.model.enums.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class KlawRestService {

    @Autowired
    private RestUrl restUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient(); // Java 21 HttpClient

    public Object convoxUploadData(Object object, Integer pitId) {
        HttpHeaders httpHeaders = new RestClientHelper().build();
        httpHeaders.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("access_token", CommonEnum.CONVOX_TOKEN_KEY);

        String url = restUrl.getConvoxEndCallUrl(pitId);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);

        log.info("Making rest call to convox for data upload. pit {} with url {} and object {}", pitId, url, object);

        Object convoxResponse = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriComponentsBuilder.toUriString()))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .header("access_token", CommonEnum.CONVOX_TOKEN_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(object.toString())) // assuming object can be converted to JSON string
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // If the response is successful, parse the response body here
                convoxResponse = response.body(); // You can parse this based on your actual response type
            } else {
                log.error("Error from Convox API: Status code {}", response.statusCode());
                // You can throw a custom exception or handle the error as required
            }

        } catch (IOException | InterruptedException e) {
            log.error("Exception occurred while calling convox upload data API: ", e);
        }

        log.info("Convox Response for pit {} is {}", pitId, convoxResponse);
        return convoxResponse;
    }
}
