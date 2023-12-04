package com.jsp.expense_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.expense_tracker.dao.ExpensesDao;
import com.jsp.expense_tracker.entity.Expenses;

@Controller
public class AppController {

	@RequestMapping("/")
	public String index()
	{
		
		return "index";
	}
	
	@RequestMapping("addExpenses")
	public String addExpenses()
	{
		return "addexpenses";
	}
	
	@RequestMapping("home")
	public String home()
	{
		return "home";
	}

	@RequestMapping("login")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping("registeration")
	public String registeration()
	{
		return "registeration";
	}
	
	@Autowired
	private ExpensesDao expenseDao;
	@RequestMapping("update")
	public ModelAndView update(@RequestParam("expenseId") int expenseId)
	{
		ModelAndView mv = new ModelAndView();
		Expenses expense = expenseDao.findByExpenseId(expenseId);
		mv.addObject("expense", expense);
		mv.setViewName("update");
		return mv;
	}
	
	@RequestMapping("filter")
	public String filter()
	{
		return "filter";
	}
	
	@RequestMapping("total")
	public String total()
	{
		return "total";
	}
}

