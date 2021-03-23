package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Phone {

    @JsonProperty("number")
    private String number;
    @JsonProperty("type")
    private String type;

    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

}
