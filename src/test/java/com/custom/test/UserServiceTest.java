package com.custom.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.custom.domain.User;
import com.custom.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-configs/spring-*.xml" })
public class UserServiceTest {

	@Resource(name = "userService")
	private UserService<User, Integer> userService;

	@Test
	public void main() {
		System.out.println("===run====");

		User user = new User("123", 12);
		System.out.println(user);
		userService.listAll();
	}
}
