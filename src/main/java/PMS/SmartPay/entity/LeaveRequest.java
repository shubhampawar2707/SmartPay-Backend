package PMS.SmartPay.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import PMS.SmartPay.constants.LeaveStatus;
import PMS.SmartPay.constants.LeaveType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "leave_request")
public class LeaveRequest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "employee_id", nullable = false)
	 private Employee employee;
	 
	 @Enumerated(EnumType.STRING)
	 private LeaveType leaveType;
	 
	 private LocalDate startDate;
	 private LocalDate endDate;
	 private int totalDays;

	 @Enumerated(EnumType.STRING)
	 private LeaveStatus status = LeaveStatus.PENDING;

	 private String reason;
	 private String approvedBy;
	 private String remarks;

	    // Auto-calculate totalDays before saving
	 @PrePersist
	 @PreUpdate
	 public void calculateTotalDays() {
		 if (startDate != null && endDate != null) {
		     this.totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
		 }
	 }
}
