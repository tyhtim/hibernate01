package org.fkjava.test;


import java.util.HashMap;

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
		p.setName("tom0");
		p.setPassword(123456);
		p.setBirthday(new java.util.Date());
		//创建一个动态组件
		HashMap<String,String> map =new HashMap<String,String>();
		map.put("key_1", "广东广州");
		map.put("key_2", "湖南郴州");
		map.put("key_3", "北京");
		map.put("key_3", "上海");
		
		p.setAttr(map);
	
		tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
	
	}
	
	

	@Test
	public void testGet() {
		Person p = (Person)session.get(Person.class, 1);
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
