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
		//System.out.println("uri?????????"+uri);
		//System.out.println("url?????????"+url);
		//url?????????http://localhost:8080/HRM/documentlist.action
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
		//??????
		else if(uri.equals("preview.action")) {
			 // ?????????
			  InputStream is = null;
			  // ???????????????
			  BufferedInputStream bis = null;
			  // ???????????????
			  OutputStream output = null;
			  // ???????????????
			  BufferedOutputStream bos = null;
			  // 1.??????????????????
			  request.setCharacterEncoding("UTF-8");
			  // 2.???????????????????????????
			  response.setCharacterEncoding("UTF-8");
			  // 3.???????????????????????????????????????
			  String fileName = request.getParameter("downfilename");
			  // 4.??????response
			  response.reset();
			  // 5.????????????????????????
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
				  System.out.println("??????:"+path);
				  System.out.println("?????????:"+fileName);
				  // 7.???????????????????????????response??????????????????????????????
				  is = new FileInputStream(path+File.separator+fileName);
				  bis = new BufferedInputStream(is);
				  output = response.getOutputStream();
				  bos = new BufferedOutputStream(output);
				  byte data[] = new byte[1024];// ???????????????
				  int size = bis.read(data);
				  while (size != -1) {
				   bos.write(data, 0, size);
				   size = bis.read(data);
				  }
				 
				  // ?????????
				  bis.close();
				  bos.flush();// ?????????????????????
				  bos.close();
				  output.close();
			} catch (Exception e) {
				System.out.println("?????????????????????");
				request.setAttribute("mess", "???????????????????????????");
				request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
			}
			 
			 
		}
		//??????
		else if(uri.equals("download.action")) {
			String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
			System.out.println("??????:"+path);
			//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
			String fileName = request.getParameter("downfilename");
			System.out.println("?????????:"+fileName);
			InputStream in = new FileInputStream(path+File.separator+fileName);
			//????????????????????????????????????,filename???????????????
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO8859_1"));
			//??????????????????
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
		//??????
		else if(uri.equals("documentdel.action")) {
			String[] ids = request.getParameterValues("documentIds");
			//????????????
			String session_status = request.getParameter("session_stat");
			System.out.println("?????????"+session_status);
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
			//System.out.println("id??????"+ids[0]);
				if(ids!=null) {
					if("2".equals(session_status)) {
						for (int i = 0; i < ids.length; i++) {
							int delid = Integer.parseInt(ids[i]);
							Document dcm = dms.findById(delid);
							String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
							//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
							///HRM/WebContent/ueditor/jsp/upload
							System.out.println("???????????????:"+path);
							String delfilename = dcm.getFilename();
							System.out.println("?????????????????????:"+delfilename);
							File delfile  = new File(path);
							File[] files = delfile.listFiles();
							for(File dfile:files){
								if(dfile.getName().equals(delfilename)){
									if(dfile.delete()) {
										System.out.println("????????????!");
									}else {
										System.out.println("????????????..");
									}
								}
							}						
							//File delfile = new File(delfilename, path);						
						}
						dms.del(ids);
						request.setAttribute("mess", "????????????!");
						request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
					}else {
						request.setAttribute("mess", "????????????");
						request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
					}
				}
		}
		//????????????
		else if(uri.equals("updateDcm.action")) {
			String id = request.getParameter("id");
			//System.out.println("??????????????????id???"+id);
			if(id!=null) {
				Document dcmud = dms.findById(Integer.parseInt(id));
				if(dcmud!=null) {
					request.setAttribute("document", dcmud);
					request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
				}
			}
		}
		//??????
		else if(uri.equals("updateDocument.action")) {
			//String id = request.getParameter("id");
			//System.out.println("??????????????????id???"+id);
			//String session_status = request.getParameter("session_stat");
			//if(id!=null&&!id.equals("")) 
				//Document dcmud = dms.findById(Integer.parseInt(id));
				//if("2".equals(session_status)) 
					//????????????????????????????????????????????????
					Document dcmud = null;
					if(ServletFileUpload.isMultipartContent(request)) {
						//????????????FileItemFactory?????? ????????????request
						FileItemFactory factory = new DiskFileItemFactory();
						//??????????????????ServletFileUpload??????
						ServletFileUpload upload = new ServletFileUpload(factory);
						try {
							//?????????????????????request???????????? ??????????????????FileItem
							List<FileItem> list = upload.parseRequest(request);
							
							for (FileItem fi: list) {
								if("id".equals(fi.getFieldName())) {
									int id= Integer.parseInt(fi.getString("utf-8"));
									System.out.println("????????????id???:"+id);
									dcmud = dms.findById(id);
									System.out.println("????????????????????????:"+dcmud.getTitle());
								}
								//????????????????????????????????????????????????
								if(fi.isFormField()) {
										//???????????????????????????????????????????????????????????? getFieldName
										if("title".equals(fi.getFieldName())) {
											dcmud.setTitle(fi.getString("utf-8"));
											//??????????????????
											//System.out.println("????????????:"+fi.getString("utf-8"));
										}
										if("remark".equals(fi.getFieldName())) {
											dcmud.setRemark(fi.getString("utf-8"));
										}									
								}else {
									//????????????????????????(??????????????????)  ????????????
									String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
									//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
									///HRM/WebContent/ueditor/jsp/upload
									System.out.println("???????????????:"+path);
									//?????????????????????????????????
									File file = new File(path);
									if(!file.exists())
										file.mkdirs();
									//??????????????????,???????????????????????? getName
									String name = fi.getName();
									System.out.println("??????:"+name);
									String[] names = name.split("\\.");
									//????????????name?????????
									dcmud.setFiletype(names[1]);
									name = names[0]+System.currentTimeMillis()+"."+names[1];
									dcmud.setFilename(name); 	//??????????????????????????????
									File newFile = new File(file, name);
									byte[] byData = new byte[(int) newFile.length()];
									dcmud.setFileBytes(byData);
									//?????????????????????????????????
									fi.write(newFile);
									System.out.println("????????????!");
								}							
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(dcmud!=null) {
							if(dms.update(dcmud)) {
								request.setAttribute("document", dcmud);
								request.setAttribute("message", "????????????!");
								request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
							}else {
								request.setAttribute("document", dcmud);
								request.setAttribute("message", "????????????");
								request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
							}
						}
					}
		}
		//????????????
		else if(uri.equals("documentadd.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		}
		//??????
		else if(uri.equals("documentaddsave.action")){
			Document dcm = new Document();
			User operateuser = new User();
			//????????????????????????????????????????????????
			if(ServletFileUpload.isMultipartContent(request)) {
				//????????????FileItemFactory?????? ????????????request
				FileItemFactory factory = new DiskFileItemFactory();
				//??????????????????ServletFileUpload??????
				ServletFileUpload upload = new ServletFileUpload(factory);
				boolean flag = false;
				try {
					//@SuppressWarnings("unchecked")  //????????????
					//?????????????????????request???????????? ??????????????????FileItem
					List<FileItem> list = upload.parseRequest(request);
					//?????????????????????
					if(list!=null) {
						for (FileItem fi: list) {
							//????????????????????????????????????????????????
							if(fi.isFormField()) {
								//???????????????????????????????????????????????????????????? getFieldName
								if("title".equals(fi.getFieldName())) {
									dcm.setTitle(fi.getString("utf-8"));
									//??????????????????
									//System.out.println("????????????:"+fi.getString("utf-8"));
								}
								if("remark".equals(fi.getFieldName())) {
									dcm.setRemark(fi.getString("utf-8"));
								}
								//??????????????????ID
								if("session_id".equals(fi.getFieldName())) {
									int userid = Integer.parseInt(fi.getString("utf-8"));
									operateuser.setId(userid);
									dcm.setUSER_ID(userid);
								}
								
							}else {
								//????????????????????????(??????????????????)  ????????????
								String path = request.getServletContext().getRealPath("/ueditor/jsp/upload");
								//String path = "D:\\workspace\\Day0329\\WebContent\\pic";
								///HRM/WebContent/ueditor/jsp/upload
								System.out.println("???????????????:"+path);
								//?????????????????????????????????
								File file = new File(path);
								if(!file.exists())
									file.mkdirs();
								//??????????????????,???????????????????????? getName
								String name = fi.getName();
								System.out.println("??????:"+name);
								String[] names = name.split("\\.");
								//????????????name?????????
								dcm.setFiletype(names[1]);
								name = names[0]+System.currentTimeMillis()+"."+names[1];
								dcm.setFilename(name); 	//??????????????????????????????
								File newFile = new File(file, name);
								byte[] byData = new byte[(int) newFile.length()];
								dcm.setFileBytes(byData);
								//?????????????????????????????????
								fi.write(newFile);
								System.out.println("????????????!");
								flag = true;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag) {
					System.out.println("????????????????????????,???????????????");
					if(dms.save(dcm)) {
						response.sendRedirect(request.getContextPath()+"/documentlist.action");
					}else {
						request.setAttribute("message", "????????????");
						request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
					}
				}
			}else {
				String name = request.getParameter("title");
				System.out.println("???????????????:"+name);
			}
			//request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		}
	}

}
