package org.fkjava.test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	@Test
	public void list3(){
		
		Query query = session.createQuery("from Person p");
		
		Iterator<Person> it = query.iterate();
		for(int i=0;it.hasNext();i++){
			
			System.out.println(i+"--"+it.next());
			
		}
				
	}
	
	@Test
	public void list(){
		
		Query query = session.createQuery("from Person p");
		
		List<Person> personList = query.list();
		for(int i=0;i<personList.size();i++){
			
			System.out.println(i+"--"+personList.get(i));
			
		}
				
	}
	
	/**
	 * batch-size是一种在进行关联关系获取数据的时候的优化策略
	 * 它可以在我们进行 关联关系获取的时候 进行类似  分页 一样的 分配获取数据的操作
	 * 效率比较高  
	 * 例如：如果我们有10 Person数据，然后每个Person都有多个Address 的关联关系
	 * 那么我们在<set name="addressSet" batch-size="3" ...>设置每次批量获取关联关系的Addrss数据的操作是3条
	 * 执行sql 是 3,3,3,1
	 */
	@Test
	public void list2(){
		Query query = session.createQuery("select p from Person p");
	
		List<Person> personList = query.list();
	
	for(int i = 0;i<personList.size();i++){
		Person p = personList.get(i);
		System.out.println(p);
		
		Set<Address> addressSet = p.getAddressSet();
		Iterator<Address> it = addressSet.iterator();
		int index = 0;
		while(it.hasNext()){
			Address a = it.next();
			System.out.println((++index)+"---"+a);
			System.out.println("------------------------------");
		}			
	  }
	}
	
	
	@Test
	public void load(){
		
		Person p = (Person)session.load(Person.class, 1);
		
		System.out.println(p);
		Set<Address> addressSet = p.getAddressSet();
		System.out.println(p.getName()+"----"+addressSet.size());
		
		Iterator<Address> it = addressSet.iterator();
		int index = 0;
		while(it.hasNext()){
			Address a = it.next();
			System.out.println((++index)+"---"+a);
		}
	}
	
	
	
	@Test
	public void get(){
		
		Person p = (Person)session.get(Person.class, 1);
		
		System.out.println(p);
		Set<Address> addressSet = p.getAddressSet();
		System.out.println(p.getName()+"----"+addressSet.size());
		
		Iterator<Address> it = addressSet.iterator();
		int index = 0;
		while(it.hasNext()){
			Address a = it.next();
			System.out.println((++index)+"---"+a);
		}
	}
	
	/**
	 * 插入单对象数据
	 */
	@Test
	public void testAdd1(){
		Transaction tx = session.beginTransaction();
		for(int i=0;i<10;i++){
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
	 * 测试数据插入
	 */
	@Test
	public void testAdd2(){
		Person p = new Person();
		p.setName("tom1");
		p.setPassword(123456);
		p.setBirthday(new java.util.Date());
		
		
		HashSet<Address> set = new HashSet<Address>();
		for(int i =0;i<10;i++){
			//循环创建10个地址
			Address address = new Address();
			address.setCodes("432000_"+i);
			address.setDes("湖南郴州_"+i);
			//将每个地址与  一个人 对应
			address.setPerson(p);
			//将每个地址保存到set集合中
			set.add(address);
		}
		//将人和地址进行关联
		p.setAddressSet(set);
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
