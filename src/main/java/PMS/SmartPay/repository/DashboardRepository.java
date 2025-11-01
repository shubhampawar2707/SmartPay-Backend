package PMS.SmartPay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import PMS.SmartPay.entity.Payroll;

public interface DashboardRepository extends JpaRepository<Payroll, Long> {

	@Query("SELECT SUM(p.bonus) FROM Payroll p")
	Double sumTotalBonus();

	@Query("SELECT SUM(p.deductions) FROM Payroll p")
	Double sumTotalDeductions();

	@Query("SELECT SUM(p.netSalary) FROM Payroll p")
	Double sumTotalNetSalary();
}