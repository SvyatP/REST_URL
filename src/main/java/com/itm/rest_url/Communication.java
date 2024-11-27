package com.itm.rest_url;

import com.itm.rest_url.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class Communication {
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

//    @Value("${api.url}")
//    private String URL;
    private final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.httpHeaders = headers;
        this.httpHeaders.set("Cookie",
                String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    private ResponseEntity<String> sendRequest(HttpMethod method, Object body) {
        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange("http://94.198.50.185:7081/api/users", method, entity, String.class);
    }

    public String getAnswer() {
        try {
            return addUser().getBody() + updateUser().getBody() + deleteUser().getBody();
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    private ResponseEntity<String> addUser() {
        User user = new User(3L, "James", "Brown", (byte) 5);
        return sendRequest(HttpMethod.POST, user);
    }

    private ResponseEntity<String> updateUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 5);
        return sendRequest(HttpMethod.PUT, user);
    }

    private ResponseEntity<String> deleteUser() {
        String deleteUrl = UriComponentsBuilder.fromHttpUrl(URL)
                .pathSegment("{id}")
                .buildAndExpand(3L)
                .toUriString();
        return restTemplate.exchange(deleteUrl, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), String.class);
    }

}
