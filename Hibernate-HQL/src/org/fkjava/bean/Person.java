package org.fkjava.bean;

import java.util.Date;

/**
 * 普通的javabean ，需要进行持久化的类
 * 需要注意：
 *  1：通常这个类需要有一个 id ，一般建议使用封装类型
 *  2：这个类不能是final修饰的
 *  3：需要给这个类提供一个无参的构造器
 *  4：需要给所有的属性提供getting/setting方法
 *  5：如果有涉及集合数据的操作,集合类型要使用接口类型 List
 *
 * @author hanfeili
 *  www.fkjava.org
 */
public class Person {
	private Integer id;

	private String name;
	private int password;
	private Date birthday;
	
	public Person(){}
	//给 "select new Person(p.id, p.name) from Person p"; 提供
	public Person(Integer id,String name){
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password
				+ ", birthday=" + birthday +"]";
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
