<%-- 
    Document   : JSPInit
    Created on : 19-nov.-2017, 13:57:44
    Author     : Fanny Gillet
--%>
<%@page language="java"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.*" %>
<!DOCTYPE html>

<% ResourceBundle resource = ResourceBundle.getBundle("//bundles//location_"+request.getAttribute("langue")); 

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>INPRES-Airport</title>
    </head>
    <body>
        <h2><%=resource.getString("destination")%></h2>
       
             <form method="POST" action="Connexion"> 
                 <select name="selectvol">
            <%--<%! ;  %>--%>
            <%String[] tb =  (String[])request.getAttribute("destination"); 
                
                for(int i=0; i<tb.length; i++)
                {%>
                <option id = "
                        <%= tb[i]%>
                        " >
                    <%=tb[i]%>
                </option> 
               <% }
            
            %>
            
        </select>
                <input type="submit" value="<%=resource.getString("boutonRechercher")%>"  name="button_sub"> 
            </form>
    </body>
</html>
