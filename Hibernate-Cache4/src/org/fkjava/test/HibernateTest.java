package org.fkjava.test;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.fkjava.bean.Person;
import org.hibernate.Cache;
import org.hibernate.CacheMode;
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
	
	/**
	 * iterate()方法
	 * iterate方法是 延迟查询，返回查询代理对象，当真正使用的时候才发起SQL语句查询
	 * 
	 * iterate()方法   支持  session缓存（一级缓存）
	 * iterate()方法   支持   二级缓存
	 * iterate()方法   不支持  查询缓存
	 */
	@Test
	public void testIterate2(){
		String hql = "from Person p where p.id in(:ids)";
		Query query = session.createQuery(hql);
		query.setCacheable(true);
		query.setParameterList("ids", new Object[]{3,6,9});
		Iterator<Person> it = query.iterate();
		while(it.hasNext()){
			Person p = it.next();
			System.out.println(p);
		}
		//需要清除一级缓存，才能够看到正确的测试效果
		session.clear();
		
		System.out.println("-----同一个Session-再执行相同的-iterate---------");
		Query query2 = session.createQuery(hql);
		query.setCacheable(true);
		query2.setParameterList("ids", new Object[]{3,6,9});
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
	 * list()方法   支持   二级缓存
	 * list()方法   支持   查询缓存
	 */
	@Test
	public void testList2(){
		String hql = "from Person p where p.id in(:ids)";
		Query query = session.createQuery(hql);
		//设置使用查询缓存缓存本次 hql语句
		query.setCacheable(true);
		query.setParameterList("ids", new Object[]{3,6,9});
		
		List<Person> personList = query.list();
		for(Person p :personList){
			System.out.println(p);
		}
		
		System.out.println("-----同一个Session-再执行相同的-list---------");
		
		Query query2 = session.createQuery(hql);
		query2.setCacheable(true);
		query2.setParameterList("ids", new Object[]{3,6,8});
		List<Person> personList2 = query2.list();
		for(Person p :personList2){
			System.out.println("----"+p);
		}
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