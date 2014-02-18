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
import org.nailedtothex.jpatest.set.SetBasic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SetBasicTest {

	@Deployment
	public static Archive<?> createDeployment() {
		Archive<?> a = ShrinkWrap.create(WebArchive.class, "test.war").addPackage(SetBasic.class.getPackage())
				.addPackage(SetBasicTest.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return a;
	}

	@Inject
	SetBasicTestDataManipulator testDataManipulator;

	@Test
	@UsingDataSet({ "set/basic/create/setbasic.yml", "set/basic/create/setbasic_set.yml" })
	@ShouldMatchDataSet(value = {
			"set/basic/create/expected-setbasic.yml",
			"set/basic/create/expected-setbasic_set.yml" })
	public void create() throws Exception {
		testDataManipulator.create();
	}

	@Test
	@UsingDataSet({ "set/basic/find/setbasic.yml", "set/basic/find/setbasic_set.yml" })
	public void find() throws Exception {
		Set<String> actual = testDataManipulator.find();
		assertThat(actual, hasItems("hoge", "hige", "fuge"));
	}

	@Test
	@UsingDataSet({ "set/basic/add/setbasic.yml", "set/basic/add/setbasic_set.yml" })
	@ShouldMatchDataSet(value = {
			"set/basic/add/expected-setbasic_set.yml" }, orderBy = {"setbasic_id", "set"})
	public void add() throws Exception {
		boolean actual = testDataManipulator.add();
		assertThat(actual, is(true));
	}

	@Test
	@UsingDataSet({ "set/basic/remove/setbasic.yml", "set/basic/remove/setbasic_set.yml" })
	@ShouldMatchDataSet(value = {
			"set/basic/remove/expected-setbasic_set.yml" }, orderBy = {"setbasic_id", "set"})
	public void remove() throws Exception {
		boolean actual = testDataManipulator.remove();
		assertThat(actual, is(true));
	}
}