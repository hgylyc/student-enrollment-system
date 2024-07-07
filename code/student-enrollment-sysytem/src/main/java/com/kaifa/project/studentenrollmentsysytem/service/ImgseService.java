package com.kaifa.project.studentenrollmentsysytem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImgseService {

    private static final String UPLOAD_URL = "https://imgse.com/api/upload";

    public String uploadFile(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());

        HttpEntity<?> entity = new HttpEntity<>(builder.build(), headers);

        ResponseEntity<String> response = restTemplate.exchange(UPLOAD_URL, HttpMethod.POST, entity, String.class);

        // Assuming the response contains a JSON object with the URL
        // Parse the JSON response to extract the URL
        String responseBody = response.getBody();
        // Example: extract the URL from the response (this depends on the actual response format)
        // You may need to adjust this part according to the actual response format
        String imageUrl = extractImageUrlFromResponse(responseBody);
        return imageUrl;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return factory;
    }

    private String extractImageUrlFromResponse(String response) {
        // Implement the logic to extract the image URL from the response
        // For example, if the response is a JSON object, you can use a JSON parser
        // Here is an example using a simple regex pattern (you may need to adjust it)
        // Assuming the URL is in a field named "url"
        Pattern pattern = Pattern.compile("\"url\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Failed to extract image URL from response");
        }
    }
}
