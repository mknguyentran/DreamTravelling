/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DiscountCodeDAO;
import dto.AccountDTO;
import dto.CartDTO;
import dto.DiscountCodeDTO;
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
public class ApplyDiscountCodeController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ApplyDiscountCodeController.class);
    private static final String SUCCESS = "LoadCartController";
    private static final String ERROR = "error.jsp";

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
        HttpSession session = request.getSession();
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
        CartDTO cart = (CartDTO) session.getAttribute("CART");
        DiscountCodeDTO discountCode;
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String url = ERROR;
        try {
            if (cart != null) {
                if (action != null) {
                    if (action.equals("add")) {
                        if (id != null) {
                            if (!id.isEmpty()) {
                                String userEmail = account.getEmail();
                                DiscountCodeDAO dDAO = new DiscountCodeDAO();
                                int discountCodeStatus = dDAO.checkDiscountCode(id, userEmail);
                                if (discountCodeStatus == dDAO.IS_AVAILABLE) {
                                    discountCode = dDAO.getDiscountCode(id);
                                    cart.setDiscountCode(discountCode);
                                    session.setAttribute("CART", cart);
                                } else if (discountCodeStatus == dDAO.IS_UNAVAILABLE) {
                                    request.setAttribute("DC_ERROR", "Discount code not available");
                                } else if (discountCodeStatus == dDAO.IS_EXPIRED) {
                                    request.setAttribute("DC_ERROR", "Discount code expired");
                                } else if (discountCodeStatus == dDAO.IS_USED) {
                                    request.setAttribute("DC_ERROR", "You have used this discount code before");
                                }
                            } else {
                                request.setAttribute("DC_ERROR", "Please enter a discount code");
                            }
                        }
                    } else if(action.equals("remove")){
                        cart.setDiscountCode(null);
                    }
                }
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Cart is not available");
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
