package PMS.SmartPay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.constants.LeaveStatus;
import PMS.SmartPay.entity.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{

	List<LeaveRequest> findByEmployeeId(Long employeeId);

    List<LeaveRequest> findByStatus(LeaveStatus status);

    @Query("SELECT l FROM LeaveRequest l WHERE l.startDate BETWEEN :start AND :end OR l.endDate BETWEEN :start AND :end")
    List<LeaveRequest> findLeavesWithinDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
