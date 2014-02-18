package org.nailedtothex.jpatest.set;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SetBasic implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;

	@ElementCollection
	private Set<String> set;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

}
