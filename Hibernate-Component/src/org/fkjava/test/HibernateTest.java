package org.fkjava.test;

import org.fkjava.bean.Address;
import org.fkjava.bean.Person;
import org.fkjava.bean.Phones;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 使用Junit4 进行Hibernate 操作
 *
 * @author hanfeili
 *  www.fkjava.org
 */
public class HibernateTest {
	SessionFactory  sessionFactory =null;
	Session session =null;
	Transaction tx = null;
	/**
	 * 初始化测试数据 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("------setUp---初始化测试资源-----");
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		sessionFactory = config.buildSessionFactory(sr);
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testAdd(){
		Person p = new Person();
		p.setName("tom2");
		p.setPassword(123456);
		p.setBirthday(new java.util.Date());
		//创建组件
		Address address = new Address();
		address.setHomeAddress("湖南郴州");
		address.setCompanyAddress("广东广州");
		//设置组件类到Person类
		p.setAddress(address);
	
		Phones phones = new Phones();
		phones.setHomePhone("0735-22222222");
		phones.setCompanyPhone("020-88888888");
		p.setPhones(phones);
		
		tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
	
	}
	
	

	@Test
	public void testGet() {
		Person p = (Person)session.get(Person.class, 1);
		System.out.println(p);
		System.out.println("homeAddress="+p.getAddress().getHomeAddress());
		System.out.println("homePhone="+p.getPhones().getHomePhone());
	}
	
	
	/**
	 * 释放测试数据 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("------tearDown---释放测试数据---");
		session.close();
		sessionFactory.close();
	}

}
