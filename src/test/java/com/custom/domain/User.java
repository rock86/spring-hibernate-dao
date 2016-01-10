package com.custom.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.custom.spring.hibernate.annotation.EntityDao;

/**
 * 
 * @author admin
 * @date 2016年1月9日 上午11:53:34
 */
@Entity
@EntityDao
@Table(name = "T_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private int age;

	
	
	public User(Integer id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	@Id
	@Column(name = "ID", unique = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGE")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
}
