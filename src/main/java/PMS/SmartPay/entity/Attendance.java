package PMS.SmartPay.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import PMS.SmartPay.constants.AttendanceStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "attendance")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee employee;

	private LocalDate attendanceDate;
	private LocalTime checkInTime;
	private LocalTime checkOutTime;
	private Double workHours;

	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;

	private String remarks;

}
