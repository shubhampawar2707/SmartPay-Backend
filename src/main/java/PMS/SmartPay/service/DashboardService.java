package PMS.SmartPay.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PMS.SmartPay.repository.DepartmentRepository;
import PMS.SmartPay.repository.EmployeeRepository;
import PMS.SmartPay.repository.PayrollRepository;
import PMS.SmartPay.repository.TaxRepository;

@Service
public class DashboardService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private PayrollRepository payrollRepository;

	@Autowired
	private TaxRepository taxRepository;

	// --- Basic Totals ---

	public Map<String, Object> getDashboardSummary() {
		Map<String, Object> summary = new LinkedHashMap<>();

		Long totalEmployees = employeeRepository.count();
		Long totalDepartments = departmentRepository.count();
		Long totalPayrolls = payrollRepository.count();

		Double totalBonus = payrollRepository.sumTotalBonus();
		Double totalDeductions = payrollRepository.sumTotalDeductions();
		Double totalNetSalary = payrollRepository.sumTotalNetSalary();

		Double totalTaxes = taxRepository.sumActiveTaxPercentage();
		Long activeTaxCount = taxRepository.countActiveTaxes();

		summary.put("totalEmployees", totalEmployees);
		summary.put("totalDepartments", totalDepartments);
		summary.put("totalPayrolls", totalPayrolls);
		summary.put("totalBonus", totalBonus != null ? totalBonus : 0.0);
		summary.put("totalDeductions", totalDeductions != null ? totalDeductions : 0.0);
		summary.put("totalNetSalary", totalNetSalary != null ? totalNetSalary : 0.0);
		summary.put("activeTaxes", activeTaxCount);
		summary.put("totalTaxPercentage", totalTaxes != null ? totalTaxes : 0.0);

		return summary;
	}

	// --- Optional: Monthly Tax Chart ---

	public Map<String, Double> getTaxByMonth() {
		List<Object[]> results = taxRepository.findTaxByMonth();
		Map<String, Double> taxMap = new LinkedHashMap<>();

		for (Object[] row : results) {
			String month = (String) row[0];
			Double total = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
			taxMap.put(month != null ? month : "Unknown", total);
		}

		return taxMap;
	}

}
