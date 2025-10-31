package PMS.SmartPay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PMS.SmartPay.entity.Role;
import PMS.SmartPay.repository.RoleRepository;

@Service

public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleById(Long id) {
		return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
	}

	public Role updateRole(Long id, Role updated) {
		Role role = getRoleById(id);
		role.setName(updated.getName());
		role.setDescription(updated.getDescription());
		role.setBaseSalary(updated.getBaseSalary());
		return roleRepository.save(role);
	}

	public void deleteRole(Long id) {
		roleRepository.deleteById(id);
	}
}
