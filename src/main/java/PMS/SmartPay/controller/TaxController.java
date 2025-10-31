package PMS.SmartPay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.entity.Tax;
import PMS.SmartPay.service.TaxService;

@RestController
@RequestMapping("/api/taxes")
@CrossOrigin
public class TaxController {

	private final TaxService taxService;

	public TaxController(TaxService taxService) {
		this.taxService = taxService;
	}

	@GetMapping
	public List<Tax> getAllTaxes() {
		return taxService.getAllTaxes();
	}

	@PostMapping
	public Tax addTax(@RequestBody Tax tax) {
		return taxService.addTax(tax);
	}

	@PutMapping("/{id}")
	public Tax updateTax(@PathVariable Long id, @RequestBody Tax tax) {
		return taxService.updateTax(id, tax);
	}

	@DeleteMapping("/{id}")
	public void deleteTax(@PathVariable Long id) {
		taxService.deleteTax(id);
	}
}