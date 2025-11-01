package PMS.SmartPay.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/summary")
	public Map<String, Object> getDashboardSummary() {
		return dashboardService.getDashboardSummary();
	}

	@GetMapping("/tax-by-month")
	public Map<String, Double> getTaxByMonth() {
		return dashboardService.getTaxByMonth();
	}
}