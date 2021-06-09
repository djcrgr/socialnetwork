package com.getjavajob.training.karpovn.socialnetwork.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@Entity
@Table(name = "phones")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Phone implements Serializable {

    @JsonProperty("number")
    @Column(name = "phoneNum")
    private String number;
    @JsonProperty("type")
    @Column(name = "phoneType")
    private String type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    @XmlTransient
    private Account account;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   /* @JsonCreator
    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    @JsonCreator
    public Phone(String number, String type, Account account) {
        this.number = number;
        this.type = type;
        this.account = account;
    }*/
}
