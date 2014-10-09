package org.fkjava.demo;

import org.fkjava.bean.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * hibernate入门实例
 *
 * @author hanfeili
 *  www.fkjava.org
 */
public class Demo {
	public static void main(String[] args) {
		//persist();
		
		//get();
		System.out.println("----------------------");
		//load();
		
		//update1();
		//update2();
		
		//merge();
		//merge2();
		//saveOrUpdate();
		//saveOrUpdate2();
		
		//delete();
		delete2();
		
	}
	/**
	 * 对象删除
	 */
	private static void delete2() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Person p = new Person();
		p.setId(2);
		
		session.delete(p);
	
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	
	/**
	 * 对象删除
	 */
	private static void delete() {
		Configuration config = new Configuration().configure();
		
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Person p = (Person)session.get(Person.class, 1);
		
		session.delete(p);
	
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	
	/**
	 * 更新操作
	 * saveOrUpdate 方法会根据传递的对象有没有ID来进行新增或者修改操作
	 * 有id就是修改
	 * 没有id就是新增
	 * 如果给定的id没有对应的记录将报错
	 */
	private static void saveOrUpdate2() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Person p1 = new Person();
		p1.setId(9);
		p1.setName("YYY");
		p1.setPassword(00000);
		p1.setBirthday(new java.util.Date());
		
		session.saveOrUpdate(p1);
	
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	/**
	 * 更新操作
	 */
	private static void saveOrUpdate() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Person p1 = (Person)session.get(Person.class, 1);

		p1.setName("III");
		session.saveOrUpdate(p1);
	
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	
	
	/**
	 * 更新操作
	 */
	private static void merge2() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Person p1 = new Person();
		p1.setId(1);
		p1.setName("ooo");
		
		System.out.println("merge2--修改前--"+p1);
		//传递给merge方法的p1是一个临时对象，没有跟session进行关联
		//merge方法返回的p2是一个由session持久化管理的持久化对象，所以p2的改变会在session关闭的时候同步到数据库表中
		Person p2 = (Person)session.merge(p1);
		 
		System.out.println("p1---"+p1);
		System.out.println("p2---"+p2);
		
		p2.setName("p22");
		p1.setName("p11");
		
		
		System.out.println("-1-p1---"+p1);
		System.out.println("-2-p2---"+p2);
		
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	
	/**
	 * 更新操作
	 */
	private static void merge() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Person p1 = (Person)session.get(Person.class, 1);
		System.out.println("--修改前--"+p1);
		
		p1.setName("kk2");
		
		Person p2 = (Person)session.merge(p1);
		 
		System.out.println("p1---"+p1);
		System.out.println("p2---"+p2);
		
		p2.setName("p2");
		p1.setName("p1");
		
		
		
		System.out.println("-1-p1---"+p1);
		System.out.println("-2-p2---"+p2);
		
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	
	/**
	 *  更新通过new 创建的对象
	 *  需要注意：
	 *  1:id 必须有，并且必须对应数据库表的某一条记录
	 */
	private static void update2() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Person p = new Person();
		p.setName("mm");

		p.setId(2);		
		System.out.println("--修改前--"+p);
		session.update(p);
		
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	/**
	 * 修改通过get方法获得的持久化对象
	 */
	private static void update1() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Person p = (Person)session.get(Person.class, 1);
		System.out.println("-update1-修改前--"+p);
		
		p.setName("admin1");
		//如果是一个跟session关联的持久化状态的对象，可以不需要调用update方法，也能够在session.close()前执行更新操作
		session.update(p);
		
		tx.commit();//提交的时候才执行SQL
		session.close();
		System.out.println("---------end--------");
	}
	 /**
	  * load 方式查询一条记录
	  * 延迟查询
	  * load方法返回的是一个持久化类的代理，当真正访问除id以外的属性的时候才去发起SQL语句查询id对应的记录
	  * load方法查询id对应的记录不存在的时候报 ObjectNotFoundException 异常
	  * 可以在*.hbm.xml的class配置中使用lazy 让load方法变成立即加载 (lazy=false) lazy默认true 表示延迟加载 
	  * <class name="Person" table="t_person" lazy="false">
	  */
	private static void load() {
		Configuration config = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		//根据id 查询对应的持久化类的  一条 记录
		Person p = (Person)session.load(Person.class, 1);
		//System.out.println("load--->"+p);
		System.out.println("-------load---------------");
		System.out.println("load--id->"+p.getId());
		System.out.println("load--name->"+p.getName());
		session.close();
	}
	/**
	 * get方式查询一条记录
	 * 立即查询
	 * get方法返回的是一个持久化类的实例，一执行就发起SQL语句查询id对应的记录
	 * get方法查询id对应的记录不存在的时候 返回 null 
	 */
	private static void get() {
		Configuration config = new Configuration().configure();
		//hibernate 4 通过下面的方式获得SessionFactory 更加的高效和安全
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(sr);
		
		Session session = sessionFactory.openSession();
		//根据id 查询对应的持久化类的  一条 记录
		Person p = (Person) session.get(Person.class, 1);
		
		System.out.println("get--->"+p);
		System.out.println("-------get---------------");
		System.out.println("get--->"+p.getName());
		
		session.close();
	}

	/**
	 * 新增
	 */
	private static void persist() {
		//创建Configuraction实例，并加载配置文件（hibernate.cfg.xml）
		Configuration config = new Configuration().configure();
		//通过Configuration获得SessionFactory（获得对应的需要操作的数据库的配置信息）
		//SessionFactrory是与应用程序生命周期一致，是在多个线程之间共享的
		//现在的写法是config.buildSessionFactory(); 是hibernate 4之前的写法
		SessionFactory factory = config.buildSessionFactory();
		
		//通过SessionFactory 获得session （一次数据库的交互）
		Session session = factory.openSession();
		//在使用hibernate进行 增 ，删，修 的时候需要事务
		Transaction tx = session.beginTransaction();
		//创建需要持久化的java对象
		Person person = new Person();
		person.setName("tom1");
		person.setPassword(123456);
		person.setBirthday(new java.util.Date());
		
		//使用hibernate面向对象的方式进行持久化
		//session.save(person);//会返回持久化数据对应的id值
		session.persist(person);//根据容易让人理解，但是不会返回id值
		//提交事务
		tx.commit();
		//关闭session
		session.close();
		//factory.close();
	}
}
