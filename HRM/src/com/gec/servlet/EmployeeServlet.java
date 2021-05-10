package com.gec.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.service.DeptService;
import com.gec.service.EmployeeService;
import com.gec.service.JobService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.EmployeeServiceImpl;
import com.gec.service.impl.JobServiceImpl;
import com.gec.util.PageModel;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet(value= {"/emplist.action", "/empadd.action","/empedit.action","/emsaveOrUpdate.action","/empdel.action"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeService es = new EmployeeServiceImpl();
	JobService js = new JobServiceImpl();
	DeptService ds = new DeptServiceImpl();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if(uri.equals("emplist.action")) {
			int pageIndex = 1;
			Employee emp = new Employee();
			Job job = new Job();
			Dept dept = new Dept();
//			String[] ids = request.getParameterValues("employeeIds");
//			//用户权限
//			String session_status = request.getParameter("session_stat");
//			System.out.println("权限为"+session_status);
//				if(ids!=null) {
//					if("2".equals(session_status)) {
//						es.del(ids);
//					}else {
//						request.setAttribute("mess", "权限不足");
//					}
//				}
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String job_id = request.getParameter("job_id");
			if(job_id!=null&&!job_id.equals("")) {
				int jobid = Integer.parseInt(job_id);
				job.setId(jobid);
				emp.setJob(job);
				emp.setJob_id(jobid);
			}else {
				job.setId(0);
				emp.setJob(job);
			}
			String name = request.getParameter("name");
			if(name!=null&&!name.equals("")) {
				emp.setName(name);
			}
			String cardId = request.getParameter("cardId");
			if(cardId!=null&&!cardId.equals("")) {
				emp.setCardId(cardId);
			}
			String sex = request.getParameter("sex");
			if(sex!=null&&!sex.equals("")) {
				int empsex = Integer.parseInt(sex);
				emp.setSex(empsex);
			}else {
				emp.setSex(-1);
			}
			String phone = request.getParameter("phone");
			if(phone!=null&&!phone.equals("")) {
				emp.setPhone(phone);
			}
			String dept_id = request.getParameter("dept_id");
			if(dept_id!=null&&!dept_id.equals("")) {
				int deptid = Integer.parseInt(dept_id);
				dept.setId(deptid);
				emp.setDept(dept);
				emp.setDept_id(deptid);
			}else {
				dept.setId(0);
				emp.setDept(dept);
			}
			List<Job> jobList = js.findAll();
			List<Dept> deptList = ds.findAll();
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			PageModel<Employee> pageModel = es.findPage(pageIndex, emp);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("emp", emp);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}
		//删除
		else if(uri.equals("empdel.action")) {
			String[] ids = request.getParameterValues("employeeIds");
			//用户权限
			String session_status = request.getParameter("session_stat");
			System.out.println("权限为"+session_status);
			int pageIndex = 1;
			Employee emp = new Employee();
			Job job = new Job();
			Dept dept = new Dept();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String job_id = request.getParameter("job_id");
			if(job_id!=null&&!job_id.equals("")) {
				int jobid = Integer.parseInt(job_id);
				job.setId(jobid);
				emp.setJob(job);
				emp.setJob_id(jobid);
			}else {
				job.setId(0);
				emp.setJob(job);
			}
			String name = request.getParameter("name");
			if(name!=null&&!name.equals("")) {
				emp.setName(name);
			}
			String cardId = request.getParameter("cardId");
			if(cardId!=null&&!cardId.equals("")) {
				emp.setCardId(cardId);
			}
			String sex = request.getParameter("sex");
			if(sex!=null&&!sex.equals("")) {
				int empsex = Integer.parseInt(sex);
				emp.setSex(empsex);
			}else {
				emp.setSex(-1);
			}
			String phone = request.getParameter("phone");
			if(phone!=null&&!phone.equals("")) {
				emp.setPhone(phone);
			}
			String dept_id = request.getParameter("dept_id");
			if(dept_id!=null&&!dept_id.equals("")) {
				int deptid = Integer.parseInt(dept_id);
				dept.setId(deptid);
				emp.setDept(dept);
				emp.setDept_id(deptid);
			}else {
				dept.setId(0);
				emp.setDept(dept);
			}
			List<Job> jobList = js.findAll();
			List<Dept> deptList = ds.findAll();
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			PageModel<Employee> pageModel = es.findPage(pageIndex, emp);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("emp", emp);
				if(ids!=null) {
					if("2".equals(session_status)) {
						es.del(ids);
						request.setAttribute("mess", "删除成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "权限不足");
						request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
					}
				}
		}
		//添加
		else if(uri.equals("empadd.action")){
			List<Job> jobList = js.findAll();
			List<Dept> deptList = ds.findAll();
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("val", "add");
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
		}//修改查询
		else if(uri.equals("empedit.action")) {
			String id = request.getParameter("id");
			//System.out.println("获取到的用户id为"+id);
			if(id!=null) {
				Employee empud = es.findById(Integer.parseInt(id));
				if(empud!=null) {
					List<Job> jobList = js.findAll();
					List<Dept> deptList = ds.findAll();
					request.setAttribute("jobList", jobList);
					request.setAttribute("deptList", deptList);
					request.setAttribute("employee", empud);
					request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
					}
				}		
		//修改
		}else if(uri.equals("emsaveOrUpdate.action")) {
			Job job = new Job();
			Dept dept = new Dept();
			String id = request.getParameter("id");
			//System.out.println("获取到的用户id为"+id);
			String session_status = request.getParameter("session_stat");
			System.out.println("获取限权为:"+session_status);
			if(id!=null&&!id.equals("")) {
				Employee empud = es.findById(Integer.parseInt(id));
				//System.out.println("获取到修改部门id为"+id);
				if("2".equals(session_status)) {
					String name = request.getParameter("name");
					if(name!=null&&!name.equals("")) {
						empud.setName(name);
					}
					String cardId = request.getParameter("cardId");
					if(cardId!=null&&!cardId.equals("")) {
						empud.setCardId(cardId);
					}
					String sex = request.getParameter("sex");
					if(sex!=null&&!sex.equals("")) {
						int empsex = Integer.parseInt(sex);
						empud.setSex(empsex);
					}else {
						empud.setSex(-1);
					}
					String job_id = request.getParameter("job_id");
					if(job_id!=null&&!job_id.equals("")) {
						int jobid = Integer.parseInt(job_id);
						job.setId(jobid);
						empud.setJob(job);
						empud.setJob_id(jobid);
					}else {
						job.setId(0);
						empud.setJob(job);
					}
					String education = request.getParameter("education");
					if(education!=null&&!education.equals("")) {
						empud.setEducation(education);
					}
					
					String email= request.getParameter("email");
					if(email!=null&&!email.equals("")) {
						empud.setEmail(email);
					}					
					String phone = request.getParameter("phone");
					if(phone!=null&&!phone.equals("")) {
						empud.setPhone(phone);
					}
					String tel = request.getParameter("tel");
					if(tel!=null&&!tel.equals("")) {
						empud.setTel(tel);
					}
					
					String party = request.getParameter("party");
					if(party!=null&&!party.equals("")) {
						empud.setParty(party);
					}
					
					String qqNum = request.getParameter("qqNum");
					if(qqNum!=null&&!qqNum.equals("")) {
						empud.setQqNum(qqNum);
					}
					
					String address = request.getParameter("address");
					if(address!=null&&!address.equals("")) {
						empud.setAddress(address);
					}
					
					String postCode = request.getParameter("postCode");
					if(postCode!=null&&!postCode.equals("")) {
						empud.setPostCode(postCode);
					}
					
					String birthday = request.getParameter("birthday");
					if(birthday!=null&&!birthday.equals("")) {
						SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date bd = null;
						try {
							bd = date.parse(birthday);
						} catch (Exception e) {
							date = new SimpleDateFormat("yyyy-MM-dd");
							try {
								bd = date.parse(birthday);
							} catch (ParseException e1) {
								bd = empud.getBirthday();
							}
						}
						empud.setBirthday(bd);
					}
					
					String race = request.getParameter("race");
					if(race!=null&&!race.equals("")) {
						empud.setRace(race);
					}
					String speciality = request.getParameter("speciality");
					if(speciality!=null&&!speciality.equals("")) {
						empud.setSpeciality(speciality);
					}
					String hobby = request.getParameter("hobby");
					if(hobby!=null&&!hobby.equals("")) {
						empud.setHobby(hobby);
					}
					String remark = request.getParameter("remark");
					if(remark!=null&&!remark.equals("")) {
						empud.setRemark(remark);
					}
					String dept_id = request.getParameter("dept_id");
					if(dept_id!=null&&!dept_id.equals("")) {
						int deptid = Integer.parseInt(dept_id);
						dept.setId(deptid);
						empud.setDept(dept);
						empud.setDept_id(deptid);
					}else {
						dept.setId(0);
						empud.setDept(dept);
					}
					es.update(empud);
					if(empud!=null) {
						List<Job> jobList = js.findAll();
						List<Dept> deptList = ds.findAll();
						request.setAttribute("jobList", jobList);
						request.setAttribute("deptList", deptList);
						request.setAttribute("employee",empud);
						request.setAttribute("message", "修改成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
					}
				}else {
					List<Job> jobList = js.findAll();
					List<Dept> deptList = ds.findAll();
					request.setAttribute("jobList", jobList);
					request.setAttribute("deptList", deptList);
					request.setAttribute("employee", empud);
					request.setAttribute("message", "权限不足,无法修改");
					request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
				}
				//添加
			}else {
				boolean flag = true;
				Employee newemp = new Employee();
				String name = request.getParameter("name");
				if(name!=null&&!name.equals("")) {
					newemp.setName(name);
				}
				String cardId = request.getParameter("cardId");
				if(cardId!=null&&!cardId.equals("")) {
					List<Employee> listcardid = es.findAll();
					for (Employee empcid : listcardid) {
						if(cardId.equals(empcid.getCardId())) {
							flag = false;
						}
					}
					newemp.setCardId(cardId);
				}
				String sex = request.getParameter("sex");
				if(sex!=null&&!sex.equals("")) {
					int empsex = Integer.parseInt(sex);
					newemp.setSex(empsex);
				}else {
					newemp.setSex(-1);
				}
				String job_id = request.getParameter("job_id");
				if(job_id!=null&&!job_id.equals("")) {
					int jobid = Integer.parseInt(job_id);
					job.setId(jobid);
					newemp.setJob(job);
					newemp.setJob_id(jobid);
				}else {
					job.setId(0);
					newemp.setJob(job);
				}
				String education = request.getParameter("education");
				if(education!=null&&!education.equals("")) {
					newemp.setEducation(education);
				}
				
				String email= request.getParameter("email");
				if(email!=null&&!email.equals("")) {
					newemp.setEmail(email);
				}					
				String phone = request.getParameter("phone");
				if(phone!=null&&!phone.equals("")) {
					newemp.setPhone(phone);
				}
				String tel = request.getParameter("tel");
				if(tel!=null&&!tel.equals("")) {
					newemp.setTel(tel);
				}
				
				String party = request.getParameter("party");
				if(party!=null&&!party.equals("")) {
					newemp.setParty(party);
				}
				
				String qqNum = request.getParameter("qqNum");
				if(qqNum!=null&&!qqNum.equals("")) {
					newemp.setQqNum(qqNum);
				}
				
				String address = request.getParameter("address");
				if(address!=null&&!address.equals("")) {
					newemp.setAddress(address);
				}
				
				String postCode = request.getParameter("postCode");
				if(postCode!=null&&!postCode.equals("")) {
					newemp.setPostCode(postCode);
				}
				
				String birthday = request.getParameter("birthday");
				if(birthday!=null&&!birthday.equals("")) {
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date bd = null;
					try {
						bd = date.parse(birthday);
					} catch (Exception e) {
						bd = newemp.getBirthday();
					}
					newemp.setBirthday(bd);
				}
				
				String race = request.getParameter("race");
				if(race!=null&&!race.equals("")) {
					newemp.setRace(race);
				}
				String speciality = request.getParameter("speciality");
				if(speciality!=null&&!speciality.equals("")) {
					newemp.setSpeciality(speciality);
				}
				String hobby = request.getParameter("hobby");
				if(hobby!=null&&!hobby.equals("")) {
					newemp.setHobby(hobby);
				}
				String remark = request.getParameter("remark");
				if(remark!=null&&!remark.equals("")) {
					newemp.setRemark(remark);
				}
				String dept_id = request.getParameter("dept_id");
				if(dept_id!=null&&!dept_id.equals("")) {
					int deptid = Integer.parseInt(dept_id);
					dept.setId(deptid);
					newemp.setDept(dept);
					newemp.setDept_id(deptid);
				}else {
					dept.setId(0);
					newemp.setDept(dept);
				}
				if(flag) {
					if(es.save(newemp)) {
						request.setAttribute("mess", "添加成功!");
						response.sendRedirect(request.getContextPath()+"/emplist.action");
					}
				}else {
					request.setAttribute("idmess", "身份证ID已存在,请重新输入");
					List<Job> jobList = js.findAll();
					List<Dept> deptList = ds.findAll();
					request.setAttribute("jobList", jobList);
					request.setAttribute("deptList", deptList);
					request.setAttribute("employee", newemp);
					request.setAttribute("val", "add");
					request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
				}
			}
		}
	}

}
