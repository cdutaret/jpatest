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
public class ListEmbeddableTestDataManipulator {

	private static final Logger log = Logger.getLogger(ListEmbeddableTestDataManipulator.class.getName());

	@PersistenceContext
	EntityManager em;

	protected List<ListEmbeddableChild> createChildren(){
		ListEmbeddableChild child1 = new ListEmbeddableChild();
		ListEmbeddableChild child2 = new ListEmbeddableChild();
		ListEmbeddableChild child3 = new ListEmbeddableChild();

		child1.setEmbField1("child1field1");
		child1.setEmbField2("child1field2");

		child2.setEmbField1("child2field1");
		child2.setEmbField2("child2field2");

		child3.setEmbField1("child3field1");
		child3.setEmbField2("child3field2");

		List<ListEmbeddableChild> childs = new ArrayList<>();
		childs.add(child1);
		childs.add(child2);
		childs.add(child3);
		
		return childs;
	}
	
	@Transactional
	public void create() {
		ListEmbeddableParent parent = new ListEmbeddableParent();
		parent.setId(1l);

		parent.setListEmbeddableChilds(createChildren());

		em.persist(parent);
	}

	public List<ListEmbeddableChild> find() {
		ListEmbeddableParent parent = em.find(ListEmbeddableParent.class, 1l);
		List<ListEmbeddableChild> list = parent.getListEmbeddableChilds();
		log.log(Level.FINE, "find(): {0}", new Object[] { list });
		return list;
	}
	
	@Transactional
	public void add(int index, String embField1, String embField2){
		ListEmbeddableParent parent = em.find(ListEmbeddableParent.class, 1l);
		
		ListEmbeddableChild child = new ListEmbeddableChild();
		child.setEmbField1(embField1);
		child.setEmbField2(embField2);
		
		parent.getListEmbeddableChilds().add(index, child);
	}
	
	@Transactional
	public ListEmbeddableChild remove(int index){
		ListEmbeddableParent parent = em.find(ListEmbeddableParent.class, 1l);
		return parent.getListEmbeddableChilds().remove(index);
	}

	@Transactional
	public void update(int index, String embField1, String embField2){
		ListEmbeddableParent parent = em.find(ListEmbeddableParent.class, 1l);
		ListEmbeddableChild child = parent.getListEmbeddableChilds().get(index);
		child.setEmbField1(embField1);
		child.setEmbField2(embField2);
	}
}
