<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户展示</title>
</head>
<body>

<table>
    <thead>
        <tr>
            <td>姓名</td>
            <td>联系人</td>
            <td>电话号码</td>
            <td>邮件</td>
            <td>备注</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listCustomer}" var="oneCustomer">
        <tr>
            <td>${oneCustomer.name}</td>
            <td>${oneCustomer.contact}</td>
            <td>${oneCustomer.telphone}</td>
            <td>${oneCustomer.email}</td>
            <td>${oneCustomer.remark}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>