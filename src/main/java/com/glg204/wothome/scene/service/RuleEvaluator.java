package com.glg204.wothome.scene.service;

import com.glg204.wothome.scene.dao.RuleDAO;
import com.glg204.wothome.scene.domain.Action;
import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.scene.domain.TriggerExpression;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RuleEvaluator {

    private final Logger logger = LoggerFactory.getLogger(RuleEvaluator.class);

    @Autowired
    RuleDAO ruleDAO;

    private final RestTemplate restTemplate;

    public RuleEvaluator() {
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    @Scheduled(fixedRate = 10000) // 10 seconds
    public void scheduleRuleEvaluation() {
        List<Rule> ruleList = ruleDAO.getRules();
        logger.info("Evaluation de {} règles", ruleList.size());

        ruleList.forEach(rule -> {
            if (evaluateTriggerExpression(rule.getTriggerExpression())) {
                // If trigger is valid, execute the action
                executeAction(rule.getAction());
            }
        });
    }

    private boolean evaluateTriggerExpression(TriggerExpression expression) {
        return expression.process();
    }

    private void executeAction(Action action) {

        if (action != null && action.getThing() != null && action.getProperty() != null) {
            String url = action.getThing().getUrl() + "/properties/" + action.getProperty();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //EXEMPLE : {TemperatureProperty: 11}
            String requestBody = "{" + action.getProperty() + ": " + action.getValue() + "}";
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("L'action sur l'objet connecté a été executée");
            } else {
                logger.info("Echec lors de l'execution de l'action sur l'objet connecté");
            }
        } else {
            logger.info("Echec lors de l'execution de l'action sur l'objet connecté");
        }
    }
}








