package com.gec.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Dept;
import com.gec.service.DeptService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.util.PageModel;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(value= {"/deptlist.action","/deptadd.action","/deptupdate.action","/saveOrUpdate.action","/deptdel.action"})
public class DeptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DeptService ds = new DeptServiceImpl();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到url 请求路径
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		System.out.println("执行:"+uri);
		if(uri.equals("deptlist.action")) {
			int pageIndex = 1;
			Dept entity = new Dept();
//			String[] ids = request.getParameterValues("deptIds");
//			//用户权限
//			String session_status = request.getParameter("session_stat");
//			System.out.println("权限为"+session_status);
//				if(ids!=null) {
//					if("2".equals(session_status)) {
//						ds.del(ids);
//					}else {
//						request.setAttribute("mess", "权限不足");
//					}
//				}
			String Index = request.getParameter("pageIndex");
			String name = request.getParameter("name");
			//System.out.println("页数"+Index+"  登录名"+loginname+"  用户名"+username+" 状态"+status+" ok");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			if(name!=null&&!name.equals("")) {
				entity.setName(name);
			}
			//System.out.println("页数"+pageIndex+"  登录名"+entity.getLoginname()+"  用户名"+entity.getUsername()+" 状态"+entity.getStatus()+" check");	
			PageModel<Dept> pageModel = ds.findPage(pageIndex, entity);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("dept", entity);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		}
		//删除
		else if(uri.equals("deptdel.action")) {
			String[] ids = request.getParameterValues("deptIds");
			//用户权限
			String session_status = request.getParameter("session_stat");
			System.out.println("权限为"+session_status);
			int pageIndex = 1;
			Dept entity = new Dept();
			String Index = request.getParameter("pageIndex");
			String name = request.getParameter("name");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			if(name!=null&&!name.equals("")) {
				entity.setName(name);
			}
			PageModel<Dept> pageModel = ds.findPage(pageIndex, entity);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("dept", entity);
				if(ids!=null) {
					if("2".equals(session_status)) {
						ds.del(ids);
						request.setAttribute("mess", "删除成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "权限不足");
						request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
					}
				}
		}
		//修改查询
		else if(uri.equals("deptupdate.action")) {
			String id = request.getParameter("id");
			//System.out.println("获取到的用户id为"+id);
			if(id!=null) {
				Dept deptud = ds.findById(Integer.parseInt(id));
				if(deptud!=null) {
					request.setAttribute("dept", deptud);
					request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
					}
				}		
		}
		//修改
		else if(uri.equals("saveOrUpdate.action")) {
			String id = request.getParameter("id");
			System.out.println("获取到的用户id为"+id);
			String session_status = request.getParameter("session_stat");
			if(id!=null&&!id.equals("")) {
				Dept deptud = ds.findById(Integer.parseInt(id));
				System.out.println("获取到修改部门id为"+id);
				if("2".equals(session_status)) {
					String name = request.getParameter("name");
					String remark = request.getParameter("remark"); 
					if(name!=null&&!name.equals("")) {
						deptud.setName(name);
					}
					if(remark!=null&&!remark.equals("")) {
						deptud.setRemark(remark);
					}
					ds.update(deptud);
					if(deptud!=null) {
						request.setAttribute("dept", deptud);
						request.setAttribute("message", "修改成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
					}
				}else {
					request.setAttribute("dept", deptud);
					request.setAttribute("message", "权限不足,无法修改");
					request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
				}
			}else {
				Dept newdept = new Dept();
				String name = request.getParameter("name");
				String remark = request.getParameter("remark"); 
				if(name!=null&&!name.equals("")) {
					newdept.setName(name);
				}
				if(remark!=null&&!remark.equals("")) {
					newdept.setRemark(remark);
				}
				if(ds.save(newdept)) {
					request.setAttribute("mess", "添加成功!");
					response.sendRedirect(request.getContextPath()+"/deptlist.action");
				}
			}
		}
		//添加
		else if(uri.equals("deptadd.action")){
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		}
	}
}
