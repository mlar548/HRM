package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Job;
import com.gec.service.JobService;
import com.gec.service.impl.JobServiceImpl;
import com.gec.util.PageModel;

/**
 * Servlet implementation class JobServlet
 */
@WebServlet(value= {"/joblist.action","/jobadd.action","/addOrUpdate.action","/jobupdate.action","/jobdel.action"})
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JobService js = new JobServiceImpl();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到url 请求路径
				String uri = request.getRequestURI();
				uri = uri.substring(uri.lastIndexOf("/")+1);
				System.out.println("执行:"+uri);
				if(uri.equals("joblist.action")) {
					int pageIndex = 1;
					Job entity = new Job();
//					String[] ids = request.getParameterValues("jobIds");
//					//用户权限
//					String session_status = request.getParameter("session_stat");
//					System.out.println("权限为"+session_status);
//						if(ids!=null) {
//							if("2".equals(session_status)) {
//								js.del(ids);
//							}else {
//								request.setAttribute("mess", "权限不足");
//							}
//						}
					String Index = request.getParameter("pageIndex");
					String name = request.getParameter("name");
					//System.out.println("搜素名:"+name);
					//System.out.println("页数"+Index+"  登录名"+loginname+"  用户名"+username+" 状态"+status+" ok");
					if(Index!=null) {
						pageIndex = Integer.parseInt(Index);
					}
					if(name!=null&&!name.equals("")) {
						entity.setName(name);
						//System.out.println("获取到搜素名:"+entity.getName());
					}
					//System.out.println("页数"+pageIndex+"  登录名"+entity.getLoginname()+"  用户名"+entity.getUsername()+" 状态"+entity.getStatus()+" check");	
					PageModel<Job> pageModel = js.findPage(pageIndex, entity);
					request.setAttribute("pageModel", pageModel);
					request.setAttribute("job", entity);
					request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
				}
				//删除
				else if(uri.equals("jobdel.action")) {
					String[] ids = request.getParameterValues("jobIds");
					//用户权限
					String session_status = request.getParameter("session_stat");
					int pageIndex = 1;
					Job entity = new Job();
					String Index = request.getParameter("pageIndex");
					String name = request.getParameter("name");
					if(Index!=null) {
						pageIndex = Integer.parseInt(Index);
					}
					if(name!=null&&!name.equals("")) {
						entity.setName(name);
					}
					PageModel<Job> pageModel = js.findPage(pageIndex, entity);
					request.setAttribute("pageModel", pageModel);
					request.setAttribute("job", entity);
					System.out.println("权限为"+session_status);
						if(ids!=null) {
							if("2".equals(session_status)) {
								js.del(ids);
								request.setAttribute("mess", "删除成功!");
								request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
							}else {
								request.setAttribute("mess", "权限不足");
								request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
							}
						}
				}
				//修改查询
				else if(uri.equals("jobupdate.action")) {
					String id = request.getParameter("id");
					//System.out.println("获取到的用户id为"+id);
					if(id!=null) {
						Job jobud = js.findById(Integer.parseInt(id));
						if(jobud!=null) {
							request.setAttribute("job", jobud);
							request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
							}
						}		
				//修改
				}else if(uri.equals("addOrUpdate.action")) {
					String id = request.getParameter("id");
					System.out.println("获取到的用户id为"+id);
					String session_status = request.getParameter("session_stat");
					System.out.println("获取限权为:"+session_status);
					if(id!=null&&!id.equals("")) {
						Job jobud = js.findById(Integer.parseInt(id));
						System.out.println("获取到修改部门id为"+id);
						if("2".equals(session_status)) {
							String name = request.getParameter("name");
							String remark = request.getParameter("remark"); 
							if(name!=null&&!name.equals("")) {
								jobud.setName(name);
							}
							if(remark!=null&&!remark.equals("")) {
								jobud.setRemark(remark);
							}
							js.update(jobud);
							if(jobud!=null) {
								request.setAttribute("job", jobud);
								request.setAttribute("message", "修改成功!");
								request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
							}
						}else {
							request.setAttribute("job", jobud);
							request.setAttribute("message", "权限不足,无法修改");
							request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
						}
					}else {
						Job newjob = new Job();
						String name = request.getParameter("name");
						String remark = request.getParameter("remark"); 
						if(name!=null&&!name.equals("")) {
							newjob.setName(name);
						}
						if(remark!=null&&!remark.equals("")) {
							newjob.setRemark(remark);
						}
						if(js.save(newjob)) {
							request.setAttribute("mess", "添加成功!");
							response.sendRedirect(request.getContextPath()+"/joblist.action");
						}
					}
				}
				//添加
				else if(uri.equals("jobadd.action")){
					request.setAttribute("val", "add");
					request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
				}
	}

}
