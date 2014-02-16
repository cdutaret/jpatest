package org.nailedtothex.jpatest.ordercolumn;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class OrderColumnTest {

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> a = ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Employee.class.getPackage())
				.addPackage(OrderColumnTest.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsResource("META-INF/orm.xml", "META-INF/orm.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return a;
	}

	@PersistenceContext
	EntityManager em;
	@Inject
	OrderColumnTestDataManipulator testDataManipulator;

	@Test
	@ShouldMatchDataSet(value = { "ordercolumn/create/expected-dept.yml", "ordercolumn/create/expected-employee.yml" }, orderBy = "id", excludeColumns = "id")
	public void create() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "ordercolumn/add/dept.yml", "ordercolumn/add/employee.yml" })
	@ShouldMatchDataSet(value = { "ordercolumn/add/expected-employee.yml" }, orderBy = "pos", excludeColumns = "id")
	public void add() throws Exception {
		testDataManipulator.add(101l);
	}

	@Test
	@UsingDataSet({ "ordercolumn/add/dept.yml", "ordercolumn/add/employee.yml" })
	@ShouldMatchDataSet(value = { "ordercolumn/add/expected-employee.yml" }, orderBy = "pos", excludeColumns = "id")
	public void addRevised() throws Exception {
		testDataManipulator.addRevised(101l);
	}

	@Test
	@UsingDataSet({ "ordercolumn/remove/dept.yml", "ordercolumn/remove/employee.yml" })
	@ShouldMatchDataSet(value = { "ordercolumn/remove/expected-employee.yml" }, orderBy = "pos", excludeColumns = "id")
	public void remove() throws Exception {
		testDataManipulator.remove(101l, 1);
	}

	@Test
	@UsingDataSet({ "ordercolumn/swap/dept.yml", "ordercolumn/swap/employee.yml" })
	@ShouldMatchDataSet(value = { "ordercolumn/swap/expected-employee.yml" }, orderBy = "pos", excludeColumns = "id")
	public void swap() throws Exception {
		testDataManipulator.swap(101l, 1, 2);
	}

	@Test
	@UsingDataSet({ "ordercolumn/unordered/dept.yml", "ordercolumn/unordered/employee.yml" })
	public void unordered() throws Exception {
		List<Employee> employees = testDataManipulator.findEmployees(101l);
		Assert.assertEquals(0, employees.get(0).getPos().longValue());
		Assert.assertEquals(1, employees.get(1).getPos().longValue());
		Assert.assertEquals(2, employees.get(2).getPos().longValue());
	}
}