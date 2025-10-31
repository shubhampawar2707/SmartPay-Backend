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

import PMS.SmartPay.entity.Role;
import PMS.SmartPay.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<Role> create(@RequestBody Role role) {
		return ResponseEntity.ok(roleService.createRole(role));
	}

	@GetMapping
	public ResponseEntity<List<Role>> getAll() {
		return ResponseEntity.ok(roleService.getAllRoles());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Role> getById(@PathVariable Long id) {
		return ResponseEntity.ok(roleService.getRoleById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role role) {
		return ResponseEntity.ok(roleService.updateRole(id, role));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		roleService.deleteRole(id);
		return ResponseEntity.ok("Role deleted successfully");
	}
}
