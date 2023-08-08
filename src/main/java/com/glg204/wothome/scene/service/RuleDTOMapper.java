package com.glg204.wothome.scene.service;

import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.scene.dto.*;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.domain.Thing;
import com.glg204.wothome.webofthings.dto.ThingDTO;
import com.glg204.wothome.webofthings.service.ThingDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RuleDTOMapper {

    @Autowired
    ThingDAO thingDAO;

    public Rule fromDTO(RuleDTO ruleDTO, User currentUser) {
        Rule rule = new Rule(ruleDTO.getName(), currentUser);
        Optional<Thing> optionalActionThing = thingDAO.getById(ruleDTO.getActionDTO().getThingId());
        optionalActionThing.ifPresent(thing -> {
            rule.setAction(new Action(thing,
                    ruleDTO.getActionDTO().getProperty(),
                    ruleDTO.getActionDTO().getValue()));
            rule.setTriggerExpression(mapDtoToDomain(ruleDTO.getTriggerExpressionDTO()));
        });
        return rule;
    }

    private TriggerExpression mapDtoToDomain(TriggerExpressionDTO dto) {
        if (dto instanceof TriggerAndExpressionDTO) {
            TriggerAndExpressionDTO andDto = (TriggerAndExpressionDTO) dto;
            return new TriggerAndExpression(
                    mapDtoToDomain(andDto.getFirstExpression()),
                    mapDtoToDomain(andDto.getSecondExpression())
            );
        } else if (dto instanceof TriggerOrExpressionDTO) {
            TriggerOrExpressionDTO orDto = (TriggerOrExpressionDTO) dto;
            return new TriggerOrExpression(
                    mapDtoToDomain(orDto.getFirstExpression()),
                    mapDtoToDomain(orDto.getSecondExpression())
            );
        } else if (dto instanceof TriggerThingExpressionDTO) {
            TriggerThingExpressionDTO thingDto = (TriggerThingExpressionDTO) dto;
            Optional<Thing> optionalThing = thingDAO.getById(thingDto.getThingId());
            return optionalThing.map(thing -> new TriggerThingExpression(
                    thing,
                    thingDto.getProperty(),
                    thingDto.getValue()
            )).orElse(null);
        } else if (dto instanceof TriggerTimerExpressionDTO) {
            TriggerTimerExpressionDTO timerDto = (TriggerTimerExpressionDTO) dto;
            return new TriggerTimerExpression(timerDto.getRuntime());
        } else {
            throw new RuntimeException("Unsupported DTO type: " + dto.getClass().getName());
        }
    }
}
