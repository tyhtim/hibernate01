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
 * Hibernate的类型介绍
 * 
 * 
 * @author hanfeili www.fkjava.org
 */
public class Demo3 {

	public static void main(String[] args) {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);

		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();

		Person p = new Person();
		p.setName("admin_");
		p.setPassword(123);
		p.setBirthday(new java.util.Date());
		Serializable id = session.save(p);
		System.out.println("-id----"+id);
		tx.commit();
		// tx.rollback();//回滚事务
		session.close();
	}

	
}
