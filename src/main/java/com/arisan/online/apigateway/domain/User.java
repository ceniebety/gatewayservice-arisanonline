package com.arisan.online.apigateway.domain;


import java.util.Collection;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
public class User {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	
	@JsonProperty("nama")
	@Column(name = "nama", nullable = false, length=150)
	private String nama;
	
	@JsonProperty("jenisKelamin")
	@Column(name = "jenis_kelamin", nullable = false, length=15)
	private String jenisKelamin;
	
	@JsonProperty("email")
	@Column(name = "email", nullable = false, length=60, unique = true)
	private String email;
	
	@JsonProperty("tglLahir")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tglLahir;
	
	@JsonProperty("noHp")
	@Column(name = "no_hp", nullable = false)
	private String noHp;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@JsonProperty("username")
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@JsonProperty("password")
	@Column(name = "password", nullable = false)
	private String password;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getTglLahir() {
		return tglLahir;
	}

	public void setTglLahir(Date tglLahir) {
		this.tglLahir = tglLahir;
	}

	public String getNoHp() {
		return noHp;
	}

	public void setNo_hp(String noHp) {
		this.noHp = noHp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setNoHp(String noHp) {
		this.noHp = noHp;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}
