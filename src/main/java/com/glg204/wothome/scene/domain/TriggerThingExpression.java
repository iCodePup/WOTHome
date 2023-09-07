package com.glg204.wothome.scene.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TriggerThingExpression extends TriggerExpression {

    private Thing thing;

    private String property;

    private String value;

    private RestTemplate restTemplate;

    public TriggerThingExpression() {
        this.restTemplate = new RestTemplate();
    }

    public TriggerThingExpression(Thing thing, String property, String value) {
        this.thing = thing;
        this.property = property;
        this.value = value;
    }

    @Override
    public boolean process() {
        String url = thing.getUrl() + "/properties/" + property;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String responseValue = jsonNode.get(this.property).asText();
                return responseValue != null && responseValue.equalsIgnoreCase(value);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
