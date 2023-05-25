package com.example.demo.property;

import jakarta.persistence.Column;
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
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="image")
	private String image;

	@Column(name="address")
	private String address;
	

	public Property() {}

	public Property(String name, String image, String address) {
		this.name = name;
		this.image = image;
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format("ServiceUser[id=%d, name=%s, image=%s, address=%s]", id, name, image, address);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}