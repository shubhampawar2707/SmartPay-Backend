package PMS.SmartPay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.entity.Deduction;
import PMS.SmartPay.service.DeductionService;

@RestController
@RequestMapping("/api/deductions")
@CrossOrigin
public class DeductionController {

	private final DeductionService deductionService;

	public DeductionController(DeductionService deductionService) {
		this.deductionService = deductionService;
	}

	@GetMapping
	public List<Deduction> getAllDeductions() {
		return deductionService.getAllDeductions();
	}

	@GetMapping("/employee/{empId}")
	public List<Deduction> getByEmployee(@PathVariable Long empId) {
		return deductionService.getDeductionsByEmployee(empId);
	}

	@PostMapping("/{empId}/{taxId}")
	public Deduction addDeduction(@PathVariable Long empId, @PathVariable Long taxId,
			@RequestBody Deduction deduction) {
		return deductionService.addDeduction(empId, taxId, deduction);
	}

	@DeleteMapping("/{id}")
	public void deleteDeduction(@PathVariable Long id) {
		deductionService.deleteDeduction(id);
	}
}
