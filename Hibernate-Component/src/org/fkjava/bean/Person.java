package org.fkjava.bean;

import java.util.Date;

/** 
 * 引入组件类,并提供get/set方法
 * private Address address;
   private Phones phones;
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
	private Phones phones;

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

	public Phones getPhones() {
		return phones;
	}

	public void setPhones(Phones phones) {
		this.phones = phones;
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
