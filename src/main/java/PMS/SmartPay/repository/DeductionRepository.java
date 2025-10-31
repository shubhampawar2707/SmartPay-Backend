package PMS.SmartPay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.entity.Deduction;
import PMS.SmartPay.entity.Employee;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {
	List<Deduction> findByEmployee(Employee employee);

	List<Deduction> findByMonth(String month);
}