package PMS.SmartPay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import PMS.SmartPay.entity.Deduction;
import PMS.SmartPay.entity.Employee;
import PMS.SmartPay.entity.Tax;
import PMS.SmartPay.repository.DeductionRepository;
import PMS.SmartPay.repository.EmployeeRepository;
import PMS.SmartPay.repository.TaxRepository;

@Service
public class DeductionService {

	private final DeductionRepository deductionRepository;
	private final EmployeeRepository employeeRepository;
	private final TaxRepository taxRepository;

	public DeductionService(DeductionRepository deductionRepository, EmployeeRepository employeeRepository,
			TaxRepository taxRepository) {
		this.deductionRepository = deductionRepository;
		this.employeeRepository = employeeRepository;
		this.taxRepository = taxRepository;
	}

	public List<Deduction> getAllDeductions() {
		return deductionRepository.findAll();
	}

	public List<Deduction> getDeductionsByEmployee(Long empId) {
		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		return deductionRepository.findByEmployee(employee);
	}

	public Deduction addDeduction(Long empId, Long taxId, Deduction deduction) {
		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		Tax tax = taxRepository.findById(taxId).orElseThrow(() -> new RuntimeException("Tax not found"));
		deduction.setEmployee(employee);
		deduction.setTax(tax);
		return deductionRepository.save(deduction);
	}

	public void deleteDeduction(Long id) {
		deductionRepository.deleteById(id);
	}
}