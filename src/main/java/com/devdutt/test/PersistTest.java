package com.devdutt.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.devdutt.entity.Employee;

public class PersistTest {

	private static final Logger logger = Logger.getLogger(PersistTest.class);
	static {
		try {
			PropertyConfigurator.configure("src/main/java/com/devdutt/commons/log4j.properties");
		} // try
		catch (Exception e) {
			System.out.println("Logging is not configured..");
		} // catch
	}// static

	public static void main(String[] args) {
		logger.info("SaveTest.main() started");
		Configuration cfg = null;
		SessionFactory factory = null;
		Session ses = null;
		Employee employee = null;
		Transaction tx = null;
		boolean flag = false;

		// create Configuration class
		cfg = new Configuration();
		logger.info("Configuration class object is created");

		// read and locate configuration.xml file
		cfg.configure("com/devdutt/cfgs/hibernate.cfg.xml");
		logger.info("read and locate hibernate.cfg.xml file");

		// create Session Factory object
		factory = cfg.buildSessionFactory();
		logger.info("Session Factory obj is created by calling ses.buildSessionFactory()");

		// create Session object
		ses = factory.openSession();
		logger.info("Session object is created by calling factory.openSession()");

		// create domain class object having data
		employee = new Employee();
		employee.setEid(114);
		employee.setEname("Mayur");
		employee.setEmail("mayur@gmail.com");
		employee.setSalary(34000.00);
		try {
			tx = ses.beginTransaction();
			logger.debug("create the transaction object by calling beginTransaction() method");
			ses.persist(employee);
			tx.commit();
			System.out.println("Record inserted successfully...");
			logger.debug("Record inserted successfully");
		} // try
		catch (HibernateException he) {
			tx.rollback();
			he.printStackTrace();
			System.out.println("Record not inserted...");
			logger.debug("Record not inserted successfully. " + he.getMessage());
		} // catch
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			System.out.println("Record not inserted...");
			logger.debug("Record not inserted successfully. " + e.getMessage());
		} // catch
		logger.info("SaveTest.main() ended");
	}
}
