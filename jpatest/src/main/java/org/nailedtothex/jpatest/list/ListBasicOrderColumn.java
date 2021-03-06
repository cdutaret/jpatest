package org.nailedtothex.jpatest.list;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderColumn;

@Entity
public class ListBasicOrderColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;

	@OrderColumn
	@ElementCollection
	private List<String> list;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

}
