package com.jsp.expense_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.expense_tracker.dao.ExpensesDao;
import com.jsp.expense_tracker.entity.Expenses;
import com.jsp.expense_tracker.entity.User;

@Controller
public class ExpenseController {

	@Autowired
	private ExpensesDao dao;
	
	@PostMapping("addExpenses")
	public ModelAndView addExpenses (@ModelAttribute Expenses expenses, HttpServletRequest request, @RequestParam("expenseDate") String date)
	{
		ModelAndView mv = new ModelAndView();
		User user = (User)request.getSession().getAttribute("userData");
		LocalDate convertedDate  = LocalDate.parse(date);
		expenses.setDate(convertedDate);
		
		Expenses expensesFromDB = dao.addExpenses(expenses, user.getUserId());
		
		if(expensesFromDB != null)
		{
			//view viewExpense PAge
			mv.setViewName("addExpenses"); //passed argument as method mapping name using redirect keyword
			mv.addObject("msg", "Expenditure added");
			
			return mv;
		}
		//view Expense page
		
		mv.setViewName("addexpenses");
		mv.addObject("msg", "Expenditure Couldn't be added");
		return null;
	}
	
	
	@RequestMapping("viewExpenses")
	public ModelAndView viewExpenses(HttpServletRequest request)
	{
		ModelAndView mv= new ModelAndView();
		User user = (User)request.getSession().getAttribute("userData");
		
		if(user!=null)
		{
			List<Expenses> expenses = dao.viewExpenses(user.getUserId());
			mv.addObject("expenses", expenses);
			mv.setViewName("view");
			return mv;
		}
		mv.addObject("msg", "Unable to display Expenses");
		mv.setViewName("view");
		return mv;	
	}
	
	@RequestMapping("deleteExpenses")
	public ModelAndView deleteExpenses(HttpServletRequest request, @RequestParam("expenseId")int expenseId)
	{
		ModelAndView mv = new ModelAndView();
		if(expenseId != 0)
		{
			boolean check =  dao.deleteExpense(expenseId);
			if(check)
			{
				mv.setViewName("redirect:/viewExpenses");
				return mv;
			}
			System.out.println("Record couldn't delete 1");
			mv.addObject("msg", "Record couldn't delete");
			mv.setViewName("view");
			return mv;
		}
		System.out.println("Record couldn't delete 2");
		mv.addObject("msg", "Record couldn't delete");
		mv.setViewName("view");
		return mv;	
	}
	
	@PostMapping("updateExpenses")
	public ModelAndView updateExpenses(@ModelAttribute Expenses expenses, @RequestParam("expenseDate")String date)
	{
		ModelAndView mv = new ModelAndView();
		LocalDate convertedDate  = LocalDate.parse(date);
		expenses.setDate(convertedDate);
		Expenses updatedExpense = dao.updateExpenses(expenses, expenses.getExpenseId());
		if(updatedExpense!=null)
		{
			mv.setViewName("redirect:/viewExpenses");
			return mv;
		}
		mv.addObject("msg", "Expense Couldn't be updated");
		return mv;
	}
	
	@RequestMapping("filterExpenses")
	public ModelAndView filterExpenses(HttpServletRequest request, @RequestParam("amount") String amount, @RequestParam("date")String date, @RequestParam("category")String category)
	{
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute("userData");
		
		System.out.println(date);
		
		if(date!=null && category != null && amount!=null)
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filter(convertedAmount, category, convertedDate, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		else if(date==null && category == null && amount!=null)
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filterByAmount(convertedAmount, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		
		
		else if(amount==null && category == null && date!=null)
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filterByDate(convertedDate, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		
		
		else if(date==null && amount == null && category != null)
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filterByCategory(category, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		else if(category==null && amount != null && date != null )
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filterByAmountAndDate(convertedAmount, convertedDate, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		else if(date==null && category!=null && amount != null)
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filterByAmountAndCategory(convertedAmount, category, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		else if(amount==null && category != null && date != null)
		{
			LocalDate convertedDate  = LocalDate.parse(date);
			double convertedAmount = Double.parseDouble(amount);
			List expenses = dao.filterByCategoryAndDate(category, convertedDate, user);
			if(expenses!=null)
			{
				mv.addObject("expenses", expenses);
				mv.setViewName("view");
				return mv;
			}
		}
		
		
		mv.setViewName("filter");
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("totalExpenses")
	public ModelAndView totalExpenses(HttpServletRequest request,  @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate)
	{
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute("userData");
		
		LocalDate sDate = LocalDate.parse(startDate);
		LocalDate eDate = LocalDate.parse(endDate);
		
		double total = dao.totalExpenses(user, sDate, eDate);
		if(total!=0)
		{
			mv.addObject("msg", total);
			mv.addObject("endDate", eDate);
			mv.addObject("startDate", sDate);
			
		}
		else
		{
			mv.addObject("msg", "No expenses found");
		}
		mv.setViewName("total");
		return mv;
	}
	
	
}
