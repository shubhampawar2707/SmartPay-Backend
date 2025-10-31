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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.constants.LeaveStatus;
import PMS.SmartPay.entity.LeaveRequest;
import PMS.SmartPay.service.LeaveRequestService;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin("*")
public class LeaveRequestController {

	@Autowired
	private LeaveRequestService leaveService;

	// Apply Leave
	@PostMapping("/apply/{employeeId}")
	public ResponseEntity<LeaveRequest> applyLeave(@PathVariable Long employeeId, @RequestBody LeaveRequest leave) {
		return ResponseEntity.ok(leaveService.applyLeave(employeeId, leave));
	}

	// All Leaves
	@GetMapping("/all")
	public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
		return ResponseEntity.ok(leaveService.getAllLeaves());
	}

	// Leaves by Employee
	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<List<LeaveRequest>> getLeavesByEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.ok(leaveService.getLeavesByEmployee(employeeId));
	}

	// Leaves by Status
	@GetMapping("/status/{status}")
	public ResponseEntity<List<LeaveRequest>> getLeavesByStatus(@PathVariable LeaveStatus status) {
		return ResponseEntity.ok(leaveService.getLeavesByStatus(status));
	}

	// Update Leave Status
	@PutMapping("/{id}/status")
	public ResponseEntity<LeaveRequest> updateStatus(@PathVariable Long id, @RequestParam LeaveStatus status,
			@RequestParam(required = false) String approvedBy, @RequestParam(required = false) String remarks) {
		return ResponseEntity.ok(leaveService.updateLeaveStatus(id, status, approvedBy, remarks));
	}

	// Cancel Leave
	@PutMapping("/{id}/cancel")
	public ResponseEntity<LeaveRequest> cancelLeave(@PathVariable Long id) {
		return ResponseEntity.ok(leaveService.cancelLeave(id));
	}

	// Delete Leave
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteLeave(@PathVariable Long id) {
		leaveService.deleteLeave(id);
		return ResponseEntity.ok("Leave deleted successfully");
	}
}
