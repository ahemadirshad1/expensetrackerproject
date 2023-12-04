 package com.jsp.expense_tracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jsp.expense_tracker.entity.Expenses;
import com.jsp.expense_tracker.entity.User;

public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

	List<Expenses> findByAmountAndUserInfo(double amount, User user);
	List<Expenses> findByExpenseCategoryAndUserInfo(String category, User user);
	List<Expenses> findByDateAndUserInfo(LocalDate date, User user);
	
	List<Expenses> findByAmountAndExpenseCategoryAndUserInfo(double amount, String category, User user);
	List<Expenses> findByAmountAndDateAndUserInfo(double amount,LocalDate date, User user);
	List<Expenses> findByExpenseCategoryAndDateAndUserInfo(String category, LocalDate date, User user);
	
	List<Expenses> findByAmountAndExpenseCategoryAndDateAndUserInfo(double amount, String category, LocalDate date, User user);
	
//	@Query("SELECT SUM(e.amount FROM Expenses e WHERE e.userId = myUserId")
//	double sumAmountWithCondition(@Param("myUserId")int userId);
//	
	@Query("SELECT SUM(e.amount) FROM Expenses e WHERE e.userInfo = :userInfo AND e.date BETWEEN :startDate AND :endDate")
    double sumAmountByUserIdAndDateRange(@Param("userInfo") User user,
                                         @Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);
	
}
