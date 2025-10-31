package PMS.SmartPay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

	List<Payroll> findByEmployeeId(Long employeeId);

	@Query("SELECT p FROM Payroll p WHERE p.year = :year AND p.month = :month")
	List<Payroll> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
