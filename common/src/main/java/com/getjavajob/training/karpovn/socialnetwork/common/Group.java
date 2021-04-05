package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mgroup")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("owner")
    @ManyToOne
    private Account owner;
    @JsonProperty("creationDate")
    private Date creationDate;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
