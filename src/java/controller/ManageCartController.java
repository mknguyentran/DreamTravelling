/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TourDAO;
import dto.AccountDTO;
import dto.CartDTO;
import java.io.IOException;
import java.util.Map;
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
public class ManageCartController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ManageCartController.class);
    private static final String TOUR_DETAIL = "LoadTourController";
    private static final String CART = "LoadCartController";
    private static final String ERROR = "/error.jsp";
    private static final String HOME = "SearchTourController";

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
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            String action = request.getParameter("action");
            if (account != null) {
                if (account.getRole().equals("user")) {
                    String sentFrom = request.getParameter("sentFrom");
                    String id = request.getParameter("id");
                    CartDTO cart = (CartDTO) session.getAttribute("CART");
                    if (action != null) {
                        if (action.equals("add")) {
                            TourDAO tDAO = new TourDAO();
                            if (cart == null) {
                                cart = new CartDTO(account.getEmail());
                            }
                            if (cart.getCart().containsKey(id)) {
                                int availableAmount = tDAO.getAvailableAmount(id);
                                if (cart.getCart().get(id) + 1 <= availableAmount) {
                                    cart.add(id);
                                } else {
                                    cart.getCart().put(id, availableAmount);
                                    request.setAttribute("ALERT", "You have added the maximum amount of ticket for this tour");
                                }
                            } else {
                                cart.add(id);
                            }
                            session.setAttribute("CART", cart);
                        } else {
                            if (cart != null) {
                                if (action.equals("decrease")) {
                                    cart.decrease(id);
                                } else if (action.equals("remove")) {
                                    cart.remove(id);
                                }
                            }
                        }
                        url = HOME;
                        if (sentFrom != null) {
                            if (sentFrom.equals("tourDetail")) {
                                url = TOUR_DETAIL;
                            }
                            if (sentFrom.equals("cart")) {
                                url = CART;
                            }
                        }
                        for (Map.Entry<String, Integer> entry : cart.getCart().entrySet()) {
                            logger.debug(entry.getKey() + " - " + entry.getValue());
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "Invalid access to shopping feature - Your account is not permitted to use this feature");
                }
            } else {
                request.setAttribute("ERROR", "Invalid access to shopping feature - You are not logged in");
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
