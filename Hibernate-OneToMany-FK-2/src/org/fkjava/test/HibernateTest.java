package org.fkjava.test;

import java.util.HashSet;
import java.util.Set;

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
		p.setName("tom1");
		p.setPassword(123456);
		p.setBirthday(new java.util.Date());
		
		Address address = new Address();
		address.setCodes("432000");
		address.setDes("湖南郴州1");
		
		
		Address address2 = new Address();
		address2.setCodes("5000000");
		address2.setDes("广东广州1");
		
		HashSet<Address> set = new HashSet<Address>();
		set.add(address);
		set.add(address2);
		//将多个地址与人关联
		p.setAddressSet(set);
		//将人与某个地址关联
		address.setPerson(p);
		
		address2.setPerson(p);
		
		tx = session.beginTransaction();
		
		
		
		session.save(p);
		
		session.save(address);
		
		session.save(address2);

		tx.commit();
	}
	
	@Test
	public void testGet() {
		//通过人获得地址
		Person p = (Person)session.get(Person.class, 2);
		System.out.println(p);
		Set<Address> addressSet = p.getAddressSet();
		Address[] as = new Address[addressSet.size()];
		addressSet.toArray(as);
		System.out.println(p.getName()+"-有-"+addressSet.size()+" -个地址");
		for(Address a : as){
			System.out.println(a);	
		}
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
