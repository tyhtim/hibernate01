package org.fkjava.test;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.fkjava.bean.Person;
import org.hibernate.Query;
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
 * 测试 list方法和iterate方法的区别
 *
 * @author hanfeili
 *  www.fkjava.org
 */
public class HibernateTest_Iterate_list {
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
	
	
	/**
	 * iterate()方法
	 * iterate方法  延迟  查询 
	 * [n+1] 查询
	 * 1表示：先会发起一条语句去查询复合条件的所有id值 
	 * n表示：根据查询到的id获得对应的记录
	 * 
	 * iterate()方法支持session缓存（一级缓存）
	 */
	@Test
	public void testIterate(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		Iterator<Person> it = query.iterate();
		while(it.hasNext()){
			Person p = it.next();
			System.out.println(p);
		}
		System.out.println("-----同一个Session-再执行相同的-iterate---------");
		Query query2 = session.createQuery(hql);
		Iterator<Person> it2 = query2.iterate();
		while(it2.hasNext()){
			Person pp = it2.next();
			System.out.println(pp);
		}
	}
	/**
	 * list()方法
	 * list方法是 立即查询，返回查询目标对象
	 * 
	 * list()方法   不支持  session缓存（一级缓存）
	 */
	@Test
	public void testList(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		List<Person> personList = query.list();
		for(Person p :personList){
			System.out.println(p);
		}
		
		System.out.println("-----同一个Session-再执行相同的-list---------");
		
		Query query2 = session.createQuery(hql);
		List<Person> personList2 = query2.list();
		for(Person p :personList2){
			System.out.println("----"+p);
		}
		
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
