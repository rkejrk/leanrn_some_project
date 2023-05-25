package com.example.demo.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * データベースの値を格納する
 * @author tanaka
 *
 */
@Entity
public class ServiceUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String phone;
	

	protected ServiceUser() {}

	ServiceUser(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format("ServiceUser['id'=%d, 'phone'='%s']", id, phone);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	


}