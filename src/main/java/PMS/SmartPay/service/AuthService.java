package PMS.SmartPay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import PMS.SmartPay.DTO.LoginRequest;
import PMS.SmartPay.DTO.RegisterRequest;
import PMS.SmartPay.entity.User;
import PMS.SmartPay.repository.UserRepository;
import PMS.SmartPay.security.JwtService;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public String registerUser(RegisterRequest req) {
		if (userRepository.findByUsername(req.getUsername()).isPresent()) {
			return "User already exists!";
		}

		User user = User.builder().username(req.getUsername()).password(passwordEncoder.encode(req.getPassword()))
				.role(req.getRole()).build();

		userRepository.save(user);
		return "User registered successfully!";
	}

	public String loginUser(LoginRequest req) {
		User user = userRepository.findByUsername(req.getUsername())
				.orElseThrow(() -> new RuntimeException("Invalid credentials"));

		if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			return jwtService.generateToken(user.getUsername());
		} else {
			throw new RuntimeException("Invalid credentials");
		}
	}
}
