package com.jsp.expense_tracker.dao;

import java.time.LocalDate;
import java.util.List;

import com.jsp.expense_tracker.entity.Expenses;
import com.jsp.expense_tracker.entity.User;

public interface ExpensesDao {

	Expenses addExpenses (Expenses expenses, int userId);
	
	List<Expenses> viewExpenses (int userId);
	
	Expenses updateExpenses (Expenses expenses, int expenseId);
	
	boolean deleteExpense(int expenseId);
	
	Expenses findByExpenseId(int expensesId);

	List filterByAmount(double amount, User user);
	List filterByDate(LocalDate date, User user);
	List filterByCategory(String category, User user);
	
	List filterByAmountAndDate(double amount,LocalDate date, User user);
	List filterByAmountAndCategory(double amount, String category, User user);
	List filterByCategoryAndDate(String category, LocalDate date, User user);
	
	
//	List filterByAmountAndDate(double amount, LocalDate date, User user);
	
	
	
	List filter(double amount, String category, LocalDate date, User user);
	
	double totalExpenses(User user, LocalDate startDate, LocalDate endDate);
	
}
