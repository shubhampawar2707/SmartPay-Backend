package PMS.SmartPay.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PMS.SmartPay.constants.AttendanceStatus;
import PMS.SmartPay.constants.PayrollStatus;
import PMS.SmartPay.entity.Attendance;
import PMS.SmartPay.entity.Employee;
import PMS.SmartPay.entity.Payroll;
import PMS.SmartPay.repository.AttendanceRepository;
import PMS.SmartPay.repository.EmployeeRepository;
import PMS.SmartPay.repository.LeaveRequestRepository;
import PMS.SmartPay.repository.PayrollRepository;

@Service
public class PayrollService {

	@Autowired
	private PayrollRepository payrollRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private LeaveRequestRepository leaveRepository;

	// Generate Payroll for One Employee
	public Payroll generatePayroll(Long employeeId, int month, int year) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		// Fetch attendance data
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

		List<Attendance> attendanceList = attendanceRepository.findAttendanceByEmployeeAndDateRange(employeeId, start,
				end);

		int totalWorkingDays = start.lengthOfMonth();
		int presentDays = (int) attendanceList.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();

		int leaveDays = (int) attendanceList.stream().filter(a -> a.getStatus() == AttendanceStatus.LEAVE).count();

		int absentDays = totalWorkingDays - (presentDays + leaveDays);

		// Basic Salary from Employee entity
		double basic = employee.getBaseSalary();

		// Deductions for unpaid leaves (optional logic)
		double perDaySalary = basic / totalWorkingDays;
		double leaveDeduction = absentDays * perDaySalary;

		// Bonuses can be fixed or calculated based on business logic
		double bonus = 1000.0; // example

		double netSalary = basic - leaveDeduction + bonus;

		Payroll payroll = new Payroll();
		payroll.setEmployee(employee);
		payroll.setMonth(month);
		payroll.setYear(year);
		payroll.setBasicSalary(basic);
		payroll.setWorkingDays(totalWorkingDays);
		payroll.setPresentDays(presentDays);
		payroll.setAbsentDays(absentDays);
		payroll.setLeaveDays(leaveDays);
		payroll.setBonus(bonus);
		payroll.setDeductions(leaveDeduction);
		payroll.setNetSalary(netSalary);
		payroll.setStatus(PayrollStatus.GENERATED);
		payroll.setGeneratedDate(LocalDate.now());

		return payrollRepository.save(payroll);
	}

	// Fetch Payrolls
	public List<Payroll> getAllPayrolls() {
		return payrollRepository.findAll();
	}

	public List<Payroll> getPayrollByEmployee(Long employeeId) {
		return payrollRepository.findByEmployeeId(employeeId);
	}

	public List<Payroll> getPayrollByMonthYear(int month, int year) {
		return payrollRepository.findByMonthAndYear(month, year);
	}

	// Mark Salary as Paid
	public Payroll markAsPaid(Long id) {
		Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new RuntimeException("Payroll not found"));
		payroll.setStatus(PayrollStatus.PAID);
		payroll.setPaymentDate(LocalDate.now());
		return payrollRepository.save(payroll);
	}

	// Delete Payroll
	public void deletePayroll(Long id) {
		payrollRepository.deleteById(id);
	}
}
