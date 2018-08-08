<%-- 
    Document   : JSPInscri
    Created on : 28-juil.-2018, 19:20:24
    Author     : BJ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.*" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <form method="POST" action="Connexion">
                <% String [] tabLangues = (String[])request.getAttribute("listeLangues"); %>
                Login : <input type="text" name="login" value="adriana">
                <br>
                Password : <input type="password" name="password" value="azerty">
                <br>
                <input type ="email" name="email" value="inpresmail@gmail.com">
                <br>
                <input type="text" name="reservation" value="xxx">
                <br>
                <select name="langues">                    
                    <option name="langue" value="<%=tabLangues[0]%>"><%=tabLangues[0]%></option>
                    <option name="langue" value="<%=tabLangues[1]%>"><%=tabLangues[1]%></option>
                </select>
                <input type="checkbox" name ="newclient"> Nouveau client
                <br>
                <input type="submit" value="Connection" name="button_sub"> 
            </form>
            
        </div>
    </body>
</html>
