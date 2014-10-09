package org.fkjava.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.fkjava.bean.Person;
import org.hibernate.type.IntegerType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.IntegerType;
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
		//session = sessionFactory.openSession();
	}
	
	@Test
	public void testSession(){
		//在多线程环境中使用session
		//通过getCurrentSession获得的session不需要手动 关闭，如果关闭将报异常。
		//通过getCurrentSession获得的session在进行操作的时候需要开启事务的支持。
		session = sessionFactory.getCurrentSession();
		
		Transaction tx = session.beginTransaction();
		
		Person p =(Person)session.get(Person.class, 8);
		
		tx.commit();
		
		System.out.println(p);
	}
	
	
	/**
	 * 插入测试数据
	 */
	@Test
	public void testAdd(){
		Transaction tx = session.beginTransaction();
		for(int i=0;i<100;i++){
			Person p = new Person();
			p.setName("admin_"+i);
			p.setPassword(123+i);
			p.setBirthday(new java.util.Date());
			Serializable id = session.save(p);
			System.out.println(i+"-----"+id);
			//避免一次性大量的实体数据入库导致内存溢出,可以先flush()，再clear();
			if(i % 10 == 0){
				session.flush();//刷新session，让缓存中的数据理解同步到表
				session.clear();//清除session中缓存的对象，释放这些对象占用的内存
			}
		}
		tx.commit();
	
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
