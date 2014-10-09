package org.fkjava.test;

import java.util.HashSet;
import java.util.List;

import org.fkjava.bean.Address;
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
	 * left outer join 左外连接 （outer 可以不用写）
	 * 查询的时候返回的是一个Object[]
	 * Objcet[0] 对应 Person实例
	 * Objcet[1] 对应  Address 实例
	 * 并Person中关联关系的引用是延迟加载的。
	 */
	@Test
	public void  testQuery(){
		String hql ="from Person p left join p.addressSet a where p.name='tom1'";
		Query query = session.createQuery(hql);
		
		List<Object[]> objs = query.list();
		
		for(Object[] os : objs){
			System.out.println(os[0]);
			System.out.println(os[1]);
		}
	}
	
	/**
	 * left outer join fetch 是 迫切 左外连接
	 * 会忽略 lazy设置，立即将需要的目标对象和目标对象关联的引用对象一并加载
	 */
	@Test
	public void  testQuery2(){
		String hql ="from Person p left outer join fetch p.addressSet a where p.name='tom1'";
		Query query = session.createQuery(hql);
		
		List<Person> objs = query.list();
		
		for(Person p : objs){
			System.out.println(p);
			
		}
	}
	
	
	
	/**
	 * 测试数据插入
	 */
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
		//使用了级联 all 所以不需要手动保存adddres实例了 
		/*session.save(address);
		
		session.save(address2);*/

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
