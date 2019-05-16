<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="JSTL_C"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>result</title>
	</head>
	<body>
		<JSTL_C:forEach items="${pages}" var="page">
			<a href="${page.url}" target="_blank">${page.title}<br>
			</a>
		${page.text}<P>
				${page.url}
			<p>
		</JSTL_C:forEach>
		<a href="showResult?pageNum=${pageNum-1}">pre</a> 
			current:${pageNum}
			all:${allPageNum}
		<a href="showResult?pageNum=${pageNum+1}">next</a>
	</body>
</html>
