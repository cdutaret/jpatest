package org.nailedtothex.jpatest.list;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class ListBasicOrderByTestDataManipulator {

	private static final Logger log = Logger.getLogger(ListBasicOrderByTestDataManipulator.class.getName());

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create() {
		ListBasicOrderBy listBasicOrderBy = new ListBasicOrderBy();
		listBasicOrderBy.setId(1l);

		List<String> list = new ArrayList<>();
		list.add("hoge");
		list.add("hoge");
		list.add("hige");
		listBasicOrderBy.setList(list);

		em.persist(listBasicOrderBy);
	}

	public List<String> find() {
		ListBasicOrderBy listBasicOrderBy = em.find(ListBasicOrderBy.class, 1l);
		log.log(Level.FINE, "find(): {0}", new Object[] { listBasicOrderBy.getList() });
		return listBasicOrderBy.getList();
	}

	@Transactional
	public boolean add(String val) {
		ListBasicOrderBy listBasicOrderBy = em.find(ListBasicOrderBy.class, 1l);
		return listBasicOrderBy.getList().add(val);
	}

	@Transactional
	public String remove(int index) {
		ListBasicOrderBy listBasicOrderBy = em.find(ListBasicOrderBy.class, 1l);
		return listBasicOrderBy.getList().remove(index);
	}

}
