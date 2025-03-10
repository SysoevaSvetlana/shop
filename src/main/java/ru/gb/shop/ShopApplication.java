package ru.gb.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gb.shop.service.UserService;

@SpringBootApplication
public class
ShopApplication  {
	@Autowired
	private UserService userService;

	public static void main(String[] args)  {
		SpringApplication.run(ShopApplication.class, args);
	}
//	@Override
//	public void run(String... args) {
//
//		userService.createAdminUser("admin", "admin123");
//	}

}
