package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Data
@Component
public class Group {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("owner")
    private Account owner;
    @JsonProperty("creationDate")
    private Date creationDate;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group() {

    }
}
