package org.nailedtothex.jpatest.set;

import java.util.Set;

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
import org.nailedtothex.jpatest.set.SetEmbeddableChild;
import org.nailedtothex.jpatest.set.SetEmbeddableParent;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SetEmbeddableTest {

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> a = ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(SetEmbeddableParent.class.getPackage()).addPackage(SetEmbeddableTest.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return a;
	}

	@Inject
	SetEmbeddableTestDataManipulator testDataManipulator;

	@Test
	@UsingDataSet({ "set/embeddable/create/setembeddableparent.yml",
			"set/embeddable/create/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/create/expected-setembeddableparent.yml" }, orderBy = "id")
	public void create1() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "set/embeddable/create/setembeddableparent.yml",
			"set/embeddable/create/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/create/expected-setembeddableparent_setembeddablechilds.yml" }, orderBy = {
			"setembeddableparent_id", "embfield1", "embfield2" })
	public void create2() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "set/embeddable/find/setembeddableparent.yml",
			"set/embeddable/find/setembeddableparent_setembeddablechilds.yml" })
	public void find() throws Exception {
		SetEmbeddableChild child1 = new SetEmbeddableChild();
		SetEmbeddableChild child2 = new SetEmbeddableChild();
		SetEmbeddableChild child3 = new SetEmbeddableChild();
		child1.setEmbField1("child1field1");
		child1.setEmbField2("child1field2");
		child2.setEmbField1("child2field1");
		child2.setEmbField2("child2field2");
		child3.setEmbField1("child3field1");
		child3.setEmbField2("child3field2");

		Set<SetEmbeddableChild> actual = testDataManipulator.find();
		assertThat(actual, hasItems(child1, child2, child3));
	}

	@Test
	@UsingDataSet({ "set/embeddable/add/setembeddableparent.yml",
			"set/embeddable/add/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/add/expected-setembeddableparent_setembeddablechilds.yml" }, orderBy = {
			"setembeddableparent_id", "embfield1", "embfield2" })
	public void add() throws Exception {
		boolean actual = testDataManipulator.add("child4field1", "child4field2");
		assertThat(actual, is(true));
	}

	@Test
	@UsingDataSet({ "set/embeddable/add/setembeddableparent.yml",
			"set/embeddable/add/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/add/setembeddableparent_setembeddablechilds.yml" }, orderBy = {
			"setembeddableparent_id", "embfield1", "embfield2" })
	public void addDup() throws Exception {
		boolean actual = testDataManipulator.add("child1field1", "child1field2");
		assertThat(actual, is(false));
	}

	@Test
	@UsingDataSet({ "set/embeddable/remove/setembeddableparent.yml",
			"set/embeddable/remove/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/remove/expected-setembeddableparent_setembeddablechilds.yml" }, orderBy = {
			"setembeddableparent_id", "embfield1", "embfield2" })
	public void remove() throws Exception {
		boolean actual = testDataManipulator.remove("child1field1", "child1field2");
		assertThat(actual, is(true));
	}
	
	@Test
	@UsingDataSet({ "set/embeddable/remove/setembeddableparent.yml",
			"set/embeddable/remove/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/remove/setembeddableparent_setembeddablechilds.yml" }, orderBy = {
			"setembeddableparent_id", "embfield1", "embfield2" })
	public void removeNone() throws Exception {
		boolean actual = testDataManipulator.remove("no", "no");
		assertThat(actual, is(false));
	}

	@Test
	@UsingDataSet({ "set/embeddable/update/setembeddableparent.yml",
			"set/embeddable/update/setembeddableparent_setembeddablechilds.yml" })
	@ShouldMatchDataSet(value = { "set/embeddable/update/expected-setembeddableparent_setembeddablechilds.yml" }, orderBy = {
			"setembeddableparent_id", "embfield1", "embfield2" })
	public void update() throws Exception {
		testDataManipulator.update("child2field1", "child2field2", "child2field2-updated");
	}
}