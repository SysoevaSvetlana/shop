package ru.gb.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.shop.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
