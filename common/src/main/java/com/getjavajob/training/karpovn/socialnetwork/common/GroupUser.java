package com.getjavajob.training.karpovn.socialnetwork.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groupusers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;

	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private List<Group> groups;

	@OneToOne
	@JoinColumn(name = "user_id")
	private Account account;
}
