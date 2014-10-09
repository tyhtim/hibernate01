package org.fkjava.demo;

import java.io.Serializable;

import org.fkjava.bean.Person;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * session缓存的使用和管理 支持session缓存的方法有 get() load() iterate()
 * 
 * 
 * 
 * @author hanfeili www.fkjava.org
 */
public class Demo2 {

	public static void main(String[] args) {
		// test1();

		// test2();
		
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);

		Session session = sessionFactory.openSession();
		 
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
				System.out.println("-----------");
				session.flush();//刷新session，让缓存中的数据理解同步到表
				session.clear();//清除session中缓存的对象，释放这些对象占用的内存
			}
		}
		tx.commit();
		//tx.rollback();//回滚事务
		session.close();
	}
	

	/**
	 * 缓存的管理使用
	 */
	private static void test2() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);

		Session session = sessionFactory.openSession();

		Person p = (Person) session.get(Person.class, 3);

		System.out.println("get--1->" + p);
		System.out.println("-------get---------------");
		boolean flag = session.contains(p);// 判断session中是否有缓存参数对应的对象
		System.out.println(flag);

		session.evict(p);// 表示将参数指定的对象从session缓存中删除

		Person p2 = (Person) session.get(Person.class, 3);

		System.out.println("get--2->" + p2);

		session.close();
	}

	/*
	 * 缓存的使用
	 */
	private static void test1() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);

		Session session = sessionFactory.openSession();
		// 根据id 查询对应的持久化类的 一条 记录，查询到的对象会缓存在Session缓存中，方便下次快速从内存中获取
		Person p = (Person) session.get(Person.class, 3);

		System.out.println("get--1->" + p);
		System.out.println("-------get---------------");
		// 当我们通过Session获得过一个对象以后，如果在本Session没有关闭之前，再次获得之前获得过的数据，将直接才Session缓存中返回，
		// 而不再去查询数据库。
		Person p2 = (Person) session.get(Person.class, 3);

		System.out.println("get--2->" + p2);

		session.close();

		System.out.println("-------session.close()---------------");
		Session session2 = sessionFactory.openSession();
		// 因为不是同一个session缓存，所以需要重新查询一次数据库
		Person p3 = (Person) session2.get(Person.class, 3);
		System.out.println("get--1->" + p);

		session2.close();
	}

}
