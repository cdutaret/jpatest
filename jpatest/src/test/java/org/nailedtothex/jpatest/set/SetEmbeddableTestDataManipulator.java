package org.nailedtothex.jpatest.set;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class SetEmbeddableTestDataManipulator {

	private static final Logger log = Logger.getLogger(SetEmbeddableTestDataManipulator.class.getName());

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void create() {
		SetEmbeddableParent parent = new SetEmbeddableParent();

		parent.setId(1l);

		SetEmbeddableChild child1 = new SetEmbeddableChild();
		SetEmbeddableChild child2 = new SetEmbeddableChild();
		SetEmbeddableChild child3 = new SetEmbeddableChild();

		child1.setEmbField1("child1field1");
		child1.setEmbField2("child1field2");

		child2.setEmbField1("child2field1");
		child2.setEmbField2("child2field2");

		child3.setEmbField1("child3field1");
		child3.setEmbField2("child3field2");

		Set<SetEmbeddableChild> childs = new HashSet<>();
		childs.add(child1);
		childs.add(child2);
		childs.add(child3);

		parent.setSetEmbeddableChilds(childs);

		em.persist(parent);
	}

	public Set<SetEmbeddableChild> find() {
		SetEmbeddableParent parent = em.find(SetEmbeddableParent.class, 1l);
		log.log(Level.FINE, "{0}", parent.getSetEmbeddableChilds());
		return parent.getSetEmbeddableChilds();
	}

	@Transactional
	public boolean add(String embField1, String embField2) {
		SetEmbeddableParent parent = em.find(SetEmbeddableParent.class, 1l);
		SetEmbeddableChild child = new SetEmbeddableChild();
		child.setEmbField1(embField1);
		child.setEmbField2(embField2);
		return parent.getSetEmbeddableChilds().add(child);
	}

	@Transactional
	public boolean remove(String embField1, String embField2) {
		SetEmbeddableParent parent = em.find(SetEmbeddableParent.class, 1l);
		SetEmbeddableChild child = new SetEmbeddableChild();
		child.setEmbField1(embField1);
		child.setEmbField2(embField2);
		return parent.getSetEmbeddableChilds().remove(child);
	}

	@Transactional
	public void update(String embField1, String embField2, String newEmbField2) {
		SetEmbeddableParent parent = em.find(SetEmbeddableParent.class, 1l);
		for (SetEmbeddableChild child : parent.getSetEmbeddableChilds()) {
			if (Objects.equals(embField1, child.getEmbField1()) && Objects.equals(embField2, child.getEmbField2())) {
				child.setEmbField2(newEmbField2);
			}
		}
	}
}