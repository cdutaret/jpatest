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
public class ListBasicOrderColumnTestDataManipulator {

	private static final Logger log = Logger.getLogger(ListBasicOrderColumnTestDataManipulator.class.getName());

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create() {
		ListBasicOrderColumn listBasicOrderColumn = new ListBasicOrderColumn();
		listBasicOrderColumn.setId(1l);

		List<String> list = new ArrayList<>();
		list.add("hoge");
		list.add("hoge");
		list.add("hige");
		listBasicOrderColumn.setList(list);

		em.persist(listBasicOrderColumn);
	}

	public List<String> find() {
		ListBasicOrderColumn listBasicOrderColumn = em.find(ListBasicOrderColumn.class, 1l);
		log.log(Level.FINE, "find(): {0}", new Object[] { listBasicOrderColumn.getList() });
		return listBasicOrderColumn.getList();
	}

	@Transactional
	public void add(int index, String val) {
		ListBasicOrderColumn listBasicOrderColumn = em.find(ListBasicOrderColumn.class, 1l);
		listBasicOrderColumn.getList().add(index, val);
	}

	@Transactional
	public String remove(int index) {
		ListBasicOrderColumn listBasicOrderColumn = em.find(ListBasicOrderColumn.class, 1l);
		return listBasicOrderColumn.getList().remove(index);
	}

	@Transactional
	public void swap(int index1, int index2) {
		ListBasicOrderColumn listBasicOrderColumn = em.find(ListBasicOrderColumn.class, 1l);
		
		String str1 = listBasicOrderColumn.getList().get(index1);
		String str2 = listBasicOrderColumn.getList().get(index2);
		
		listBasicOrderColumn.getList().set(index1, str2);
		listBasicOrderColumn.getList().set(index2, str1);
		
		log.log(Level.FINE, "swap(): {0}", new Object[] { listBasicOrderColumn.getList() });
	}
}
