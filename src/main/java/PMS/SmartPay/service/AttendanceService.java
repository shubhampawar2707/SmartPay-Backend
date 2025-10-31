package PMS.SmartPay.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PMS.SmartPay.entity.Attendance;
import PMS.SmartPay.entity.Employee;
import PMS.SmartPay.repository.AttendanceRepository;
import PMS.SmartPay.repository.EmployeeRepository;

@Service
public class AttendanceService {

	@Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Attendance markAttendance(Long employeeId, Attendance attendance) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        attendance.setEmployee(employee);

        // Auto calculate work hours if checkIn & checkOut exist
        if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
            long minutes = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()).toMinutes();
            attendance.setWorkHours(minutes / 60.0);
        }

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    public List<Attendance> getAttendanceBetweenDates(Long employeeId, LocalDate start, LocalDate end) {
        return attendanceRepository.findAttendanceByEmployeeAndDateRange(employeeId, start, end);
    }

    public Attendance updateAttendance(Long id, Attendance updated) {
        Attendance existing = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        existing.setCheckInTime(updated.getCheckInTime());
        existing.setCheckOutTime(updated.getCheckOutTime());
        existing.setRemarks(updated.getRemarks());
        existing.setStatus(updated.getStatus());

        // Recalculate work hours
        if (updated.getCheckInTime() != null && updated.getCheckOutTime() != null) {
            long minutes = Duration.between(updated.getCheckInTime(), updated.getCheckOutTime()).toMinutes();
            existing.setWorkHours(minutes / 60.0);
        }

        return attendanceRepository.save(existing);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
