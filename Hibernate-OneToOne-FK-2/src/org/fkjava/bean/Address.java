package org.fkjava.bean;

/**
 * 一对一  外键  双向 关联关系 配置 
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
	//因为是 双向 关联 ,所以需要在address类
	//中也要有person
	private Person person;
	
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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
