package org.fkjava.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 *  动态组件配置
 *  使用一个Map来替代之前普通组件的类
 *
 * @author hanfeili
 *  www.fkjava.org
 */
public class Person {
	private Integer id;

	private String name;
	private int password;
	private Date birthday;
	//动态组件，使用一个Map
	//注意 集合声明的时候一定使用  接口 类型
	private Map attr;

	public Person() {
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password
				+ ", birthday=" + birthday + "]";
	}

	

	public Map getAttr() {
		return attr;
	}

	public void setAttr(Map attr) {
		this.attr = attr;
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
