package org.fkjava.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.fkjava.bean.Person;
import org.hibernate.type.IntegerType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.IntegerType;
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
	public void testexecuteUpdate(){
		Transaction tx = session.beginTransaction();
		//在hibernate 3以后 提供了一个高效的更新和删除操作的方法executeUpdate();
		//更新
		//Query query = session.createQuery("update from Person p set p.name = :name where p.id=:id");
		//删除
		Query query = session.createQuery("delete from Person p where p.id=:id");
		query.setInteger("id", 9);
		//返回删除或更新影响的 行数
		int count = query.executeUpdate();
		
		System.out.println(count);
		tx.commit();
		
	}
	
	
	
	@Test
	public void testQuery19(){
		//group by 测试
		String hql = "select p.password, count(p.id) from Person p group by p.password";  
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		System.out.println("--size---"+list.size());
		for(Object[] o : list){
			
			System.out.println(o[0]+"----"+o[1]);
		}
	}
	
	@Test
	public void testQuery18(){
		//排序
		String hql = "from Person p  order by p.id desc";
		Query query = session.createQuery(hql);
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	
	@Test
	public void testQuery17(){
		//hibernate推荐将 HQL 定义到一个专门的配置文件中
		//然后通过getNamedQuery方法获得参数指定查询语句
		Query query = session.getNamedQuery("seletePerson3");
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery16(){
		//hibernate推荐将 HQL 定义到一个专门的配置文件中
		//然后通过getNamedQuery方法获得参数指定查询语句
		Query query = session.getNamedQuery("seletePerson2");
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery15(){
		//hibernate推荐将 HQL 定义到一个专门的配置文件中
		//然后通过getNamedQuery方法获得参数指定查询语句
		Query query = session.getNamedQuery("seletePerson");
		query.setString("n", "%_8");
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	
	@Test
	public void testQuery14(){
		//String hql = "from Person p where p.name like '%_8'";
		//hibernate 支持 模糊查询
		String hql = "from Person p where p.name like :n";
		Query query = session.createQuery(hql);
		//query.setString("n", "'%_8'");//错误：不能使用 单引号
		query.setString("n", "%_8");
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery13(){
		String hql = "from Person p where p.id in(:ids)";
		Query query = session.createQuery(hql);
		//query.setParameterList("ids", new Object[]{3,9,6});
		
		List idList = new ArrayList();
		idList.add(3);
		idList.add(6);
		idList.add(9);
		query.setParameterList("ids", idList);
		
		//query.setParameterList("ids", idList,IntegerType);
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery12(){
		String hql = "from Person p where p.id in(3,6,9)";
		Query query = session.createQuery(hql);
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery11(){
		//hibernate提供了 命名参数绑定 方式 进行参数赋值
		String hql = "from Person p where p.name = ?1 and p.password = ?2";
		Query query = session.createQuery(hql);
		//在执行list()之前通过 query.setXxx();进行参数绑定
		//参数1：表示命名参数占位符的名称
		//参数2：表示实际的参数值
		query.setString("1", "admin_8");
		query.setInteger("8",131);
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	
	@Test
	public void testQuery10(){
		//hibernate提供了 命名参数绑定 方式 进行参数赋值
		String hql = "from Person p where p.name =:n";
		Query query = session.createQuery(hql);
		//在执行list()之前通过 query.setXxx();进行参数绑定
		//参数1：表示命名参数占位符的名称
		//参数2：表示实际的参数值
		query.setString("n", "admin_8");
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	
	@Test
	public void testQuery9(){
		//可以同类型JDBC中 ？ 占位符 进行条件参数 绑定
		String hql = "from Person p where p.name =?";
		Query query = session.createQuery(hql);
		//在执行list()之前通过 query.setXxx();进行参数绑定
		//参数1：表示占位符的位置（hibernate中占位符的位置是从  0 开始的，JDBC是从 1 开始）
		//参数2：表示实际的参数值
		query.setString(0, "admin_8");
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	
	@Test
	public void testQuery8(){
		//String hql = "from Person p where p.id<5";
		//如果传递是参数是字符串需要使用  单引号
		String hql = "from Person p where p.name = 'admin_8'";
		Query query = session.createQuery(hql);
		List<Person> list = query.list();
		System.out.println("--size---"+list.size());
		for(Person p : list){
			System.out.println(p);
		}
	}
	
	
	
	/**
	 * iterate()方法
	 * iterate方法  延迟  查询 
	 * [n+1] 查询
	 * 1表示：先会发起一条语句去查询复合条件的所有id值 
	 * n表示：根据查询到的id获得对应的记录
	 */
	@Test
	public void testIterate(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		Iterator<Person> it = query.iterate();
		while(it.hasNext()){
			Person p = it.next();
			System.out.println("-------iterate---------");
			System.out.println(p);
		}
	}
	/**
	 * list()方法
	 * list方法是立即查询，返回查询目标对象
	 */
	@Test
	public void testList(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		List<Person> personList = query.list();
		for(Person p :personList){
			System.out.println("--------list--------");
			System.out.println(p);
		}
	}

	
	

	/**
	 * 分页
	 */
	@Test
	public void testQuery7(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		query.setMaxResults(10);//设置分页返回的数据条数
		query.setFirstResult(0);//表示才什么位置开始获取数据，注意会忽略0，从1开始。
		List<Person> personList = query.list();
		for(Person p :personList){
			System.out.println(p);
		}
	}

	
	
	@Test
	public void testQuery6(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		//如果数据只要唯一的一条，可以使用uniqueResult()方法进行操作
		//但是如果有超过 1 条数据，将报异常
		Person person =(Person)query.uniqueResult();
		System.out.println(person);
	}
	
	@Test
	public void testQuery5(){
		String hql = "from Person p";
		Query query = session.createQuery(hql);
		//获得查询结果集中的第一条记录（其实就是List.get(0)操作）
		Person person =(Person)query.list().get(0);		
		System.out.println(person);
	}
	
	
	@Test
	public void testQuery4(){
		//当查询需要返回的是一个对象时候可以在 hql中使用new Person(写需要获得的属性name)
		//但是需要注意：在Person类中一定要有一个与hql 中写的new Person(Type...) 对应的构造器
		String hql = "select new Person(p.id, p.name) from Person p";
		Query query = session.createQuery(hql);
		List<Person> personList = query.list();
		for(Person p : personList){
			System.out.println(p);
		}
	}
	
	@Test
	public void testQuery3(){
		String hql = "select p.id, p.name from Person p";
		Query query = session.createQuery(hql);
		//如果查询返回的属性是多个属性，那么接收的时候list中的数据类型就是Object[]数据类型
		//obj[0]  = p.id 
		//obj[1]  = p.name 
		List<Object[]> persons = query.list();
		for(Object[] objs : persons){
			System.out.println("id="+objs[0]+"----name"+objs[1]);
		}
	}
	
	@Test
	public void testQuery2(){
		String hql = "select p.name from Person p";
		
		Query query = session.createQuery(hql);
		//如果查询返回的属性是单属性，那么接收的时候list中的数据类型就是属性的数据类型
		List<String> personNames = query.list();
		
		for(String name : personNames){
			System.out.println(name);
		}
	}
	
	@Test
	public void testQuery(){
		//sql: select * from t_person;
		//Hql语句是基于 面向对象 的方式进行操作的
		//所以在写的时候 都是 对象 和 属性
		String hql = "from Person";
		Query query = session.createQuery(hql);
		/*List list = query.list();
		for(Object o : list){
			Person p = (Person)o;
			System.out.println(p);
		}*/
		
		List<Person> personList = query.list();
		for(Person p : personList){
			System.out.println(p);
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
