package org.fkjava.test;

import java.io.Serializable;
import java.util.List;

import org.fkjava.bean.Person;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
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
public class HibernateTest2 {
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
	public void testQuery2(){
		//多态 查询
		//通过父类查询出所有的子类 数据
		String hql = "from java.lang.Object";
		Query query = session.createQuery(hql);
		List<Object> objectList = query.list();
		for(Object obj : objectList){
			System.out.println(obj);
		}
	}
	
	
	@Test
	public void testQuery(){
		//离线查询
		//在拼装查询条件的时候 不需要与任何session进行关联
		DetachedCriteria query = DetachedCriteria.forClass(Person.class);
		query.add(Restrictions.in("id", new Object[]{3,6,9}));
		//当执行的时候才传递一个session进行关联
		List<Person> personList = query.getExecutableCriteria(session).list();
		
		for(Person p : personList){
			
			System.out.println(p);
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
