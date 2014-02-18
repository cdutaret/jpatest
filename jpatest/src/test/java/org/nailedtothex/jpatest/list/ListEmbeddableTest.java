package org.nailedtothex.jpatest.list;

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
public class ListEmbeddableTest {

	@Inject
	ListEmbeddableTestDataManipulator testDataManipulator;

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> a = ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(ListEmbeddableParent.class.getPackage()).addPackage(ListEmbeddableTest.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return a;
	}

	@Test
	@UsingDataSet({ "list/listembeddable/create/listembeddableparent.yml",
			"list/listembeddable/create/listembeddableparent_listembeddablechilds.yml" })
	@ShouldMatchDataSet(value = "list/listembeddable/create/expected-listembeddableparent.yml")
	public void create1() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "list/listembeddable/create/listembeddableparent.yml",
			"list/listembeddable/create/listembeddableparent_listembeddablechilds.yml" })
	@ShouldMatchDataSet(value = "list/listembeddable/create/expected-listembeddableparent_listembeddablechilds.yml", orderBy = {
			"listembeddableparent_id", "listembeddablechilds_order" })
	public void create2() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "list/listembeddable/find/listembeddableparent.yml",
			"list/listembeddable/find/listembeddableparent_listembeddablechilds.yml" })
	public void find() throws Exception {
		List<ListEmbeddableChild> expected = testDataManipulator.createChildren();
		List<ListEmbeddableChild> actual = testDataManipulator.find();
		assertEquals(expected, actual);
	}

	@Test
	@UsingDataSet({ "list/listembeddable/add/listembeddableparent.yml",
			"list/listembeddable/add/listembeddableparent_listembeddablechilds.yml" })
	@ShouldMatchDataSet(value = "list/listembeddable/add/expected-listembeddableparent_listembeddablechilds.yml", orderBy = {
			"listembeddableparent_id", "listembeddablechilds_order" })
	public void add() throws Exception {
		testDataManipulator.add(2, "child4field1", "child4field2");
	}
	
	@Test
	@UsingDataSet({ "list/listembeddable/remove/listembeddableparent.yml",
			"list/listembeddable/remove/listembeddableparent_listembeddablechilds.yml" })
	@ShouldMatchDataSet(value = "list/listembeddable/remove/expected-listembeddableparent_listembeddablechilds.yml", orderBy = {
			"listembeddableparent_id", "listembeddablechilds_order" })
	public void remove() throws Exception {
		ListEmbeddableChild removed = testDataManipulator.remove(1);
		
		assertThat(removed.getEmbField1(), is("child2field1"));
		assertThat(removed.getEmbField2(), is("child2field2"));
	}

	@Test
	@UsingDataSet({ "list/listembeddable/update/listembeddableparent.yml",
			"list/listembeddable/update/listembeddableparent_listembeddablechilds.yml" })
	@ShouldMatchDataSet(value = "list/listembeddable/update/expected-listembeddableparent_listembeddablechilds.yml", orderBy = {
			"listembeddableparent_id", "listembeddablechilds_order" })
	public void update() throws Exception {
		testDataManipulator.update(1, "child2field1-updated", "child2field2-updated");
	}

}
