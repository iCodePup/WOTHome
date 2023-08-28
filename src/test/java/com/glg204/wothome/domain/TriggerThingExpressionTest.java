package com.glg204.wothome.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.webofthings.domain.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TriggerThingExpressionTest {

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess_SuccessfulResponse() throws Exception {
        Thing thing = new Thing();
        thing.setUrl("http://webofthings.com");
        String property = "temperature";
        String value = "25";

        String responseBody = "{\"temperature\":\"25\"}";
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);

        TriggerThingExpression triggerExpression = new TriggerThingExpression(thing, property, value);
        triggerExpression.setRestTemplate(restTemplate);

        assertTrue(triggerExpression.process());
    }

    @Test
    public void testProcess_FailedResponse() throws Exception {
        Thing thing = new Thing();
        thing.setUrl("http://webofthings.com");
        String property = "temperature";
        String value = "25";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);

        TriggerThingExpression triggerExpression = new TriggerThingExpression(thing, property, value);
        triggerExpression.setRestTemplate(restTemplate);

        assertFalse(triggerExpression.process());
    }

    @Test
    public void testProcess_Exception() throws Exception {
        Thing thing = new Thing();
        thing.setUrl("http://webofthings.com");
        String property = "temperature";
        String value = "25";

        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(new RuntimeException());

        TriggerThingExpression triggerExpression = new TriggerThingExpression(thing, property, value);
        triggerExpression.setRestTemplate(restTemplate);

        assertFalse(triggerExpression.process());
    }
}
