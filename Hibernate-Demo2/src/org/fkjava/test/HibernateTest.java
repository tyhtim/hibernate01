package org.fkjava.test;

import org.fkjava.bean.Person;
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
		tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
	
	}
	@Test
	public void testUpdate(){
		//Person p =(Person)session.get(Person.class, 1);
		Person p = new Person();
		p.setId(1);
		p.setName("99993");
		p.setPassword(8888);
		tx = session.beginTransaction();
		session.update(p);
		tx.commit();	
	}
	

	@Test
	public void testGet() {
		Person p = (Person)session.get(Person.class, 2);
		System.out.println(p);
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
