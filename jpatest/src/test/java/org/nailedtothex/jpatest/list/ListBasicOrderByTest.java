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
public class ListBasicOrderByTest {

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> a = ShrinkWrap.create(WebArchive.class, "test.war").addPackage(ListBasicOrderBy.class.getPackage())
				.addPackage(ListBasicOrderByTest.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return a;
	}

	@Inject
	ListBasicOrderByTestDataManipulator testDataManipulator;

	@Test
	@UsingDataSet({ "list/listbasic-orderby/create/listbasicorderby.yml",
			"list/listbasic-orderby/create/listbasicorderby_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-orderby/create/expected-listbasicorderby.yml")
	public void create1() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "list/listbasic-orderby/create/listbasicorderby.yml",
			"list/listbasic-orderby/create/listbasicorderby_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-orderby/create/expected-listbasicorderby_list.yml", orderBy = {
			"listbasicorderby_id", "list" })
	public void create2() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "list/listbasic-orderby/find/listbasicorderby.yml",
			"list/listbasic-orderby/find/listbasicorderby_list.yml" })
	public void find() throws Exception {
		List<String> expected = Arrays.asList(new String[] { "hige", "hoge", "hoge" });
		List<String> actual = testDataManipulator.find();
		assertEquals(expected, actual);
	}

	@Test
	@UsingDataSet({ "list/listbasic-orderby/add/listbasicorderby.yml",
			"list/listbasic-orderby/add/listbasicorderby_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-orderby/add/expected-listbasicorderby_list.yml", orderBy = {
			"listbasicorderby_id", "list" })
	public void add() throws Exception {
		boolean actual = testDataManipulator.add("hige");
		assertThat(actual, is(true));
	}

	@Test
	@UsingDataSet({ "list/listbasic-orderby/remove/listbasicorderby.yml",
			"list/listbasic-orderby/remove/listbasicorderby_list.yml" })
	@ShouldMatchDataSet(value = "list/listbasic-orderby/remove/expected-listbasicorderby_list.yml", orderBy = {
			"listbasicorderby_id", "list" })
	public void remove() throws Exception {
		String actual = testDataManipulator.remove(2);
		assertThat(actual, is("hoge"));
	}
}