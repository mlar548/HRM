package com.gec.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Notice;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.service.NoticeService;
import com.gec.service.TypeService;
import com.gec.service.impl.NoticeServiceImpl;
import com.gec.service.impl.TypeServiceImpl;
import com.gec.util.PageModel;

@WebServlet(value= {"/noticelist.action","/noticedel.action","/noticeupdate.action","/noticeadd.action","/noticesaveOrUpdate.action",
					"/typelist.action","/typedel.action","/typeupdate.action","/typeadd.action","/typesaveOrUpdate.action"})
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NoticeService ns = new NoticeServiceImpl();
	TypeService ts = new TypeServiceImpl();

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if(uri.equals("noticelist.action")) {
			int pageIndex = 1;
			Notice nt = new Notice();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String name = request.getParameter("name");
			if(name!=null&&!name.equals("")) {
				nt.setName(name);
			}
			PageModel<Notice> pageModel = ns.findPage(pageIndex, nt);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("notice", nt);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}
		//删除
		else if(uri.equals("noticedel.action")) {
			String[] ids = request.getParameterValues("noticeIds");
			//用户权限
			String session_status = request.getParameter("session_stat");
			System.out.println("权限为"+session_status);
			int pageIndex = 1;
			Notice nt = new Notice();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String name = request.getParameter("name");
			if(name!=null&&!name.equals("")) {
				nt.setName(name);
			}
			PageModel<Notice> pageModel = ns.findPage(pageIndex, nt);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("notice", nt);
				if(ids!=null) {
					if("2".equals(session_status)) {
						ns.del(ids);
						request.setAttribute("mess", "删除成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "权限不足");
						request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);					}
				}
		}
		//添加
		else if(uri.equals("noticeadd.action")){
			request.setAttribute("val", "add");
			List<Type> typeList = ts.findAll();
			request.setAttribute("typeList", typeList);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
		}
		//修改查询
		else if(uri.equals("noticeupdate.action")) {	
			String id = request.getParameter("id");
			if(id!=null) {
				Notice ntud = ns.findById(Integer.parseInt(id));
				if(ntud!=null) {
					request.setAttribute("notice", ntud);
					List<Type> typeList = ts.findAll();
					request.setAttribute("typeList", typeList);
					request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
					}
				}
		}
		//添加修改
		else if(uri.equals("noticesaveOrUpdate.action")) {
			Type type = new Type();
			User user = new User();
			String id = request.getParameter("id");
			System.out.println("获取到的用户id为"+id);
			String session_status = request.getParameter("session_stat");
			if(id!=null&&!id.equals("")) {
				Notice ntud = ns.findById(Integer.parseInt(id));
				System.out.println("获取到修改部门id为"+id);
				if("2".equals(session_status)) {
					//获取当前用户ID
					String session_id = request.getParameter("session_id");
					if(session_id!=null&&!session_id.equals("")) {
						int userid = Integer.parseInt(session_id);
						user.setId(userid);
						ntud.setUser_id(userid);
					}
					String name = request.getParameter("name");
					String text = request.getParameter("text");
					String type_id = request.getParameter("type_id");
					if(name!=null&&!name.equals("")) {
						ntud.setName(name);
					}
					if(text!=null&&!text.equals("")) {
						ntud.setContent(text);
					}
					if(type_id!=null&&!type_id.equals("")) {
						int typeid = Integer.parseInt(type_id);
						type.setId(typeid);
						ntud.setType(type);
						ntud.setType_id(typeid);
					}
					ns.update(ntud);
					if(ntud!=null) {
						request.setAttribute("notice", ntud);
						List<Type> typeList = ts.findAll();
						request.setAttribute("typeList", typeList);
						request.setAttribute("message", "修改成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
					}
				}else {
					request.setAttribute("notice", ntud);
					List<Type> typeList = ts.findAll();
					request.setAttribute("typeList", typeList);
					request.setAttribute("message", "权限不足,无法修改");
					request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
				}
			}else {
				Notice newnt = new Notice();
				//获取当前用户ID
				String session_id = request.getParameter("session_id");
				if(session_id!=null&&!session_id.equals("")) {
					int userid = Integer.parseInt(session_id);
					user.setId(userid);
					newnt.setUser_id(userid);
				}
				String name = request.getParameter("name");
				String text = request.getParameter("text");
				String type_id = request.getParameter("type_id");
				if(name!=null&&!name.equals("")) {
					newnt.setName(name);
				}
				if(text!=null&&!text.equals("")) {
					newnt.setContent(text);
				}
				if(type_id!=null&&!type_id.equals("")) {
					int typeid = Integer.parseInt(type_id);
					type.setId(typeid);
					newnt.setType(type);
					newnt.setType_id(typeid);
				}
				if(ns.save(newnt)) {
					request.setAttribute("mess", "添加成功!");
					response.sendRedirect(request.getContextPath()+"/noticelist.action");
				}
			}
		}
		//类型
		//查询
		else if(uri.equals("typelist.action")) {
			int pageIndex = 1;
			Type type = new Type();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String name = request.getParameter("name");
			if(name!=null&&!name.equals("")) {
				type.setName(name);
			}
			PageModel<Type> pageModel = ts.findPage(pageIndex, type);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("type", type);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);
		}
		//删除
		else if(uri.equals("typedel.action")) {
			String[] ids = request.getParameterValues("typeIds");
			//用户权限
			String session_status = request.getParameter("session_stat");
			System.out.println("权限为"+session_status);
			int pageIndex = 1;
			Type type = new Type();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String name = request.getParameter("name");
			if(name!=null&&!name.equals("")) {
				type.setName(name);
			}
			PageModel<Type> pageModel = ts.findPage(pageIndex, type);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("type", type);
				if(ids!=null) {
					if("2".equals(session_status)) {
						ts.del(ids);
						request.setAttribute("mess", "删除成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "权限不足");
						request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);				}
				}
		}//添加
		else if(uri.equals("typeadd.action")){
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
		}
		//修改查询
		else if(uri.equals("typeupdate.action")) {	
			String id = request.getParameter("id");
			if(id!=null) {
				Type typeud = ts.findById(Integer.parseInt(id));
				if(typeud!=null) {
					request.setAttribute("type", typeud);
					request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
					}
				}
		}
		//添加修改
		else if(uri.equals("typesaveOrUpdate.action")) {
			User user = new User();
			String id = request.getParameter("id");
			String session_status = request.getParameter("session_stat");
			if(id!=null&&!id.equals("")) {
				Type typeud = ts.findById(Integer.parseInt(id));
				if("2".equals(session_status)) {
					//获取当前用户ID
					String session_id = request.getParameter("session_id");
					if(session_id!=null&&!session_id.equals("")) {
						int userid = Integer.parseInt(session_id);
						user.setId(userid);
						typeud.setUser_id(userid);
					}
					String name = request.getParameter("name");
					if(name!=null&&!name.equals("")) {
						typeud.setName(name);
					}
					ts.update(typeud);
					if(typeud!=null) {
						request.setAttribute("type", typeud);
						request.setAttribute("message", "修改成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
					}
				}else {
					request.setAttribute("type", typeud);
					request.setAttribute("message", "权限不足,无法修改");
					request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
				}
			}else {
				Type newtype = new Type();
				//获取当前用户ID
				String session_id = request.getParameter("session_id");
				if(session_id!=null&&!session_id.equals("")) {
					int userid = Integer.parseInt(session_id);
					user.setId(userid);
					newtype.setUser_id(userid);
				}
				String name = request.getParameter("name");
				if(name!=null&&!name.equals("")) {
					newtype.setName(name);
				}
				if(ts.save(newtype)) {
					request.setAttribute("mess", "添加成功!");
					response.sendRedirect(request.getContextPath()+"/typelist.action");
				}
			}
		}
	}
}
