package com.custom.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.custom.domain.User;
import com.custom.service.UserService;
import com.custom.spring.hibernate.dao.EntityDao;

/**
 * 
 * @author admin
 * @date 2016年1月9日 上午11:57:30
 */
@Service("userService")
public class UserServiceImpl implements UserService<User, Integer> {

	@Resource(name = "userDao")
	private EntityDao<User, Integer> userDao;

	@Override
	@Transactional
	public void listAll() {

		for (int i = 0; i < 5; i++) {
			User user = new User(i, "test" + i, i * 2);
			userDao.save(user);
		}

		List<User> users = userDao.getAll();
		System.out.println(users.size());
	}
}
