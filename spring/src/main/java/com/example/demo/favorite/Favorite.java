package com.example.demo.favorite;

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
public class Favorite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long uid;
	private Long pid;
	

	protected Favorite() {}

	Favorite(Long uid, Long pid) {
		this.uid = uid;
		this.pid = pid;
	}

	@Override
	public String toString() {
		return String.format("ServiceUser[id=%d, user=%d, property=%d]", id, uid, pid);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	


}