package com.mindlease.fa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "FIRST_NAME", nullable = false)
	@NotEmpty
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	@NotEmpty
	private String lastName;

	@Column(name = "EMAIL", nullable = false, unique = true)
	@NotNull
	@NotEmpty
	@Email(message = "{errors.invalid_email}")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "LANGUAGE")
	private String language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Company company;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<Role> roles;

	@Transient
	private Role role;

	@Transient
	private String mode;

	@Transient
	private String newPassword;

	@Transient
	private String confirmPassword;

}
