package org.fkjava.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
		
		 String[] array = {"A","B","C","D"};
		 p.setMy_array(array);
		 
		 ArrayList<String> list = new ArrayList();
		 list.add("广东");
		 list.add("湖南");
		 list.add("北京");
		 list.add("上海");
		
		 p.setMy_list(list);
		 
		 
		 HashMap<String,String> map = new HashMap<String,String>();
		 map.put("fkjava","www.fkjava.org");
		 map.put("facejava","www.facejava.org");
		 p.setMy_map(map);
		 
		 HashSet<String> set = new HashSet<String>();
		 set.add("帅哥");
		 set.add("大帅哥");
		 set.add("帅哥2");
		 set.add("美女");
		 p.setMy_set(set);
		 
	
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
