package PMS.SmartPay.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
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

		double baseSalary = payroll.getBasicSalary() != null ? payroll.getBasicSalary() : 0.0;
		double bonus = payroll.getBonus() != null ? payroll.getBonus() : 0.0;
		double grossSalary = baseSalary + bonus;
		double payrollDeductions = payroll.getDeductions() != null ? payroll.getDeductions() : 0.0;
		double totalDeductions = deductions.stream().mapToDouble(d -> d.getAmount() != null ? d.getAmount() : 0.0).sum()
				+ payrollDeductions;
		double netPay = grossSalary - totalDeductions;

		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();

		// ===== Header =====
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);

		Paragraph companyName = new Paragraph("SurePlus Technologies Pvt. Ltd.", titleFont);
		companyName.setAlignment(Element.ALIGN_CENTER);
		document.add(companyName);

		Paragraph address = new Paragraph("221 Hillman Ave, Orlando, FL 32801  |  www.sureplus.com", smallFont);
		address.setAlignment(Element.ALIGN_CENTER);
		document.add(address);
		document.add(Chunk.NEWLINE);

		Paragraph payslipTitle = new Paragraph("Salary Slip - " + Month.of(month).name() + " " + payroll.getYear(),
				new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
		payslipTitle.setAlignment(Element.ALIGN_CENTER);
		document.add(payslipTitle);
		document.add(Chunk.NEWLINE);

		// ===== Pay Info =====
		PdfPTable payInfo = new PdfPTable(2);
		payInfo.setWidthPercentage(100);
		payInfo.setSpacingBefore(5f);
		payInfo.setSpacingAfter(10f);
		payInfo.getDefaultCell().setPadding(8);
		payInfo.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

		payInfo.addCell("Pay Period");
		payInfo.addCell(Month.of(month).name() + " " + payroll.getYear());
		payInfo.addCell("Pay Date");
		payInfo.addCell(payroll.getPaymentDate() != null ? payroll.getPaymentDate().toString() : "N/A");
		payInfo.addCell("Employee Name");
		payInfo.addCell(employee.getFirstName() + " " + employee.getLastName());
		payInfo.addCell("Department");
		payInfo.addCell(employee.getDepartment() != null ? employee.getDepartment().getName() : "N/A");
		payInfo.addCell("Designation");
		payInfo.addCell(employee.getDesignation() != null ? employee.getDesignation() : "N/A");
		payInfo.addCell("Email");
		payInfo.addCell(employee.getEmail() != null ? employee.getEmail() : "N/A");
		document.add(payInfo);
		document.add(Chunk.NEWLINE);

		// ===== Earnings & Deductions =====
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		table.setWidths(new int[] { 2, 2, 2, 2 });
		table.getDefaultCell().setPadding(8);
		table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

		Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		BaseColor headerColor = new BaseColor(230, 230, 230);

		String[] headers = { "Earnings", "Amount", "Deductions", "Amount" };
		for (String header : headers) {
			PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
			headerCell.setBackgroundColor(headerColor);
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setPadding(8);
			headerCell.setBorderColor(BaseColor.LIGHT_GRAY);
			table.addCell(headerCell);
		}

		// Earnings & deductions with padding
		table.addCell("Basic Salary");
		table.addCell(String.format("%.2f", baseSalary));
		table.addCell("Tax");
		table.addCell(deductions.size() > 0 ? String.format("%.2f", deductions.get(0).getAmount()) : "0.00");

		table.addCell("Bonus");
		table.addCell(String.format("%.2f", bonus));
		table.addCell("Insurance");
		table.addCell(deductions.size() > 1 ? String.format("%.2f", deductions.get(1).getAmount()) : "0.00");

		table.addCell("Gross Salary");
		table.addCell(String.format("%.2f", grossSalary));
		table.addCell("Other Deductions");
		table.addCell(String.format("%.2f", payrollDeductions));
		document.add(table);

		// ===== Net Pay Summary =====
		PdfPTable netPayTable = new PdfPTable(2);
		netPayTable.setWidthPercentage(40);
		netPayTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		netPayTable.getDefaultCell().setPadding(8);
		netPayTable.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

		PdfPCell netLabel = new PdfPCell(new Phrase("Net Pay", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		PdfPCell netValue = new PdfPCell(
				new Phrase("₹" + String.format("%.2f", netPay), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
		netLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
		netValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
		netLabel.setPadding(8);
		netValue.setPadding(8);
		netLabel.setBorderColor(BaseColor.LIGHT_GRAY);
		netValue.setBorderColor(BaseColor.LIGHT_GRAY);
		netPayTable.addCell(netLabel);
		netPayTable.addCell(netValue);
		document.add(netPayTable);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		// ===== Signature Section =====
		PdfPTable signTable = new PdfPTable(2);
		signTable.setWidthPercentage(100);
		signTable.setSpacingBefore(30f);

		PdfPCell empSign = new PdfPCell();
		empSign.setBorder(Rectangle.NO_BORDER);
		empSign.setPaddingTop(30);
		empSign.addElement(new Paragraph("Employee Signature:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		empSign.addElement(new Paragraph("________________________"));

		PdfPCell hrSign = new PdfPCell();
		hrSign.setBorder(Rectangle.NO_BORDER);
		hrSign.setPaddingTop(30);
		hrSign.addElement(
				new Paragraph("HR Department Signature:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		hrSign.addElement(new Paragraph("________________________"));
		hrSign.setHorizontalAlignment(Element.ALIGN_RIGHT);

		signTable.addCell(empSign);
		signTable.addCell(hrSign);
		document.add(signTable);

		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

}