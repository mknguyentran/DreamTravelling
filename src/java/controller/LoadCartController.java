/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TourDAO;
import dto.CartDTO;
import dto.TourDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utilities.Convert;
import utilities.Logging;

/**
 *
 * @author Mk
 */
public class LoadCartController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoadCartController.class);
    private static final String SUCCESS = "/user/cart.jsp";

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
        String url = SUCCESS;
        try {
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart != null) {
                String id;
                int amount;
                TourDTO tour = null;
                TourDAO tDAO = new TourDAO();
                ArrayList<TourDTO> cartList = new ArrayList<>();
                float total = 0, discountedTotal = 0;
                for (Map.Entry<String, Integer> entry : cart.getCart().entrySet()) {
                    id = entry.getKey();
                    amount = entry.getValue();
                    tour = tDAO.loadTourForCart(id);
                    int availableAmount = tDAO.getAvailableAmount(id);
                    if (amount <= availableAmount) {
                        tour.setAmount(amount);
                    } else {
                        tour.setAmount(availableAmount);
                        cart.getCart().put(id, availableAmount);
                    }
                    cartList.add(tour);
                    if (tDAO.isAvailable(id)) {
                        total += tour.getPrice() * amount;
                    }
                }
                if (cart.getDiscountCode() != null) {
                    discountedTotal = (total / 100) * (100 - cart.getDiscountCode().getDiscountPercentage());
                    discountedTotal = Convert.roundFloat(discountedTotal, 2);
                    request.setAttribute("DISCOUNT_CODE", cart.getDiscountCode().getId());
                    request.setAttribute("DISCOUNTED_TOTAL", discountedTotal);
                }
                total = Convert.roundFloat(total, 2);
                request.setAttribute("TOTAL", total);
                request.setAttribute("CART", cartList);
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
