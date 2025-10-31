package PMS.SmartPay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PMS.SmartPay.entity.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
}
