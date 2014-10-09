package org.fkjava.bean;

import java.util.Date;
import java.util.Set;

/** 
 * 多对多   双向 关联关系 配置 
 * 
 * 
 * @author hanfeili
 *  www.fkjava.org
 */
public class Person {
	private Integer id;
	private String name;
	private int password;
	private Date birthday;
	
	//一个set集合保存多个地址引用
	private Set<Address> addressSet;
	
	public Person() {
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password
				+ ", birthday=" + birthday + "]";
	}

	
	

	public Set<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<Address> addressSet) {
		this.addressSet = addressSet;
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
