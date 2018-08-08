/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import BeanDataBase.BeanConnection;
import BeanDataBase.BeanMySql;
import TICKMAP.ReponseTICKMAP;
import TICKMAP.RequeteTICKMAP;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import Utils.Utils;
import classObj.Personne;
import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
/**
 *
 * @author Fanny Gillet
 */
@WebServlet(name = "Connexion", urlPatterns = {"/Connexion"})
public class Connexion extends HttpServlet {
    
    BeanConnection bc = null;
    ResultSet rs = null;
    private Utils u;
    private Socket cliSock;
    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ReponseTICKMAP rep;
    private RequeteTICKMAP req;
    
    private String tabLangues[];
    private List listeLangues;
    private ResourceBundle resource;
    private Personne perso;
    
    public void init (ServletConfig config) throws ServletException
    {
        super.init(config);
        
        bc = new BeanMySql("localhost", "3306", "root", "", "mysql");
        u = new Utils();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, MessagingException, ClassNotFoundException {
        
        System.out.println("request with parameter : " + request.getParameter("button_sub"));
        System.out.println("request with header : " + request.getHeader("button_sub"));
        System.out.println("request with method : " + request.getServletPath());
        System.out.println(" next ");
if(request.getParameter("button_sub").compareTo("Site") == 0)
        {
//            tabLangues = new String[];
            int i = 0, rowcount = 0;
            bc.CreateConnection();
            rs = bc.executeQuery("select * from bd_compta.langues");
            if (rs.last()) 
            {
                rowcount = rs.getRow();
                rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            tabLangues = new String[rowcount];
            while(rs.next())
            {
                tabLangues[i] = rs.getString("langues");
                i++;
            }
            for(int j = 0 ; j > tabLangues.length ; j++)
            {
                System.out.println("langues : " + tabLangues[j]);
            }
            request.setAttribute("listeLangues", tabLangues);
            request.getRequestDispatcher("/JSPInscri.jsp").forward(request, response);
        }            
if(request.getParameter("button_sub").compareTo("Connection") == 0)
        {       
            bc.CreateConnection();
            
            response.setContentType("text/html;charset=UTF-8");

                if(request.getParameter("newclient") != null && request.getParameter("newclient").compareTo("on")==0)
                {
                    String re = "insert into jdbc2.clients (login, password, langues, mail) values ('"+request.getParameter("login")+"', '"+request.getParameter("password")+"', '"+request.getParameter("langues")+"', '"+request.getParameter("email")+"')";
//                    String re = "insert into jdbc2.clients (login, password, langues, mail) values ('azer', '"+request.getParameter("password")+"', "+request.getParameter("langues")+", "+request.getParameter("email")+"')";
                    bc.executeUpdate(re);
                }
                    rs = bc.executeQuery("select login, password, langues, mail from jdbc2.clients where login like '"+request.getParameter("login")+"'");                   
                
                    rs.next();
                    perso = new Personne(rs.getString("login"), rs.getString("password"), rs.getString("langues"), rs.getString("mail"));
                    resource = ResourceBundle.getBundle("//bundles//location_"+perso.getLangue()); 
                    boolean exist = false;
                    if(rs.getString("login") != null)
                    {
                        if(rs.getString("password").compareTo(request.getParameter("password")) == 0)
                        {
                            exist = true;
                            rs = bc.executeQuery("select count(distinct destination)as total from jdbc2.vols");
                            rs.next();
                            String[] tab;

                            if(rs.getInt("total") != 0)
                            {
                                tab = new String[rs.getInt("total")];
                                int cpt = 0;
                                rs = bc.executeQuery("select distinct destination from jdbc2.vols");
                                while(rs.next())
                                {
                                    tab[cpt] = rs.getString("destination");
                                    cpt++;
                                }
                            }
                            else
                            {
                                tab = new String[1];
                                tab[0] = "NODEST";
                            }
                            request.setAttribute("langue", perso.getLangue());
                            request.setAttribute("destination", tab);
                            request.getRequestDispatcher("/JSPInit.jsp").forward(request, response);
        //                        out.println("<h1>Connexion réussie</h1>");
                        }
                        else
                        {
                            exist = false;
                        }

                    }
                    else
                    {
    //                    out.println("<h1>Login inexistant</h1>");
                            exist = false;
                    }

                    if(exist == false)
                    {
                        try (PrintWriter out = response.getWriter()) {
                            out.println("<!DOCTYPE html>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Erreur login</title>");            
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<h1>Mot de passe incorrect</h1>");
                            out.println("</body>");
                            out.println("</html>");
                        }                        
                    }
                }
if(resource.getString("boutonRechercher").equals(request.getParameter("button_sub")))
                {
                    rs =null;
                    rs = bc.executeQuery("select count(*)as total from jdbc2.vols where upper(destination) like upper('"+request.getParameter("selectvol")+"')");
                    rs.next();
                    System.out.println("nb retourné : " +rs.getInt("total"));
                    String[][] tab = new String[rs.getInt("total")][5];
                    System.out.println("nb retourné : " +rs.getInt("total"));
                    rs = bc.executeQuery("select idvols, destination, nbbillets, DATE_FORMAT(datedepart, '%d/%m/%y - %H:%i') as datedepart, DATE_FORMAT(dateprevue, '%d/%m/%y - %H:%i') as dateprevue from jdbc2.vols where upper(destination) like upper('"+request.getParameter("selectvol")+"')");
                    int cpt=0;
                    while(rs.next())
                    {
                        tab[cpt][0] = rs.getString("idvols");
                        tab[cpt][1] = rs.getString("destination");
                        tab[cpt][2] = rs.getString("nbbillets");
                        tab[cpt][3] = rs.getString("datedepart");
                        tab[cpt][4] = rs.getString("dateprevue");
                        cpt++;
                    }
                    request.setAttribute("langue", perso.getLangue());
                    request.setAttribute("infotab", tab);
                    request.getRequestDispatcher("/JSPCaddie.jsp").forward(request, response);
                }
if(resource.getString("validation").equals(request.getParameter("button_sub")))
        {
            System.out.println("Je suis dans le payement");
             
//            cliSock = new Socket("127.0.0.1", 42000);
//            req = new RequeteTICKMAP(RequeteTICKMAP.REQ_PAIEMENT_WEB, "requete");
//            oos = new ObjectOutputStream(cliSock.getOutputStream());
//            oos.writeObject(req); oos.flush();
//                
//            ois = new ObjectInputStream(cliSock.getInputStream());
//            rep = (ReponseTICKMAP)ois.readObject();
//            
//            System.out.println("rep : " + rep.getChargeUtile());
//            u.envoieMail("inpresmail@gmail.com");
        }
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
