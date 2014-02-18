package org.nailedtothex.jpatest.set;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SetEmbeddableChild implements Serializable {
	private static final long serialVersionUID = 1L;

	private String embField1;
	private String embField2;

	public String getEmbField1() {
		return embField1;
	}

	public void setEmbField1(String embField1) {
		this.embField1 = embField1;
	}

	public String getEmbField2() {
		return embField2;
	}

	public void setEmbField2(String embField2) {
		this.embField2 = embField2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((embField1 == null) ? 0 : embField1.hashCode());
		result = prime * result + ((embField2 == null) ? 0 : embField2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetEmbeddableChild other = (SetEmbeddableChild) obj;
		if (embField1 == null) {
			if (other.embField1 != null)
				return false;
		} else if (!embField1.equals(other.embField1))
			return false;
		if (embField2 == null) {
			if (other.embField2 != null)
				return false;
		} else if (!embField2.equals(other.embField2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SetEmbeddableChild [embField1=" + embField1 + ", embField2=" + embField2 + "]";
	}

}
