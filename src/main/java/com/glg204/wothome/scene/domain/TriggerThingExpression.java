package com.glg204.wothome.scene.domain;

import com.glg204.wothome.webofthings.domain.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TriggerThingExpression extends TriggerExpression {

    private Thing thing;

    private String property;

    private String value;


    public TriggerThingExpression() {
    }

    public TriggerThingExpression(Thing thing, String property, String value) {
        this.thing = thing;
        this.property = property;
        this.value = value;
    }

    @Override
    public boolean process() {
        RestTemplate restTemplate = new RestTemplate();
        String url = thing.getUrl() + "?property=" + property;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                return responseBody != null && responseBody.equals(value);
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
}
