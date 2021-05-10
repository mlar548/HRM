package com.gec.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.bean.Document;
import com.gec.bean.User;
import com.gec.service.DocumentService;
import com.gec.service.UserService;
import com.gec.service.impl.DocumentServiceImpl;
import com.gec.service.impl.UserServiceImpl;
import com.gec.util.PageModel;

/**
 * Servlet implementation class DocumentServlet
 */
@WebServlet(value= {"/documentlist.action","/documentadd.action","/documentaddsave.action","/documentdel.action","/updateDocument.action","/updateDcm.action","/download.action","/preview.action"})
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DocumentService dms = new DocumentServiceImpl();
	UserService us = new UserServiceImpl();
	 private static final String GIF = "image/gif;charset=UTF-8";
	 private static final String JPG = "image/jpeg;charset=UTF-8";
	 private static final String PNG = "image/png;charset=UTF-8";
	 private static final String PDF = "application/pdf;charset=UTF-8";
	 private static final String ZIP = "application/zip;charset=UTF-8";
	 private static final String DOC = "application/msword;charset=UTF-8";
	 private static final String TXT = "text/plain;charset=UTF-8";
	 private static final String AVI = "video/x-msvideo;charset=UTF-8";
	 private static final String MP3 = "audio/mpeg;charset=UTF-8";
	 private static final String PPT = "application/vnd.ms-powerpoint;charset=UTF-8";
	 private static final String WPS = "application/vnd.ms-works;charset=UTF-8";
	 private static final String XLS = "application/vnd.ms-excel;charset=UTF-8";
	 private static final String PUB = "application/x-mspublisher;charset=UTF-8";
	 private static final String RTF = "application/rtf;charset=UTF-8";
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		//StringBuffer url = request.getRequestURL();
		//System.out.println("uri的值："+uri);
		//System.out.println("url的值："+url);
		//url的值：http://localhost:8080/HRM/documentlist.action
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if(uri.equals("documentlist.action")) {
			int pageIndex = 1;
			Document entity = new Document();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String title = request.getParameter("title");
			if(title!=null&&!title.equals("")) {
				entity.setTitle(title);
			}
			List<User> userList = us.findAll();
			request.setAttribute("userList", userList);
			PageModel<Document> pageModel = dms.findPage(pageIndex, entity);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("document", entity);
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		}
		//预览
		else if(uri.equals("preview.action")) {
			 // 文件流
			  InputStream is = null;
			  // 输入缓冲流
			  BufferedInputStream bis = null;
			  // 得到输出流
			  OutputStream output = null;
			  // 输出缓冲流
			  BufferedOutputStream bos = null;
			  // 1.设置参数编码
			  request.setCharacterEncoding("UTF-8");
			  // 2.设置响应数据字符集
			  response.setCharacterEncoding("UTF-8");
			  // 3.获取客户端请求参数：文件名
			  String fileName = request.getParameter("downfilename");
			  // 4.重置response
			  response.reset();
			  // 5.设置响应数据格式
			  if (fileName.endsWith(".gif")) {
			   response.setContentType(GIF);
			  } else if (fileName.endsWith(".jpg")) {
			   response.setContentType(JPG);
			  } else if (fileName.endsWith(".png")) {
			   response.setContentType(PNG);
			  } else if (fileName.endsWith(".pdf")) {
			   response.setContentType(PDF);
			  } else if (fileName.endsWith(".gif")) {
			   response.setContentType(GIF);
			  } else if (fileName.endsWith(".zip")) {
			   response.setContentType(ZIP);
			  } else if (fileName.endsWith(".doc")) { 
				response.setContentType(DOC); 
			  } else if (fileName.endsWith(".txt")) { 
				response.setContentType(TXT); 
			  } else if (fileName.endsWith(".avi")) { 
				response.setContentType(AVI); 
			  } else if (fileName.endsWith(".mp3")) { 
				response.setContentType(MP3); 
			  } else if (fileName.endsWith(".ppt")) { 
				response.setContentType(PPT); 
			  } else if (fileName.endsWith(".wps")) { 
				response.setContentType(WPS); 
			  } else if (fileName.endsWith(".xls")) { 
				response.setContentType(XLS); 
			  } else if (fileName.endsWith(".pub")) { 
				response.setContentType(PUB); 
			  } else if (fileName.endsWith(".rtf")) { 
				response.setContentType(RTF); 
			  }
			  try {
				  String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
				  System.out.println("地址:"+path);
				  System.out.println("文件名:"+fileName);
				  // 7.读取目标文件，通过response将目标文件写到客户端
				  is = new FileInputStream(path+File.separator+fileName);
				  bis = new BufferedInputStream(is);
				  output = response.getOutputStream();
				  bos = new BufferedOutputStream(output);
				  byte data[] = new byte[1024];// 缓冲字节数
				  int size = bis.read(data);
				  while (size != -1) {
				   bos.write(data, 0, size);
				   size = bis.read(data);
				  }
				 
				  // 关闭流
				  bis.close();
				  bos.flush();// 清空输出缓冲流
				  bos.close();
				  output.close();
			} catch (Exception e) {
				System.out.println("无法打开该格式");
				request.setAttribute("mess", "无法打开该格式文件");
				request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
			}
			 
			 
		}
		//下载
		else if(uri.equals("download.action")) {
			String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
			System.out.println("地址:"+path);
			//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
			String fileName = request.getParameter("downfilename");
			System.out.println("文件名:"+fileName);
			InputStream in = new FileInputStream(path+File.separator+fileName);
			//设置响应的方式为下载方式,filename为下载名称
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO8859_1"));
			//输出对象建立
			ServletOutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))>0) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();                        
			in.close();
		}
		//删除
		else if(uri.equals("documentdel.action")) {
			String[] ids = request.getParameterValues("documentIds");
			//用户权限
			String session_status = request.getParameter("session_stat");
			System.out.println("权限为"+session_status);
			int pageIndex = 1;
			Document entity = new Document();
			String Index = request.getParameter("pageIndex");
			if(Index!=null) {
				pageIndex = Integer.parseInt(Index);
			}
			String title = request.getParameter("title");
			if(title!=null&&!title.equals("")) {
				entity.setTitle(title);
			}
			List<User> userList = us.findAll();
			request.setAttribute("userList", userList);
			PageModel<Document> pageModel = dms.findPage(pageIndex, entity);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("document", entity);
			//System.out.println("id为："+ids[0]);
				if(ids!=null) {
					if("2".equals(session_status)) {
						for (int i = 0; i < ids.length; i++) {
							int delid = Integer.parseInt(ids[i]);
							Document dcm = dms.findById(delid);
							String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
							//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
							///HRM/WebContent/ueditor/jsp/upload
							System.out.println("真实路径为:"+path);
							String delfilename = dcm.getFilename();
							System.out.println("删除的文件名为:"+delfilename);
							File delfile  = new File(path);
							File[] files = delfile.listFiles();
							for(File dfile:files){
								if(dfile.getName().equals(delfilename)){
									if(dfile.delete()) {
										System.out.println("删除成功!");
									}else {
										System.out.println("删除失败..");
									}
								}
							}						
							//File delfile = new File(delfilename, path);						
						}
						dms.del(ids);
						request.setAttribute("mess", "删除成功!");
						request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "权限不足");
						request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
					}
				}
		}
		//修改查询
		else if(uri.equals("updateDcm.action")) {
			String id = request.getParameter("id");
			//System.out.println("获取到的用户id为"+id);
			if(id!=null) {
				Document dcmud = dms.findById(Integer.parseInt(id));
				if(dcmud!=null) {
					request.setAttribute("document", dcmud);
					request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
				}
			}
		}
		//修改
		else if(uri.equals("updateDocument.action")) {
			//String id = request.getParameter("id");
			//System.out.println("获取到的用户id为"+id);
			//String session_status = request.getParameter("session_stat");
			//if(id!=null&&!id.equals("")) 
				//Document dcmud = dms.findById(Integer.parseInt(id));
				//if("2".equals(session_status)) 
					//获取页面传输内容是否为二进制文件
					Document dcmud = null;
					if(ServletFileUpload.isMultipartContent(request)) {
						//创建一个FileItemFactory工厂 用于解析request
						FileItemFactory factory = new DiskFileItemFactory();
						//创建生成一个ServletFileUpload组件
						ServletFileUpload upload = new ServletFileUpload(factory);
						try {
							//通过该组件解析request中的内容 为多个文件项FileItem
							List<FileItem> list = upload.parseRequest(request);
							
							for (FileItem fi: list) {
								if("id".equals(fi.getFieldName())) {
									int id= Integer.parseInt(fi.getString("utf-8"));
									System.out.println("获取到的id为:"+id);
									dcmud = dms.findById(id);
									System.out.println("获取到的文件名为:"+dcmud.getTitle());
								}
								//判断每一项内容是否为普通表单数据
								if(fi.isFormField()) {
										//判断获取到的属性名称是否为我们需要的属性 getFieldName
										if("title".equals(fi.getFieldName())) {
											dcmud.setTitle(fi.getString("utf-8"));
											//获取对应的值
											//System.out.println("用户名为:"+fi.getString("utf-8"));
										}
										if("remark".equals(fi.getFieldName())) {
											dcmud.setRemark(fi.getString("utf-8"));
										}									
								}else {
									//获取上传文件地址(要上传的地址)  真实路径
									String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
									//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
									///HRM/WebContent/ueditor/jsp/upload
									System.out.println("真实路径为:"+path);
									//如果该路径不存在则创建
									File file = new File(path);
									if(!file.exists())
										file.mkdirs();
									//获取到文件名,并拼接在原有路径 getName
									String name = fi.getName();
									System.out.println("名为:"+name);
									String[] names = name.split("\\.");
									//再次存入name属性中
									dcmud.setFiletype(names[1]);
									name = names[0]+System.currentTimeMillis()+"."+names[1];
									dcmud.setFilename(name); 	//将文件名保存到数据库
									File newFile = new File(file, name);
									byte[] byData = new byte[(int) newFile.length()];
									dcmud.setFileBytes(byData);
									//将二进制数据写入该文件
									fi.write(newFile);
									System.out.println("上传成功!");
								}							
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(dcmud!=null) {
							if(dms.update(dcmud)) {
								request.setAttribute("document", dcmud);
								request.setAttribute("message", "修改成功!");
								request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
							}else {
								request.setAttribute("document", dcmud);
								request.setAttribute("message", "上传失败");
								request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
							}
						}
					}
		}
		//添加页面
		else if(uri.equals("documentadd.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		}
		//添加
		else if(uri.equals("documentaddsave.action")){
			Document dcm = new Document();
			User operateuser = new User();
			//获取页面传输内容是否为二进制文件
			if(ServletFileUpload.isMultipartContent(request)) {
				//创建一个FileItemFactory工厂 用于解析request
				FileItemFactory factory = new DiskFileItemFactory();
				//创建生成一个ServletFileUpload组件
				ServletFileUpload upload = new ServletFileUpload(factory);
				boolean flag = false;
				try {
					//@SuppressWarnings("unchecked")  //忽略警告
					//通过该组件解析request中的内容 为多个文件项FileItem
					List<FileItem> list = upload.parseRequest(request);
					//循环每一项内容
					if(list!=null) {
						for (FileItem fi: list) {
							//判断每一项内容是否为普通表单数据
							if(fi.isFormField()) {
								//判断获取到的属性名称是否为我们需要的属性 getFieldName
								if("title".equals(fi.getFieldName())) {
									dcm.setTitle(fi.getString("utf-8"));
									//获取对应的值
									//System.out.println("用户名为:"+fi.getString("utf-8"));
								}
								if("remark".equals(fi.getFieldName())) {
									dcm.setRemark(fi.getString("utf-8"));
								}
								//获取当前用户ID
								if("session_id".equals(fi.getFieldName())) {
									int userid = Integer.parseInt(fi.getString("utf-8"));
									operateuser.setId(userid);
									dcm.setUSER_ID(userid);
								}
								
							}else {
								//获取上传文件地址(要上传的地址)  真实路径
								String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
								//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
								///HRM/WebContent/ueditor/jsp/upload
								System.out.println("真实路径为:"+path);
								//如果该路径不存在则创建
								File file = new File(path);
								if(!file.exists())
									file.mkdirs();
								//获取到文件名,并拼接在原有路径 getName
								String name = fi.getName();
								System.out.println("名为:"+name);
								String[] names = name.split("\\.");
								//再次存入name属性中
								dcm.setFiletype(names[1]);
								name = names[0]+System.currentTimeMillis()+"."+names[1];
								dcm.setFilename(name); 	//将文件名保存到数据库
								File newFile = new File(file, name);
								byte[] byData = new byte[(int) newFile.length()];
								dcm.setFileBytes(byData);
								//将二进制数据写入该文件
								fi.write(newFile);
								System.out.println("上传成功!");
								flag = true;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag) {
					System.out.println("表单数据提交成功,存入数据库");
					if(dms.save(dcm)) {
						response.sendRedirect(request.getContextPath()+"/documentlist.action");
					}else {
						request.setAttribute("message", "上传失败");
						request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
					}
				}
			}else {
				String name = request.getParameter("title");
				System.out.println("无上传文件:"+name);
			}
			//request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		}
	}

}
