package PMS.SmartPay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PMS.SmartPay.entity.Department;
import PMS.SmartPay.repository.DepartmentRepository;

@Service

public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public Department createDepartment(Department department) {
		return departmentRepository.save(department);
	}

	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
	}

	public Department updateDepartment(Long id, Department updated) {
		Department department = getDepartmentById(id);
		department.setName(updated.getName());
		department.setDescription(updated.getDescription());
		return departmentRepository.save(department);
	}

	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
	}
}
