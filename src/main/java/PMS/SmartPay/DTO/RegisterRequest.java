package PMS.SmartPay.DTO;

import PMS.SmartPay.constants.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RegisterRequest {
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private RoleType role;	
}
