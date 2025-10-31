package PMS.SmartPay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.repository.EmployeeRepository;
import PMS.SmartPay.service.PayslipService;

@RestController
@RequestMapping("/api/payslip")
@CrossOrigin
public class PayslipController {

	private final PayslipService payslipService;

	@Autowired
	private EmployeeRepository employeeRepository;

	public PayslipController(PayslipService payslipService) {
		this.payslipService = payslipService;
	}

//	@GetMapping("/generate/{empId}/{month}")
//	public ResponseEntity<InputStreamResource> generatePayslip(@PathVariable Long empId, @PathVariable Integer month) {
//		try {
//			var bis = payslipService.generatePayslip(empId, month);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Content-Disposition", "inline; filename=payslip_" + month + ".pdf");
//
//			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
//					.body(new InputStreamResource(bis));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//		}
//	

	@GetMapping("/generate/{empId}/{month}")
	public ResponseEntity<?> generatePayslip(@PathVariable(required = false) Long empId,
			@PathVariable(required = false) Integer month) {
		if (empId == null || month == null) {
			return ResponseEntity.badRequest().body("Employee ID and Month are required!");
		}

		try {
			var bis = payslipService.generatePayslip(empId, month);
			if (bis == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Payslip not available for given employee/month");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=payslip_" + month + ".pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

}