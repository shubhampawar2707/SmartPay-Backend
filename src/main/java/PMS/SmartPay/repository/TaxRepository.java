package PMS.SmartPay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.entity.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

	@Query("SELECT FUNCTION('DATE_FORMAT', t.createdDate, '%Y-%m') AS month, SUM(t.percentage) "
			+ "FROM Tax t GROUP BY FUNCTION('DATE_FORMAT', t.createdDate, '%Y-%m')")
	List<Object[]> findTaxByMonth();

	@Query("SELECT COUNT(t) FROM Tax t WHERE t.isActive = true")
	Long countActiveTaxes();

	@Query("SELECT SUM(t.percentage) FROM Tax t WHERE t.isActive = true")
	Double sumActiveTaxPercentage();
}
