package com.getjavajob.training.karpovn.socialnetwork.common;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message implements Serializable {

	@Id
	private Integer id;
	private String to;
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
