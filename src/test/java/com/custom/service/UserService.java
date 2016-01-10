package com.custom.service;

import java.io.Serializable;

/**
 * 
 * @author admin
 * @date 2016年1月9日 上午11:57:19
 */
public interface UserService<T, ID extends Serializable> {

	public void listAll();
}
