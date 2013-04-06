package com.infrastructure.log;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * Initialises Log4J
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0 
 */
public class Log4j extends HttpServlet {

	private static final long serialVersionUID = 9186865849954481356L;

	public void init(ServletConfig config) throws ServletException {
		String log4jLocation = config
				.getInitParameter("log4j-properties-location");
		if (log4jLocation == null) {
			System.err.println("No log4j-properties-location init param. "
					+ "Initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			ServletContext sc = config.getServletContext();
			String log4jProp = sc.getRealPath("/") + log4jLocation;
			if (new File(log4jProp).exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println(log4jProp + " not found. "
						+ "Initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}
}
