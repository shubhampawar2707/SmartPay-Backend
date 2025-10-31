package PMS.SmartPay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PMS.SmartPay.constants.LeaveStatus;
import PMS.SmartPay.entity.Employee;
import PMS.SmartPay.entity.LeaveRequest;
import PMS.SmartPay.repository.EmployeeRepository;
import PMS.SmartPay.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {
	@Autowired
    private LeaveRequestRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Apply for Leave
    public LeaveRequest applyLeave(Long employeeId, LeaveRequest leave) {
        Employee emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        leave.setEmployee(emp);
        leave.setStatus(LeaveStatus.PENDING);
        return leaveRepository.save(leave);
    }

    // View All Leaves
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // View Employee-specific Leaves
    public List<LeaveRequest> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    // Filter Leaves by Status
    public List<LeaveRequest> getLeavesByStatus(LeaveStatus status) {
        return leaveRepository.findByStatus(status);
    }

    // Update Leave (Approve/Reject)
    public LeaveRequest updateLeaveStatus(Long id, LeaveStatus status, String approvedBy, String remarks) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(status);
        leave.setApprovedBy(approvedBy);
        leave.setRemarks(remarks);
        return leaveRepository.save(leave);
    }

    // Cancel Leave
    public LeaveRequest cancelLeave(Long id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(LeaveStatus.CANCELLED);
        return leaveRepository.save(leave);
    }

    // Delete Leave
    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
