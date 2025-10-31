package PMS.SmartPay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import PMS.SmartPay.entity.Tax;
import PMS.SmartPay.repository.TaxRepository;

@Service
public class TaxService {

	private final TaxRepository taxRepository;

	public TaxService(TaxRepository taxRepository) {
		this.taxRepository = taxRepository;
	}

	public List<Tax> getAllTaxes() {
		return taxRepository.findAll();
	}

	public Tax addTax(Tax tax) {
		return taxRepository.save(tax);
	}

	public Tax updateTax(Long id, Tax taxDetails) {
		return taxRepository.findById(id).map(tax -> {
			tax.setName(taxDetails.getName());
			tax.setDescription(taxDetails.getDescription());
			tax.setPercentage(taxDetails.getPercentage());
			tax.setIsActive(taxDetails.getIsActive());
			return taxRepository.save(tax);
		}).orElseThrow(() -> new RuntimeException("Tax not found"));
	}

	public void deleteTax(Long id) {
		taxRepository.deleteById(id);
	}
}
