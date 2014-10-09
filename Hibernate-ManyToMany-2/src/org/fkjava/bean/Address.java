package org.fkjava.bean;

import java.util.Set;

/**
 * 多对多 双向 关联关系 配置
 * 
 * @author hanfeili www.fkjava.org
 */
public class Address {
	private Integer id;
	/**
	 * 邮政编码
	 */
	private String codes;
	/**
	 * 具体地址
	 */
	private String des;

	private Set<Person> personSet;

	@Override
	public String toString() {
		return "Address [id=" + id + ", codes=" + codes + ", des=" + des + "]";
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	

	public Set<Person> getPersonSet() {
		return personSet;
	}

	public void setPersonSet(Set<Person> personSet) {
		this.personSet = personSet;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
