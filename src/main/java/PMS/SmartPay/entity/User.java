package PMS.SmartPay.entity;


import PMS.SmartPay.constants.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String username;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleType role;	
	
}
