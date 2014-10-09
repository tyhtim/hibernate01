package org.fkjava.bean;

import java.util.Date;

/** 
 * 一对一  主键  双向 关联关系 配置 
 * 一个人  一个地址
 * 
 * @author hanfeili
 *  www.fkjava.org
 */
public class Person {
	private Integer id;
	private String name;
	private int password;
	private Date birthday;

	private Address address;
	//省略getting/setting
	
	public Person() {
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password
				+ ", birthday=" + birthday + "]";
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
