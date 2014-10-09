package org.fkjava.bean;

import java.util.Date;

/** 
 * 
 * 子类数据保存子类表中，父类数据保存在父类表中
 * 但是需要注意的是：
 * 子类保存了继承的属性数据和自己扩展的属性数据
 * 
 * 单独才表上看不出 任何 关联关系
 * 
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
