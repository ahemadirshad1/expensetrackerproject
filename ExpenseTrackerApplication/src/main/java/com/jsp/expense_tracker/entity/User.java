package com.jsp.expense_tracker.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String fullName;
	private String userName;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private long mobile;
	private String password;
	
	@OneToMany(mappedBy = "userInfo", fetch = FetchType.EAGER)
	private List<Expenses> expenses;

}
