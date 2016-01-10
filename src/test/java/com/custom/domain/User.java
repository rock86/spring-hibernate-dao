package com.custom.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class User extends BaseDomain<Integer> {

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;

	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
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
		return "User [name=" + name + ", age=" + age + ", getId()=" + getId() + ", getVersion()=" + getVersion() + "]";
	}

	
}
