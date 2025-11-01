package PMS.SmartPay.DTO;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class DashboardSummaryDTO {

	private long totalEmployees;
	private long totalDepartments;
	private long totalLeaves;
	private BigDecimal totalPayrollPaid;
	private BigDecimal totalDeductions;
	private Map<String, Long> employeesByDepartment;
	private Map<String, BigDecimal> payrollByMonth;
	private Map<String, BigDecimal> taxByMonth;

}
