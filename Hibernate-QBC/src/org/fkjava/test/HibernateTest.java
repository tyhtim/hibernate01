package org.fkjava.test;

import java.io.Serializable;
import java.util.List;

import org.fkjava.bean.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
	public void testQuery4(){
		//通过给定的持久化类进行对应的查询
		Criteria query = session.createCriteria(Person.class);
		
		Person p = new Person();
		p.setName("admin_8");
		//p.setPassword(131);
		//使用 样例查询  hibernate会将你传递给他的对象中设置了值的属性 拿出来拼装SQL语句的where条件进行查询
		//但是需要注意的是：如果一个对象的属性是 基本类型 ，那么基本类型是会有 默认值的，
		//所以通常建议使用对应的封装类, 来设置属性类型
		query.add(Example.create(p));

		List<Person> personList = query.list();
		
		for(int i =0;i<personList.size();i++){
			System.out.println(personList.get(i));
		}
	}
	
	
	
	@Test
	public void testQuery3(){
		//通过给定的持久化类进行对应的查询
		Criteria query = session.createCriteria(Person.class);
		//绑定查询参数 
		//query.add(Property.forName("id").eq(6));
		query.add(Property.forName("name").like("%_8"));

		List<Person> personList = query.list();
		
		for(int i =0;i<personList.size();i++){
			System.out.println(personList.get(i));
		}
	}
	
	@Test
	public void testQuery2(){
		//通过给定的持久化类进行对应的查询
		Criteria query = session.createCriteria(Person.class);
		//绑定查询参数 
		//query.add(Restrictions.eq("id", 6));
		//query.add(Restrictions.between("id", 3, 6));
		query.add(Restrictions.like("name", "%_8"));
		List<Person> personList = query.list();
		
		for(int i =0;i<personList.size();i++){
			System.out.println(personList.get(i));
		}
	}
	
	
	
	@Test
	public void testQuery(){
		//通过给定的持久化类进行对应的查询
		Criteria query = session.createCriteria(Person.class);
		query.setFirstResult(2).setMaxResults(5);
		
		List<Person> personList = query.list();
		
		for(int i =0;i<personList.size();i++){
			System.out.println(personList.get(i));
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
