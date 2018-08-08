<%-- 
    Document   : JSPCaddie
    Created on : 19-nov.-2017, 18:14:53
    Author     : Fanny Gillet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.*" %>
<!DOCTYPE html>

<% ResourceBundle resource = ResourceBundle.getBundle("//bundles//location_"+request.getAttribute("langue")); 

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inpres_airport</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    </head>
    <body>
        <h2><%=resource.getString("choixBillet")%></h2>
        <div>
            <form method="POST" action="Connexion">
        <%
                String[][] tb =  (String[][])request.getAttribute("infotab"); 
                %>
                <%--<%= //tb.length%>--%>
                <%
          for(int i=0; i<tb.length; i++)
                {%>
                <div>
                    <p name="id"><%=resource.getString("numVol")%> : <%=tb[i][0]%></p>
                    <p name="destination"><%=resource.getString("destination")%> : <%=tb[i][1]%></p>
                    <p name ="billet"><%=resource.getString("billetsDisponibles")%> : <%=tb[i][2]%></p>
                    <p name ="dateDepart"><%=resource.getString("dateDepart")%> : <%=tb[i][3]%></p>
                    <p><%=resource.getString("dateMort")%> : <%=tb[i][4]%></p>
                    <input type="number" value="0" >
                    <input type="submit"  value ="<%=resource.getString("validation")%>" name="button_sub" class="btn btn-default glyphicon glyphicon-star">
                    <p>-------------------------------------</p>
                </div> 
               <% }
            
            %>
            </form>
        </div>
        </body>
</html>
