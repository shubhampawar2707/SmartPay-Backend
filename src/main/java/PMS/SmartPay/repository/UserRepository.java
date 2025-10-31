package PMS.SmartPay.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import PMS.SmartPay.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
	Optional<User> findByUsername(String username);
}
