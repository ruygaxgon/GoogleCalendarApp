<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' href='javascript/fullcalendar/fullcalendar.css' />
<script src='javascript/fullcalendar/lib/jquery.min.js'></script>
<script src='javascript/fullcalendar/lib/moment.min.js'></script>
<script src='javascript/fullcalendar/fullcalendar.js'></script>
<script src='javascript/jsCode.js'></script>
<title>Servlet Example Calendar</title>
</head>
<body>
	<script type="text/javascript">
	
	$(document).ready(function() {
		/**
		* Call this function locally as it does not need the REST url,
		* it gets the value from the servlet request
		**/
		$('#calendarServlet').fullCalendar({
		    events: ${requestScope.events}
		});
	});
	</script>
	<p>Type an email and click share to send events via email / こちらメールを記入してシェアボタンを押しイベントをメールで送れる </p>
	<input id='emails' placeholder='xyz@mail.com, abc@mail.com' size="30"/>
	<button id='shareButton'>Share　/　シェア</button>
	<br/>
	<div id='calendarServlet' style='width: 60%; height: 60%;'></div>
</body>
</html>