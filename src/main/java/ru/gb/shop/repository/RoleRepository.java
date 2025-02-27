package ru.gb.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.shop.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}