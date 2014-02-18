package org.nailedtothex.jpatest.set;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.nailedtothex.jpatest.set.SetBasic;

@Named
public class SetBasicTestDataManipulator {

	private static final Logger log = Logger.getLogger(SetBasicTestDataManipulator.class.getName());

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create() {
		SetBasic setBasic = new SetBasic();
		setBasic.setId(1l);

		Set<String> set = new HashSet<>();
		set.add("hoge");
		set.add("hige");
		set.add("fuge");
		setBasic.setSet(set);

		em.persist(setBasic);
	}

	public Set<String> find() {
		SetBasic setBasic = em.find(SetBasic.class, 1l);
		log.log(Level.FINE, "find(): {0}", new Object[] { setBasic.getSet() });
		return setBasic.getSet();
	}

	@Transactional
	public boolean add() {
		SetBasic setBasic = em.find(SetBasic.class, 1l);
		return setBasic.getSet().add("hege");
	}
	
	@Transactional
	public boolean remove() {
		SetBasic setBasic = em.find(SetBasic.class, 1l);
		return setBasic.getSet().remove("hige");
	}

}
