package com.getjavajob.training.karpovn.socialnetwork.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message implements Serializable {

	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	private Integer id;
	private String recepient;
	private Date date;
	private String body;
	private String imageUrl;
	@OneToOne
	@JoinColumn(name = "account_id_from")
	private Account accountFrom;
	@OneToOne
	@JoinColumn(name = "account_id_to")
	private Account accountTo;
}
