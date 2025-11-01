package PMS.SmartPay.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.DTO.LoginRequest;
import PMS.SmartPay.DTO.RegisterRequest;
import PMS.SmartPay.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterRequest req) {
		return ResponseEntity.ok(authService.registerUser(req));
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest req) {
		try {
			String token = authService.loginUser(req);
			return ResponseEntity.ok(Map.of("token", token, "message", "Login successful"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
		}
	}
}
