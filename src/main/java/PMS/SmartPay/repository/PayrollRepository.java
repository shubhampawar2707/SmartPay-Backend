package PMS.SmartPay.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.entity.Employee;
import PMS.SmartPay.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

	List<Payroll> findByEmployeeId(Long employeeId);

	@Query("SELECT p FROM Payroll p WHERE p.year = :year AND p.month = :month")
	List<Payroll> findByMonthAndYear(@Param("month") Integer month, @Param("year") int year);

	Optional<Payroll> findByEmployeeAndMonth(Employee employee, Integer month);

	// Get all payrolls for a specific month
	List<Payroll> findByMonth(Integer month);

	// Get all payrolls for a specific employee
	List<Payroll> findByEmployee(Employee employee);

	@Query("SELECT SUM(p.netSalary) FROM Payroll p")
	BigDecimal sumTotalSalary();

	@Query("SELECT SUM(p.deductions) FROM Payroll p")
	Double sumTotalDeductions();

	@Query("SELECT FUNCTION('DATE_FORMAT', p.paymentDate, '%Y-%m') AS month, SUM(p.netSalary) "
			+ "FROM Payroll p GROUP BY FUNCTION('DATE_FORMAT', p.paymentDate, '%Y-%m')")
	Map<String, BigDecimal> findPayrollByMonth();

	@Query("SELECT SUM(p.bonus) FROM Payroll p")
	Double sumTotalBonus();

	@Query("SELECT SUM(p.netSalary) FROM Payroll p")
	Double sumTotalNetSalary();

}
