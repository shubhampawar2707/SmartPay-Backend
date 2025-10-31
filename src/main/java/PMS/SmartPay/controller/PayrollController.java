package PMS.SmartPay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.entity.Payroll;
import PMS.SmartPay.service.PayrollService;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin("*")
public class PayrollController {

	@Autowired
	private PayrollService payrollService;

	// Generate Payroll for Employee
	@PostMapping("/generate/{employeeId}")
	public ResponseEntity<Payroll> generatePayroll(@PathVariable Long employeeId, @RequestParam int month,
			@RequestParam int year) {
		return ResponseEntity.ok(payrollService.generatePayroll(employeeId, month, year));
	}

	// Get all payrolls
	@GetMapping("/all")
	public ResponseEntity<List<Payroll>> getAllPayrolls() {
		return ResponseEntity.ok(payrollService.getAllPayrolls());
	}

	// Get by employee
	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<List<Payroll>> getByEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.ok(payrollService.getPayrollByEmployee(employeeId));
	}

	// Get by month & year
	@GetMapping("/filter")
	public ResponseEntity<List<Payroll>> getByMonthYear(@RequestParam int month, @RequestParam int year) {
		return ResponseEntity.ok(payrollService.getPayrollByMonthYear(month, year));
	}

	// Mark Payroll as Paid
	@PutMapping("/{id}/paid")
	public ResponseEntity<Payroll> markAsPaid(@PathVariable Long id) {
		return ResponseEntity.ok(payrollService.markAsPaid(id));
	}

	// Delete Payroll
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePayroll(@PathVariable Long id) {
		payrollService.deletePayroll(id);
		return ResponseEntity.ok("Payroll deleted successfully");
	}
}
