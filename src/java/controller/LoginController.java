/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import dto.AccountDTO;
import dto.errorObject.LoginErrorObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utilities.Logging;

/**
 *
 * @author Mk
 */
public class LoginController extends HttpServlet {

    private static final String SUCCESS = "SearchTourController";
    private static final String INVALID = "login.jsp";
    private static Logger logger = Logger.getLogger(LoginController.class);

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID;
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        boolean loginWithFB = request.getParameter("loginWithFB") != null;
        try {
            if (action != null) {
                HttpSession session = request.getSession();
                LoginErrorObject errorObj = new LoginErrorObject();
                if (action.equals("login")) {
                    AccountDTO account = null;
                    AccountDAO aDAO = new AccountDAO();
                    if (loginWithFB) {
                        if (aDAO.isExisted(email)) {
                            account = aDAO.login(email);
                        } else {
                            String name = request.getParameter("name");
                            Logging.logDebug(logger, "name", name);
                            account = new AccountDTO(email, name, null, password);
                            if (aDAO.createAccount(account)) {
                                account = aDAO.login(email);
                            }
                        }
                    } else if (email != null && password != null) {
                        account = aDAO.login(email, password);
                    }
                    if (account != null) {
                        session.setAttribute("ACCOUNT", account);
                        url = SUCCESS;
                    } else {
                        errorObj.setMainError("Email or Password is invalid");
                        request.setAttribute("ERROR", errorObj);
                    }

                } else if (action.equals("logout")) {
                    session.invalidate();
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            Logging.logError(logger, e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
