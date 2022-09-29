package com.devdutt.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.devdutt.entity.Employee;

public class SaveTest {

	private static final Logger logger = Logger.getLogger(SaveTest.class);
	static {
		try {
			PropertyConfigurator.configure("src/main/java/com/devdutt/commons/log4j.properties");
		} catch (Exception e) {
			System.out.println("Logger is not configured");
		}

	}

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
		employee.setEid(112);
		employee.setEname("Devanshu");
		employee.setEmail("devanshu@gmail.com");
		employee.setSalary(80000.00);
		try {
			logger.info("Transaction begin by calling ses.beginTransaction()");
			tx = ses.beginTransaction();
			int idVal = (Integer) ses.save(employee);
			logger.info("Calling save(-) method");
			System.out.println("Identity Value:- " + idVal);
			flag = true;
		} // try
		catch (HibernateException he) {
			flag = false;
			he.printStackTrace();
			logger.error(he.getMessage());
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		} // catch
		finally {
			if (flag) {
				tx.commit();
				System.out.println("Object Saved Successfully");
				logger.info("Transaction is commited and object saved successfully....!");
			} else {
				tx.rollback();
				System.out.println("Object not Saved");
				logger.info("Transaction is rollback and object not saved ....!");
			} // else
		} // finally
		logger.info("SaveTest.main() ended");
	}// main
}// class
