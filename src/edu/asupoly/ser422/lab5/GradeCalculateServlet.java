/*
 * Lab5Servlet.java
 *
 * Copyright:  2008 Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422.lab5;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class GradeCalculateServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Map<String, Double> result = new HashMap<>();
		double grade;
		String year = req.getParameter("year");
		String subject = req.getParameter("subject");

		GradeCalculator service = null;
		try {
			service = GradeCalculator.getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (service == null) {
			res.sendError(501, "Service Not Available");
		} else {
			try {
				grade = service.calculateGrade(year, subject);
			}
			catch(Exception e){
				res.sendError(500, "Server Exception occured");
				return;
			}
			result.put("grade", grade);
			res.setContentType("application/json");
			PrintWriter out = res.getWriter();
			out.println(new ObjectMapper().writeValueAsString(result));
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
