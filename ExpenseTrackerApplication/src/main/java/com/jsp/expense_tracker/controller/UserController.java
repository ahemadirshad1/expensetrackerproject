package com.jsp.expense_tracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.expense_tracker.dao.UserDao;
import com.jsp.expense_tracker.entity.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDao dao;

	@RequestMapping(path = "registerOperation", method = RequestMethod.POST)
//	@ResponseBody
	public ModelAndView register(@ModelAttribute User user, @RequestParam("cpassword") String cpassword)
	{
		ModelAndView mv = new ModelAndView();
		if(user.getPassword().equals(cpassword))
		{
			User userFromDB = dao.registerUser(user);
			if(userFromDB!=null)
			{
				mv.setViewName("login");
				mv.addObject("msg", "Registered Succefully!!!");
				return mv;
			}
			mv.setViewName("registeration");
			mv.addObject("msg", "Credentials Not Found!!!");
			return mv;
		}
		mv.setViewName("registeration");
		mv.addObject("msg", "Password Couldn't Match!!!");
		mv.addObject("user", user);
		return mv;
	}
	
	@PostMapping("loginOperation")
//	@RequestMapping(path = "loginOperation", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("username")String username, @RequestParam("password") String password, HttpServletRequest request)
	{
		User user = dao.loginUser(username, password);
		ModelAndView mv = new ModelAndView();
		if(user!=null)
		{
			HttpSession session = request.getSession();
			session.setAttribute("userData", user);
			System.out.println("User Data stored in session");
			
			mv.setViewName("home");
//			mv.addObject("userInfo", user);
			return mv;
		}
		mv.setViewName("login");
		mv.addObject("msg", "Invalid Credentials");
		return mv;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		if(session != null)
		{
			mv.setViewName("login");
			session.invalidate();
			return mv;
		}
		return null;
		
	}
}


