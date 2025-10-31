package PMS.SmartPay.entity;

import java.time.LocalDate;

import PMS.SmartPay.constants.PayrollStatus;
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
import lombok.Data;

@Data
@Entity
@Table(name = "payroll")
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	private int month;
	private Integer year;

	private Double basicSalary;
	private Double grossSalary;
	private int workingDays;
	private int presentDays;
	private int absentDays;
	private int leaveDays;

	private Double bonus = 0.0;
	private Double deductions = 0.0;
	private Double netSalary = 0.0;

	@Enumerated(EnumType.STRING)
	private PayrollStatus status = PayrollStatus.GENERATED;

	private LocalDate generatedDate;
	private LocalDate paymentDate;

}
