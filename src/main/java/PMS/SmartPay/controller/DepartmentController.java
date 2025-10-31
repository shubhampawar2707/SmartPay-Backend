package PMS.SmartPay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PMS.SmartPay.entity.Department;
import PMS.SmartPay.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<Department> create(@RequestBody Department department) {
		return ResponseEntity.ok(departmentService.createDepartment(department));
	}

	@GetMapping
	public ResponseEntity<List<Department>> getAll() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Department> getById(@PathVariable Long id) {
		return ResponseEntity.ok(departmentService.getDepartmentById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Department> update(@PathVariable Long id, @RequestBody Department department) {
		return ResponseEntity.ok(departmentService.updateDepartment(id, department));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.ok("Department deleted successfully");
	}
}
