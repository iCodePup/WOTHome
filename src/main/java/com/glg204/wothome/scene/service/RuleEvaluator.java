package com.glg204.wothome.scene.service;

import com.glg204.wothome.config.TokenProvider;
import com.glg204.wothome.scene.dao.RuleDAO;
import com.glg204.wothome.scene.domain.Action;
import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.scene.domain.TriggerExpression;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RuleEvaluator {

    private Logger logger = LoggerFactory.getLogger(RuleEvaluator.class);

    @Autowired
    RuleDAO ruleDAO;

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
        RestTemplate restTemplate = new RestTemplate();
        String url = action.getThing().getUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"property\": \"" + action.getProperty() + "\", \"value\": \"" + action.getValue() + "\"}";
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("L'action sur l'objet connecté a été executée");
        } else {
            logger.info("Echec lors de l'execution de l'action sur l'objet connecté");
        }
    }
}








