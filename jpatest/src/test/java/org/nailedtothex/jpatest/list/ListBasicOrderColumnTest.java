package org.nailedtothex.jpatest.list;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ListBasicOrderColumnTest {

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> a = ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(ListBasicOrderColumn.class.getPackage())
				.addPackage(ListBasicOrderColumnTest.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return a;
	}

	@Inject
	ListBasicOrderColumnTestDataManipulator testDataManipulator;

	@Test
	@UsingDataSet({ "list/listbasic-ordercolumn/create/listbasicordercolumn.yml",
			"list/listbasic-ordercolumn/create/listbasicordercolumn_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-ordercolumn/create/expected-listbasicordercolumn.yml")
	public void create1() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "list/listbasic-ordercolumn/create/listbasicordercolumn.yml",
			"list/listbasic-ordercolumn/create/listbasicordercolumn_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-ordercolumn/create/expected-listbasicordercolumn_list.yml", orderBy = {
			"listbasicordercolumn_id", "list_order" })
	public void create2() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "list/listbasic-ordercolumn/find/listbasicordercolumn.yml",
			"list/listbasic-ordercolumn/find/listbasicordercolumn_list.yml" })
	public void find() throws Exception {
		List<String> expected = Arrays.asList(new String[] { "hoge", "hoge", "hige" });
		List<String> actual = testDataManipulator.find();
		assertEquals(expected, actual);
	}
	
	@Test
	@UsingDataSet({ "list/listbasic-ordercolumn/add/listbasicordercolumn.yml",
			"list/listbasic-ordercolumn/add/listbasicordercolumn_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-ordercolumn/add/expected-listbasicordercolumn_list.yml", orderBy = {
			"listbasicordercolumn_id", "list_order" })
	public void add() throws Exception {
		testDataManipulator.add(1, "hige");
	}
	
	@Test
	@UsingDataSet({ "list/listbasic-ordercolumn/remove/listbasicordercolumn.yml",
			"list/listbasic-ordercolumn/remove/listbasicordercolumn_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-ordercolumn/remove/expected-listbasicordercolumn_list.yml", orderBy = {
			"listbasicordercolumn_id", "list_order" })
	public void remove() throws Exception {
		String actual = testDataManipulator.remove(1);
		assertThat(actual, is("hoge"));
	}
	
	@Test
	@UsingDataSet({ "list/listbasic-ordercolumn/swap/listbasicordercolumn.yml",
			"list/listbasic-ordercolumn/swap/listbasicordercolumn_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-ordercolumn/swap/expected-listbasicordercolumn_list.yml", orderBy = {
			"listbasicordercolumn_id", "list_order" })
	public void swap() throws Exception {
		testDataManipulator.swap(0, 2);
	}

}