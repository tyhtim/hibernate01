package org.fkjava.test;

import org.fkjava.bean.Person;
import org.fkjava.bean.Student;
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
		p.setName("tom");
		p.setPassword(123456);
		p.setBirthday(new java.util.Date());

		tx = session.beginTransaction();
	
		session.persist(p);
		tx.commit();
	}
	
	@Test
	public void testAdd2(){
		Student stu = new Student();
		stu.setName("stu");
		stu.setPassword(123456);
		stu.setBirthday(new java.util.Date());
		stu.setClassId(1206);
		stu.setClassName("J1206");
		stu.setCourse(90);
		tx = session.beginTransaction();
	
		session.persist(stu);
		tx.commit();
	}
	
	@Test
	public void testGet(){
		
		Person p = (Person)session.get(Person.class, 1);
		
		System.out.println(p);
		
	}
	@Test
	public void testGet2(){
		
		Student stu = (Student)session.get(Student.class, 2);
		
		System.out.println(stu);
		
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
