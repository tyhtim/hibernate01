package org.fkjava.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hibernate 中 集合和数组类型数据操作
 * 
 * @author hanfeili www.fkjava.org
 */
public class Person {
	private Integer id;

	private String name;
	private int password;
	private Date birthday;

	//定义数组和其他常用集合，并提供get/set方法
	private String[] my_array;
	private List my_list;
	private Map my_map;
	private Set my_set;

	public Person() {
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password
				+ ", birthday=" + birthday + "]";
	}

	public String[] getMy_array() {
		return my_array;
	}

	public void setMy_array(String[] my_array) {
		this.my_array = my_array;
	}

	public List getMy_list() {
		return my_list;
	}

	public void setMy_list(List my_list) {
		this.my_list = my_list;
	}

	public Map getMy_map() {
		return my_map;
	}

	public void setMy_map(Map my_map) {
		this.my_map = my_map;
	}

	public Set getMy_set() {
		return my_set;
	}

	public void setMy_set(Set my_set) {
		this.my_set = my_set;
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
