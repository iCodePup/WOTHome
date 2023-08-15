package com.glg204.wothome.scene.service;

import com.glg204.wothome.scene.domain.*;
import com.glg204.wothome.scene.dto.*;
import com.glg204.wothome.user.domain.User;
import com.glg204.wothome.webofthings.dao.ThingDAO;
import com.glg204.wothome.webofthings.domain.Thing;
import com.glg204.wothome.webofthings.service.ThingDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RuleDTOMapper {

    @Autowired
    ThingDAO thingDAO;

    @Autowired
    ThingDTOMapper thingDTOMapper;

    public Rule fromDTO(RuleDTO ruleDTO, User currentUser) {
        Rule rule = new Rule(ruleDTO.getName(), currentUser);
        Optional<Thing> optionalActionThing = Optional.empty();
        if (ruleDTO.getActionDTO().getThingDTO() != null) {
            optionalActionThing = thingDAO.getById(ruleDTO.getActionDTO().getThingDTO().getId());
        }
        optionalActionThing.ifPresent(thing -> {
            rule.setAction(new Action(thing,
                    ruleDTO.getActionDTO().getProperty(),
                    ruleDTO.getActionDTO().getValue()));
            rule.setTriggerExpression(mapDtoToDomain(ruleDTO.getTriggerExpressionDTO()));
        });
        return rule;
    }

    public RuleDTO toDTO(Rule rule) {
        RuleDTO ruleDTO = new RuleDTO();
        ruleDTO.setId(rule.getId());
        ruleDTO.setName(rule.getName());
        Action action = rule.getAction();
        if (action != null) {
            ActionDTO actionDTO = new ActionDTO();

            actionDTO.setThingDTO(thingDTOMapper.toDTO(action.getThing()));
            actionDTO.setProperty(action.getProperty());
            actionDTO.setValue(action.getValue());
            ruleDTO.setActionDTO(actionDTO);
        }

        // Map the TriggerExpression
        TriggerExpression triggerExpression = rule.getTriggerExpression();
        if (triggerExpression != null) {
            TriggerExpressionDTO triggerExpressionDTO = mapDomainToDto(triggerExpression);
            ruleDTO.setTriggerExpressionDTO(triggerExpressionDTO);
        }
        return ruleDTO;
    }

    private TriggerExpressionDTO mapDomainToDto(TriggerExpression domain) {
        if (domain instanceof TriggerAndExpression andExpression) {
            return new TriggerAndExpressionDTO(
                    mapDomainToDto(andExpression.getFirstExpression()),
                    mapDomainToDto(andExpression.getSecondExpression())
            );
        } else if (domain instanceof TriggerOrExpression orExpression) {
            return new TriggerOrExpressionDTO(
                    mapDomainToDto(orExpression.getFirstExpression()),
                    mapDomainToDto(orExpression.getSecondExpression())
            );
        } else if (domain instanceof TriggerThingExpression thingExpression) {
            return new TriggerThingExpressionDTO(
                    thingDTOMapper.toDTO(thingExpression.getThing()),
                    thingExpression.getProperty(),
                    thingExpression.getValue()
            );
        } else if (domain instanceof TriggerTimerExpression timerExpression) {
            return new TriggerTimerExpressionDTO(timerExpression.getRuntime());
        } else {
            throw new RuntimeException("Unsupported domain type: " + domain.getClass().getName());
        }
    }

    private TriggerExpression mapDtoToDomain(TriggerExpressionDTO dto) {
        if (dto instanceof TriggerAndExpressionDTO andDto) {
            return new TriggerAndExpression(
                    mapDtoToDomain(andDto.getFirstExpression()),
                    mapDtoToDomain(andDto.getSecondExpression())
            );
        } else if (dto instanceof TriggerOrExpressionDTO orDto) {
            return new TriggerOrExpression(
                    mapDtoToDomain(orDto.getFirstExpression()),
                    mapDtoToDomain(orDto.getSecondExpression())
            );
        } else if (dto instanceof TriggerThingExpressionDTO thingDto) {
            Optional<Thing> optionalThing = Optional.empty();
            if (thingDto.getThingDTO() != null) {
                optionalThing = thingDAO.getById(thingDto.getThingDTO().getId());
            }
            return optionalThing.map(thing -> new TriggerThingExpression(
                    thing,
                    thingDto.getProperty(),
                    thingDto.getValue()
            )).orElse(null);
        } else if (dto instanceof TriggerTimerExpressionDTO timerDto) {
            return new TriggerTimerExpression(timerDto.getRuntime());
        } else {
            throw new RuntimeException("Unsupported DTO type: " + dto.getClass().getName());
        }
    }
}
