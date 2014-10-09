package org.fkjava.test;

import java.util.HashSet;
import java.util.Iterator;
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
		p.setName("p");
		p.setPassword(654321);
		p.setBirthday(new java.util.Date());
		
		Person p2 = new Person();
		p2.setName("p2");
		p2.setPassword(123456);
		p2.setBirthday(new java.util.Date());
		
		Address address = new Address();
		address.setCodes("432000");
		address.setDes("湖南郴州");
		
		
		Address address2 = new Address();
		address2.setCodes("5000000");
		address2.setDes("广东广州");
		
		HashSet<Address> a_Set = new HashSet<Address>();
		a_Set.add(address);
		a_Set.add(address2);
		
		HashSet<Person> p_Set = new HashSet<Person>();
		p_Set.add(p);
		p_Set.add(p2);
		
		//将多个地址与人关联
		p.setAddressSet(a_Set);
		p2.setAddressSet(a_Set);
		//将多人与某个地址关联
		address.setPersonSet(p_Set);
		address2.setPersonSet(p_Set);
		
		tx = session.beginTransaction();

		session.persist(p);
		session.persist(p2);
		
		session.persist(address);
		session.persist(address2);
		
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
		Address addrss = (Address)session.get(Address.class, 2);
		System.out.println(addrss);
		Set<Person> personSet = addrss.getPersonSet();
		Iterator<Person> it = personSet.iterator();
		System.out.println(addrss.getDes()+"-上 有-"+personSet.size()+" -个人住");
		while(it.hasNext()){
			Person p = it.next();
			System.out.println(p);
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
