<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ include file="../taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>人事管理系统 ——文档管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx }/css/css.css" type="text/css" rel="stylesheet" />
	<link href="${ctx }/css/pager.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
	<link href="${ctx }/js/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />  
	<script src="${ctx }/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx }/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx }/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="${ctx }/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	function toPage(pageIndex)
	{
		$("#pageIndex").attr("value",pageIndex);
	    $("#documentform").attr("action", "${ctx}/documentlist.action");
		$("#documentform").submit();
	}
	
	function pagerJump()
	{
		var page_size=$('#pager_jump_page_size').val();
			
			var regu = /^[1-9]\d*$/;
				
			if (!regu.test(page_size)||page_size < 1 || page_size >${pageModel.totalPageSum})
			{
				alert('请输入[1- ${pageModel.totalPageSum}]之间的页码！');	
			}else
			{
	 		    $("#pageIndex").attr("value",page_size);
		        $("#documentform").attr("action", "${ctx}/documentlist.action");
		    	$("#documentform").submit();
			}
	}
	
	    $(function(){
	    	
	    	/** 获取上一次选中的部门数据 */
	  	   var boxs  = $("input[type='checkbox'][id^='box_']");
	  	   
	  	   /** 给全选按钮绑定点击事件  */
	 	   $("#checkAll").click(function(){
	 	       // this是checkAll  this.checked是true
	 	       // 所有数据行的选中状态与全选的状态一致
	 	       boxs.attr("checked",this.checked);
	 	   })
	 	   
	 	    /** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
	 	    $("tr[id^='data_']").hover(function(){
	 	    	$(this).css("backgroundColor","#eeccff");
	 	    },function(){
	 	    	$(this).css("backgroundColor","#ffffff");
	 	    })

	 	    	
	  	   /** 删除员工绑定点击事件 */
	  	   $("#delete").click(function(){
	  		   /** 获取到用户选中的复选框  */
	  		   var checkedBoxs = boxs.filter(":checked");
	  		   if(checkedBoxs.length < 1){
	  			   alert("请选择一个需要删除的用户！");
	  		   }else{
	  			    $("#documentform").attr("action", "${ctx}/documentdel.action");
	  		 		$("#documentform").submit();
	  		   }
	  	   })
	 		   
	 	   
	    	
	    	/** 下载文档功能 */
	    	$("a[id^='down_']").click(function(){
	    		/** 得到需要下载的文档的id */
	    		var id = this.id.replace("down_","");
	    		/** 下载该文档 */
	    		window.location = "${ctx}/documentdownload.action?id="+id;
	    	})
	    	
	    	
	    	
	    })
	    
/* 	    function down(id){
	    	$("a[id='down_"+id+"']").trigger("click");
	    } */
	    
	</script>
</head>
<body>
	<!-- 导航 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr><td height="10"></td></tr>
	  <tr>
	    <td width="15" height="32"><img src="${ctx }/images/main_locleft.gif" width="15" height="32"></td>
		<td class="main_locbg font2"><img src="${ctx }/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：文档管理 &gt; 文档查询</td>
		<td width="15" height="32"><img src="${ctx }/images/main_locright.gif" width="15" height="32"></td>
	  </tr>
	</table>
	<form name="documentform" method="post" id="documentform" action="${ctx }/documentlist.action">
	<!-- 用户权限 -->
		<input type="hidden" name="session_stat" value="${sessionScope.user_session.status}">
	 <!-- 配置pageIndex的隐藏域 -->
		<input type="hidden" name="pageIndex" value="${pageModel.pageIndex}" id="pageIndex">
	<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
	  <!-- 查询区  -->
	  <tr valign="top">
	    <td height="30">
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr>
			  <td class="fftd">
			  	
			  	
			  	
				  
				    <table width="100%" border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td class="font3">
					    	标题：<input type="text" name="title" />
					    	<input type="submit"  value="搜索"/>
					    	<input type="button" id="delete" value="删除"><span style="color: red;">${mess }</span>
					    </td>
					  </tr>
					</table>
		
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	  
	  <!-- 数据展示区 -->
	  <tr valign="top">
	    <td height="20">
		  <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
		    <tr class="main_trbg_tit" align="center">
		      <td><input type="checkbox" id="checkAll" ></td>
			  <td>标题</td>
			  <td>创建时间</td>
			  <td>创建人</td>
			  <td>描述</td>
			  <td>预览</td>
			  <td>操作</td>
			  <td>下载</td>
			</tr>
			<c:forEach items="${pageModel.list}" var="document" varStatus="stat">
				<tr class="main_trbg" align="center" id="data_${stat.index}">
					<td><input type="checkbox" id="box_${stat.index}" value="${document.id}" name="documentIds"></td>
					 <td>${document.title }</td>
					 <td>
					  	<f:formatDate value="${document.createDate}" 
								type="date" dateStyle="long"/>
					  </td>
					  <td><c:forEach items="${requestScope.userList }" var="user">
						   		<c:choose>
						   		<c:when test="${user.id==document.USER_ID}">
			    					<option value="${user.id}" selected="selected">${user.username}</option>
			    				</c:when>
			    				<c:otherwise></c:otherwise>
			    				</c:choose>
					    	</c:forEach>
					   </td>
					  <td>${document.remark }</td>
					  <td align="center"  width="40px;"><a href="${ctx }/preview.action?downfilename=${document.filename}">
					  <img width="20" height="20" title="预览" src="${ctx }/images/prev.gif"/></a></td>
					 <td align="center" width="40px;"><a href="${ctx }/updateDcm.action?id=${document.id}">
							<img title="修改" src="${ctx }/images/update.gif"/></a>
					  </td>
					  <td align="center"  width="40px;"><a href="${ctx }/download.action?downfilename=${document.filename}">
							<img width="20" height="20" title="下载" src="${ctx }/images/downLoad.png"/></a>
					  </td>
				</tr>
			</c:forEach>
			 

		  </table>
		</td>
	  </tr>
	  <!-- 分页标签 -->
	  <tr valign="top">
	  <td align="center" class="font3">
	  <%@include file="/page/page.jsp"%>
	  </td></tr>
	</table>
	<div style="height:10px;"></div>
	</form>
</body>
</html>