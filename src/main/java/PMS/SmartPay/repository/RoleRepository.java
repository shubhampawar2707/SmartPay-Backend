package PMS.SmartPay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.entity.Role;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}