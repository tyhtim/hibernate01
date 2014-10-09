package org.fkjava.test;

import org.fkjava.bean.Address;
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
		p.setName("tom");
		p.setPassword(123456);
		p.setBirthday(new java.util.Date());
		//创建组件
		Address address = new Address();
		address.setCodes("432000");
		address.setDes("湖南郴州");
		
		//将 人 与 地址 进行关联
		p.setAddress(address);
		//将 地址 与 人 进行关联
		address.setPerson(p);

		tx = session.beginTransaction();
		//持久化人和地址
		session.persist(p);
		//设置了级联操作所以可以不需要再 手动保存 address类了
		//session.persist(address);
		tx.commit();
	}
	
	@Test
	public void testGet() {
		//通过人获得地址
		Person p = (Person)session.get(Person.class, 1);
		System.out.println(p);
		System.out.println(p.getAddress());
	}
	
	@Test
	public void testGet2() {
		//通过地址获得人
		Address addrss = (Address)session.get(Address.class, 1);
		System.out.println(addrss);
		System.out.println(addrss.getPerson());
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
