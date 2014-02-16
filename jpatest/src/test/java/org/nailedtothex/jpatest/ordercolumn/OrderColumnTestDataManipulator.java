package org.nailedtothex.jpatest.ordercolumn;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class OrderColumnTestDataManipulator {

	@PersistenceContext
	EntityManager em;

	private static final Logger log = Logger.getLogger(OrderColumnTestDataManipulator.class.getName());

	@Transactional
	public void create() {
		Dept dept = new Dept();
		dept.setDeptName("Finance");
		dept.setEmployees(new ArrayList<Employee>());

		Employee emp1 = new Employee();
		emp1.setDept(dept);
		emp1.setFirstName("Taro");
		emp1.setLastName("Yamada");

		Employee emp2 = new Employee();
		emp2.setDept(dept);
		emp2.setFirstName("Jiro");
		emp2.setLastName("Suzuki");

		Employee emp3 = new Employee();
		emp3.setDept(dept);
		emp3.setFirstName("Saburo");
		emp3.setLastName("Tanaka");

		dept.getEmployees().add(emp1);
		dept.getEmployees().add(emp2);
		dept.getEmployees().add(emp3);

		em.persist(dept);
		em.persist(emp1);
		em.persist(emp2);
		em.persist(emp3);
	}

	@Transactional
	public void add(long deptId) {
		Dept dept = em.find(Dept.class, deptId);

		Employee emp = new Employee();
		emp.setDept(dept);
		emp.setFirstName("Jane");
		emp.setLastName("Doe");

		dept.getEmployees().add(emp);

		em.persist(emp);
	}

	@Transactional
	public void addRevised(long deptId) {
		Dept dept = em.find(Dept.class, deptId);

		Employee emp = new Employee();
		emp.setDept(dept);
		emp.setFirstName("Jane");
		emp.setLastName("Doe");

		dept.getEmployees().size(); // this make it work
		dept.getEmployees().add(emp);

		em.persist(emp);
	}

	@Transactional
	public void remove(long deptId, int pos) {
		Dept dept = em.find(Dept.class, deptId);
		Employee emp = dept.getEmployees().get(pos);

		dept.getEmployees().remove(emp);
		em.remove(emp);
	}

	@Transactional
	public void swap(long deptId, int pos1, int pos2) {
		Dept dept = em.find(Dept.class, deptId);

		Employee emp1 = dept.getEmployees().get(pos1);
		Employee emp2 = dept.getEmployees().get(pos2);

		log.log(Level.FINE, "{0}", new Object[] { dept.getEmployees() });
		dept.getEmployees().set(pos1, emp2);
		log.log(Level.FINE, "{0}", new Object[] { dept.getEmployees() });
		dept.getEmployees().set(pos2, emp1);
		log.log(Level.FINE, "{0}", new Object[] { dept.getEmployees() });
	}

	public List<Employee> findEmployees(long deptId){
		Dept dept = em.find(Dept.class, deptId);
		dept.getEmployees().size();
		log.log(Level.FINE, "{0}", new Object[] { dept.getEmployees() });
		return dept.getEmployees();
	}
}
