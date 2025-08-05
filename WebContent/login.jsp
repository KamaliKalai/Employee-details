<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h2>Login</h2>
<form method="post" action="login">
  Username: <input type="text" name="username" /><br/>
  Password: <input type="password" name="password" /><br/>
  <input type="submit" value="Login"/>
</form>
<c:if test="${not empty error}">
  <font color="red">${error}</font>
</c:if>
</body></html>
