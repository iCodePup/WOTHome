package com.glg204.wothome.scene.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TriggerAndExpressionDTO.class, name = "and"),
        @JsonSubTypes.Type(value = TriggerOrExpressionDTO.class, name = "or"),
        @JsonSubTypes.Type(value = TriggerThingExpressionDTO.class, name = "thing"),
        @JsonSubTypes.Type(value = TriggerTimerExpressionDTO.class, name = "timer")
})
public abstract class TriggerExpressionDTO {
}

//Sample:
//{
//        "name": "foo",
//        "actionDTO": {
//        "thingId": 1,
//        "property": "OnOffProperty",
//        "value": "false"
//        },
//        "triggerExpressionDTO": {
//          "type": "and",
//          "firstExpression": {
//          "type": "thing",
//          "thingId": 123,
//          "property": "temperature",
//          "value": "30"
//        },
//        "secondExpression": {
//          "type": "or",
//          "firstExpression": {
//              "type": "thing",
//              "thingId": 456,
//              "property": "humidity",
//              "value": "50"
//          },
//          "secondExpression": {
//              "type": "thing",
//              "thingId": 789,
//              "property": "light",
//              "value": "bright"
//          }
//        }
//       }
//      }