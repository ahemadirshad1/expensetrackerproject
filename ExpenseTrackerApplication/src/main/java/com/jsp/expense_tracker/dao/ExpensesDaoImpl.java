package com.jsp.expense_tracker.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.jsp.expense_tracker.entity.Expenses;
import com.jsp.expense_tracker.entity.User;
import com.jsp.expense_tracker.repository.ExpensesRepository;
import com.jsp.expense_tracker.repository.UserRepository;

@Component
public class ExpensesDaoImpl implements ExpensesDao{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExpensesRepository expensesRepository;

	@Override
	public Expenses addExpenses(Expenses expenses, int userId) {
		User user = userRepository.findById(userId).orElse(null);
		if(user!=null)
		{
			expenses.setUserInfo(user);
			return expensesRepository.save(expenses);
		}
		return null;
	}

	@Override
	public List<Expenses> viewExpenses(int userId) {
		User user = userRepository.findById(userId).orElse(null);

		if(user!=null)
		{
			return user.getExpenses();
		}
		return null;
	}

	@Override
	public Expenses updateExpenses(Expenses expenses, int expenseId) {
		Expenses expenseFromDb = expensesRepository.findById(expenseId).orElse(null);
		if(expenseFromDb != null)
		{
			expenseFromDb.setAmount(expenses.getAmount());
			expenseFromDb.setDate(expenses.getDate());
			expenseFromDb.setDescription(expenses.getDescription());
			expenseFromDb.setExpenseCategory(expenses.getExpenseCategory());
			
			return expensesRepository.save(expenseFromDb);	
		}
		return null;
	}

	@Override
	public boolean deleteExpense(int expenseId) {
			try 
			{	
				expensesRepository.deleteById(expenseId);
				return true;
			}
			catch(EmptyResultDataAccessException e)
			{
				e.printStackTrace();
			}
			return false;
	}

	@Override
	public Expenses findByExpenseId(int expensesId) {
		return expensesRepository.findById(expensesId).orElse(null);
	}
	
	@Override
	public List filterByAmount(double amount, User user)
	{
			List<Expenses> expenses  =  expensesRepository.findByAmountAndUserInfo(amount, user);
			if(expenses!=null)
			{
				return expenses;
			}
			return null;
	}
	@Override
	public List filterByDate(LocalDate date, User user)
	{
		List<Expenses> expenses  =  expensesRepository.findByDateAndUserInfo(date, user);
		if(expenses!=null)
		{
			return expenses;
		}
		return null;
	}
	
	@Override
	public List filterByCategory(String category, User user)
	{
		List<Expenses> expenses  =  expensesRepository.findByExpenseCategoryAndUserInfo(category, user);
		if(expenses!=null)
		{
			return expenses;
		}
		return null;
	}
	
	@Override
	public List filterByAmountAndDate(double amount,LocalDate date, User user)
	{
		List<Expenses> expenses  =  expensesRepository.findByAmountAndDateAndUserInfo(amount, date, user);
		if(expenses!=null)
		{
			return expenses;
		}
		return null;
	}
	
	@Override
	public List filterByAmountAndCategory(double amount, String category, User user)
	{
		List<Expenses> expenses  =  expensesRepository.findByAmountAndExpenseCategoryAndUserInfo(amount, category, user);
		if(expenses!=null)
		{
			return expenses;
		}
		return null;
	}
	
	@Override
	public List filterByCategoryAndDate(String category, LocalDate date, User user)
	{
		List<Expenses> expenses  =  expensesRepository.findByExpenseCategoryAndDateAndUserInfo(category, date, user);
				if(expenses!=null)
				{
					return expenses;
				}
				return null;
	}
	
	
	
	@Override
	public List filter(double amount, String category, LocalDate date, User user)
	{
		List<Expenses> expenses = expensesRepository.findByAmountAndExpenseCategoryAndDateAndUserInfo(amount, category, date, user);
		if(expenses!=null)
		{
			return expenses;
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public double totalExpenses(User user, LocalDate startDate, LocalDate endDate)
	{
		double total = expensesRepository.sumAmountByUserIdAndDateRange(user, startDate, endDate);
		
		return total;
	}
		
}
