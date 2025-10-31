package PMS.SmartPay.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import PMS.SmartPay.entity.Deduction;
import PMS.SmartPay.entity.Employee;
import PMS.SmartPay.entity.Payroll;
import PMS.SmartPay.repository.DeductionRepository;
import PMS.SmartPay.repository.EmployeeRepository;
import PMS.SmartPay.repository.PayrollRepository;

@Service
public class PayslipService {

	private final EmployeeRepository employeeRepository;
	private final PayrollRepository payrollRepository;
	private final DeductionRepository deductionRepository;

	public PayslipService(EmployeeRepository employeeRepository, PayrollRepository payrollRepository,
			DeductionRepository deductionRepository) {
		this.employeeRepository = employeeRepository;
		this.payrollRepository = payrollRepository;
		this.deductionRepository = deductionRepository;
	}

	public ByteArrayInputStream generatePayslip(Long employeeId, Integer month) throws Exception {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		Payroll payroll = payrollRepository.findByEmployeeAndMonth(employee, month)
				.orElseThrow(() -> new RuntimeException("Payroll not found for this month"));

		List<Deduction> deductions = deductionRepository.findByEmployee(employee);

		// ✅ Safely handle possible nulls
		double baseSalary = payroll.getBasicSalary() != null ? payroll.getBasicSalary() : 0.0;
		double bonus = payroll.getBonus() != null ? payroll.getBonus() : 0.0;
		double grossSalary = baseSalary + bonus; // recompute for clarity
		double payrollDeductions = payroll.getDeductions() != null ? payroll.getDeductions() : 0.0;

		// Sum up all deductions from Deduction table + payroll table field
		double totalDeductions = deductions.stream().mapToDouble(d -> d.getAmount() != null ? d.getAmount() : 0.0).sum()
				+ payrollDeductions;

		// Final Net Pay
		double netPay = grossSalary - totalDeductions;

		// PDF document setup
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, out);
		document.open();

		// Title
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		Paragraph title = new Paragraph("Payslip for " + month, titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		document.add(Chunk.NEWLINE);

		// Employee Info
		PdfPTable empTable = new PdfPTable(2);
		empTable.setWidthPercentage(100);
		empTable.addCell("Employee Name");
		empTable.addCell(employee.getFirstName() + " " + employee.getLastName());
		empTable.addCell("Department");
		empTable.addCell(employee.getDepartment() != null ? employee.getDepartment().getName() : "N/A");
		empTable.addCell("Designation");
		empTable.addCell(employee.getDesignation() != null ? employee.getDesignation() : "N/A");
		empTable.addCell("Email");
		empTable.addCell(employee.getEmail() != null ? employee.getEmail() : "N/A");
		document.add(empTable);
		document.add(Chunk.NEWLINE);

		// Salary Info
		PdfPTable salaryTable = new PdfPTable(2);
		salaryTable.setWidthPercentage(100);
		salaryTable.addCell("Base Salary");
		salaryTable.addCell(String.format("%.2f", baseSalary));
		salaryTable.addCell("Bonus");
		salaryTable.addCell(String.format("%.2f", bonus));
		salaryTable.addCell("Gross Salary");
		salaryTable.addCell(String.format("%.2f", grossSalary));
		document.add(salaryTable);
		document.add(Chunk.NEWLINE);

		// Deductions
		PdfPTable deductionTable = new PdfPTable(2);
		deductionTable.setWidthPercentage(100);
		deductionTable.addCell("Deduction Type");
		deductionTable.addCell("Amount");
		for (Deduction deduction : deductions) {
			deductionTable.addCell(
					deduction.getTax() != null && deduction.getTax().getName() != null ? deduction.getTax().getName()
							: "N/A");
			deductionTable.addCell(String.format("%.2f", deduction.getAmount() != null ? deduction.getAmount() : 0.0));
		}
		document.add(deductionTable);
		document.add(Chunk.NEWLINE);

		// Net Pay
		Font netPayFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
		Paragraph netPayPara = new Paragraph("Net Pay: ₹" + String.format("%.2f", netPay), netPayFont);
		netPayPara.setAlignment(Element.ALIGN_RIGHT);
		document.add(netPayPara);

		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

}