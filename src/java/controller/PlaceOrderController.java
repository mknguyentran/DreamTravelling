/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDAO;
import dao.TourDAO;
import dto.AccountDTO;
import dto.CartDTO;
import dto.OrderDTO;
import java.io.IOException;
import java.sql.Timestamp;
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
public class PlaceOrderController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(PlaceOrderController.class);
    private static final String SUCCESS = "SearchTourController";
    private static final String CART = "LoadCartController";
    private static final String ERROR = "/error.jsp";

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            if (cart != null && account != null) {
                TourDAO tDAO = new TourDAO();
                if (tDAO.isSufficient(cart.getCart())) {
                    OrderDAO oDAO = new OrderDAO();
                    int status = oDAO.getStatusID("pending");
                    String discountCode = null;
                    if (cart.getDiscountCode() != null) {
                        discountCode = cart.getDiscountCode().getId().toUpperCase();
                    }
                    String email = account.getEmail();
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    OrderDTO order = new OrderDTO(email, discountCode, now, status);
                    int orderID = oDAO.createOrder(order);
                    if (orderID > 0) {
                        if (oDAO.addOrderDetail(orderID, cart.getCart())) {
                            url = SUCCESS;
                            session.removeAttribute("CART");
                            request.setAttribute("SUCCESS_ALERT", "Order Placed Successfully");
                        }
                    }
                } else {
                    url = CART;
                    request.setAttribute("ALERT", "Order can not be placed because some item in you cart has been sold out or is insufficient! Your cart has been updated to reflect the changes!");
                }
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
