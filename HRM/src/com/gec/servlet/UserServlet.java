package com.gec.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.User;
import com.gec.service.UserService;
import com.gec.service.impl.UserServiceImpl;
import com.gec.util.PageModel;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(value= {"/loginForm.action","/login.action","/useredit.action","/useraddsave.action","/userlist.action","/useradd.action","/userupdate.action","/userdel.action"})
public class UserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserService us = new UserServiceImpl();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到url 请求路径
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		System.out.println("执行:"+uri);
		if(uri.equals("loginForm.action")) {
			//因为无法直接访问页面,都需要通过转发的形式访问页面
			request.getRequestDispatcher("/WEB-INF/jsp/loginForm.jsp").forward(request, response);
			
		}else if(uri.equals("login.action")) {
			//获取用户名和密码
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			User user_session = us.login(loginname, password);
			if(user_session!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("user_session", user_session);
				//转跳到主页的Servlet类
				response.sendRedirect(request.getContextPath()+"/main.action");
			}else {
				request.setAttribute("message", "用户名或密码错误");
				request.getRequestDispatcher("/WEB-INF/jsp/loginForm.jsp").forward(request, response);
			}
		}else if(uri.equals("userlist.action")) {
			int pageIndex = 1;
			User entity = new User();
			String stat = request.getParameter("session_stat");
			String liststate = request.getParameter("liststate");
//			System.out.println("pwd权限为"+stat);
//			boolean flag = false;
//			//获取当前用户id
//			String[] ids = request.getParameterValues("userIds");
//			String session_id = request.getParameter("session_id");
//			//用户权限
//			String session_status = request.getParameter("session_stat");
//			System.out.println("权限为"+session_status);
//			if(ids!=null) {
//				for (int i = 0; i < ids.length; i++) {
//					if(!ids[i].equals(session_id)) {
//						flag = true;
//					}else {
//						flag = false;
//						request.setAttribute("mess", "不能删除当前账户");
//					}
//				}
//			}
//			if(flag) {
//				if(ids!=null) {
//					if("2".equals(session_status)) {
//						us.del(ids);
//					}else {
//						request.setAttribute("mess", "权限不足");
//					}
//				}
//			}
			
			String Index = request.getParameter("pageIndex");
			String loginname = request.getParameter("loginname");
			String username = request.getParameter("username");
			String status = request.getParameter("status");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			if(loginname!=null&&!loginname.equals("")) {
				entity.setLoginname(loginname);
			}
			if(username!=null&&!username.equals("")) {
				entity.setUsername(username);
			}
			if(status!=null&&!status.equals("")) {
				entity.setStatus(Integer.parseInt(status));
			}else {
				entity.setStatus(0);
			}			
			PageModel<User> pageModel = us.findPage(pageIndex, entity);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("user", entity);
			
			if("2".equals(liststate)) {
				request.setAttribute("val", "pwd");
			}
			if("2".equals(stat)) {
				request.setAttribute("val", "pwd");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
		}
		//删除
		else if(uri.equals("userdel.action")) {
			boolean flag = false;
			//获取当前用户id
			String[] ids = request.getParameterValues("userIds");
			String session_id = request.getParameter("session_id");
			//用户权限
			String session_status = request.getParameter("session_stat");
			System.out.println("权限为"+session_status);
			int pageIndex = 1;
			User entity = new User();
			String Index = request.getParameter("pageIndex");
			String loginname = request.getParameter("loginname");
			String username = request.getParameter("username");
			String status = request.getParameter("status");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			if(loginname!=null&&!loginname.equals("")) {
				entity.setLoginname(loginname);
			}
			if(username!=null&&!username.equals("")) {
				entity.setUsername(username);
			}
			if(status!=null&&!status.equals("")) {
				entity.setStatus(Integer.parseInt(status));
			}else {
				entity.setStatus(0);
			}			
			PageModel<User> pageModel = us.findPage(pageIndex, entity);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("user", entity);
			if(ids!=null) {
				for (int i = 0; i < ids.length; i++) {
					if(!ids[i].equals(session_id)) {
						flag = true;
					}else {
						flag = false;
						request.setAttribute("mess", "不能删除当前账户");
						request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
					}
				}
			}
			if(flag) {
				if(ids!=null) {
					if("2".equals(session_status)) {
						us.del(ids);
						request.setAttribute("mess", "删除成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "权限不足");
						request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
					}
				}
			}
		}
		//添加
		else if(uri.equals("useradd.action")){
			request.setAttribute("val", "add");
			String session_status = request.getParameter("addstate");
			System.out.println("获取添加权限"+session_status);
			if("2".equals(session_status)) {
				request.setAttribute("state", "stat");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
		}
		//修改查询
		else if(uri.equals("userupdate.action")) {
				String id = request.getParameter("id");
				String delstate = request.getParameter("delstate");
				//System.out.println("获取到的用户id为"+id);
				if(id!=null&&!id.equals("")) {
					User userud = us.findById(Integer.parseInt(id));
					if(userud!=null) {
						if("2".equals(delstate)) {
							request.setAttribute("user", userud);
							request.setAttribute("state", "stat");
							request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
						}else {
							 	response.sendRedirect(request.getContextPath()+"/userlist.action");
							}
						}
					}		
		//修改与添加
		}else if(uri.equals("useredit.action")) {
			List<User> list = us.findAll();
			boolean flaglogin = false;
			boolean flaguser = false;
			boolean flaglen = false;
			boolean flag = true;
			String id = request.getParameter("id");
			//System.out.println("获取到的用户id为"+id);
			String session_id = request.getParameter("session_id");
			String session_status = request.getParameter("session_stat");
			System.out.println("当前用户id:"+session_id+"  修改用户id:"+id);
			if(id!=null&&!id.equals("")) {
				User userud = us.findById(Integer.parseInt(id));
				System.out.println("获取到修改用户id为"+id);
				if("2".equals(session_status)) {
					String loginname = request.getParameter("loginname");
					String password = request.getParameter("password");
					String username = request.getParameter("username");
					String status = request.getParameter("status");
					if(loginname!=null&&!loginname.equals("")) {
						if(!loginname.equals(userud.getLoginname())) {
							for (User usercheck : list) {
								if(loginname.equals(usercheck.getLoginname())) {
									flag = false;
									flaglogin = true;
								}
							}
						}
						if(loginname.length()<6) {
							flag = false;
							flaglen = true;
						}
						userud.setLoginname(loginname);
					}
					if(password!=null&&!password.equals("")) {
						userud.setPassword(password);
					}
					if(username!=null&&!username.equals("")) {
						if(!username.equals(userud.getUsername())) {
							for (User usercheck : list) {
								if(username.equals(usercheck.getUsername())) {
									flag = false;
									flaguser = true;
								}
							}
						}
						
						userud.setUsername(username);
					}
					if(id.equals(session_id)) {
						if(!status.equals(session_status)) {
							request.setAttribute("statemess", "不能修改当前用户权限");
						}
					}else {
						if(status!=null&&!status.equals("")) {
							userud.setStatus(Integer.parseInt(status));
						}
					}
					if(flag) {
						us.update(userud);
						if(userud!=null) {
							request.setAttribute("user", userud);
							request.setAttribute("message", "修改成功!");
							request.setAttribute("state", "stat");
							request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
						}
					}else {
						request.setAttribute("user", userud);
						if(flaglogin) {
							request.setAttribute("usermess", "姓名已存在,请重新输入!");
						}
						if(flaguser){
							request.setAttribute("loginmess", "登录名已存在,请重新输入!");		
						}
						if(flaglen){
							request.setAttribute("lenmess", "登录名不能小于6位,请重新输入!");		
						}
						if("2".equals(session_status)) {
							request.setAttribute("state", "stat");
						}
						request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
					}
				}else {
					request.setAttribute("user", userud);
					request.setAttribute("message", "权限不足,无法修改");
					request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
				}
			//添加
			}else {
				User newuser = new User();
				String loginname = request.getParameter("loginname");
				String password = request.getParameter("password");
				String username = request.getParameter("username");
				String status = request.getParameter("status");
				if(loginname!=null&&!loginname.equals("")) {
					for (User usercheck : list) {
						if(loginname.equals(usercheck.getLoginname())) {
							flag = false;
							flaglogin = true;
						}
					}
					if(loginname.length()<6) {
						flag = false;
						flaglen = true;
					}
					newuser.setLoginname(loginname);
				}
				if(password!=null&&!password.equals("")) {
					newuser.setPassword(password);
				}
				if(username!=null&&!username.equals("")) {
					for (User usercheck : list) {
						if(username.equals(usercheck.getUsername())) {
							flag = false;
							flaguser = true;
						}
					}
					newuser.setUsername(username);
				}
				if(status!=null&&!status.equals("")) {
					newuser.setStatus(Integer.parseInt(status));
				}
				if(flag) {
					if(us.save(newuser)) {
						if("2".equals(session_status)) {
							request.setAttribute("state", "stat");
						}
						request.setAttribute("message", "添加成功!");
						request.setAttribute("val", "add");
						request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
					}
				}else {
					request.setAttribute("user", newuser);
					if(flaglogin) {
						request.setAttribute("usermess", "姓名已存在,请重新输入!");
					}
					if(flaguser){
						request.setAttribute("loginmess", "登录名已存在,请重新输入!");		
					}
					if(flaglen){
						request.setAttribute("lenmess", "登录名不能小于6位,请重新输入!");		
					}
					if("2".equals(session_status)) {
						request.setAttribute("state", "stat");
					}
					request.setAttribute("val", "add");
					request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
				}
				
			}
		}	
	}
}
