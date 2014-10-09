package org.fkjava.test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.fkjava.bean.Person;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
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
		//使用标准的SQL语句格式进行 查询
		//sql语句中写的是 表 和  列名
		String sql = "select p.id , p.t_name from t_person p";
		SQLQuery query = session.createSQLQuery(sql);
		//将查询出来的列数据与一个固定的Hibernate类型进行绑定
		query.addScalar("id", IntegerType.INSTANCE);
		query.addScalar("t_name",StringType.INSTANCE);
		
		List<Object[]> objs = query.list();
		
		for(Object[] o : objs){
			int id =(Integer) o[0];
			String name =(String) o[1];
			System.out.println(id+"----"+name);
		}
	}
	
	
	
	@Test
	public void testQuery3(){
		
		SQLQuery query =(SQLQuery)session.getNamedQuery("sqlQuery1");
		//将标准sql查询出来的数据与addEntity方法指定的类进行关联
		query.addEntity(Person.class);
		//返回的将是一个java对象
		List<Person> objs = query.list();
		
		for(Person p : objs){
			System.out.println(p);
		}
	}
	
	
	
	@Test
	public void testQuery2(){
		String sql = "select * from t_person p";
		SQLQuery query = session.createSQLQuery(sql);
		//将标准sql查询出来的数据与addEntity方法指定的类进行关联
		query.addEntity(Person.class);
		//返回的将是一个java对象
		List<Person> objs = query.list();
		
		for(Person p : objs){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery(){
		//使用标准的SQL语句格式进行 查询
		//sql语句中写的是 表 和  列名
		String sql = "select p.id , p.t_name from t_person p";
		SQLQuery query = session.createSQLQuery(sql);
		//返回一个 包含 select 后面指定的列的Object 数据组
		List<Object[]> objs = query.list();
		
		for(Object[] p : objs){
			System.out.println(Arrays.toString(p));
		}
	}
	
	/**
	 * 插入测试数据
	 */
	@Test
	public void testAdd(){
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
