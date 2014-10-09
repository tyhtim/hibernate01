package org.fkjava.bean;

import java.util.Date;

/** 
 * 子类扩展的数据保存在子类表中，继承的数据保存的父类表中  配置实现（如果才ER图上看就是一个一对一关系）
 * @author hanfeili
 *  www.fkjava.org
 */
public class Person {
	private Integer id;
	private String name;
	private int password;
	private Date birthday;

	//省略getting/setting
	
	public Person() {
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password
				+ ", birthday=" + birthday + "]";
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
