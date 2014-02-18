package org.nailedtothex.jpatest.list;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

@Entity
public class ListEmbeddableParent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;

	@ElementCollection
	@OrderColumn
	private List<ListEmbeddableChild> listEmbeddableChilds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ListEmbeddableChild> getListEmbeddableChilds() {
		return listEmbeddableChilds;
	}

	public void setListEmbeddableChilds(List<ListEmbeddableChild> listEmbeddableChilds) {
		this.listEmbeddableChilds = listEmbeddableChilds;
	}

}
