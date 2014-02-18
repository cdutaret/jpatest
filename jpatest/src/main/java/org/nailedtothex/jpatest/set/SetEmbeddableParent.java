package org.nailedtothex.jpatest.set;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SetEmbeddableParent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;

	@ElementCollection
	private Set<SetEmbeddableChild> setEmbeddableChilds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<SetEmbeddableChild> getSetEmbeddableChilds() {
		return setEmbeddableChilds;
	}

	public void setSetEmbeddableChilds(Set<SetEmbeddableChild> setEmbeddableChilds) {
		this.setEmbeddableChilds = setEmbeddableChilds;
	}
}
