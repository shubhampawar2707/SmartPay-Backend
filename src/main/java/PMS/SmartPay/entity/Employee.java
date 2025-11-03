package PMS.SmartPay.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import PMS.SmartPay.constants.EmployeeStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;

	private String designation;
	private Double baseSalary;

	@Column(name = "join_date")
	private String joinDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	@JsonIgnoreProperties({ "employees" })
	private Department department;

	// 🔥 Prevent recursion from role
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	@JsonIgnoreProperties({ "employees" })
	private Role role;

	@Enumerated(EnumType.STRING)
	private EmployeeStatus status;
}
