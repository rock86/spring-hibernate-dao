package com.custom.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author admin
 * @date 2016年1月10日 下午1:25:18
 * @param <ID>
 */
@MappedSuperclass
public class BaseDomain<ID extends Number> implements Serializable {

	private static final long serialVersionUID = 1L;

	private ID id;
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
