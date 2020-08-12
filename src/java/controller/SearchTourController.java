/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LocationDAO;
import dao.TourDAO;
import dto.LocationDTO;
import dto.TourDTO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import utilities.Generate;
import utilities.Logging;

/**
 *
 * @author Mk
 */
public class SearchTourController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SearchTourController.class);
    private static final String HOME = "home.jsp";

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
        String url = HOME;
        Timestamp searchFromDate = null, searchToDate = null;
        int searchFromPrice = -1, searchToPrice = -1, page = 1, pagesAmount;
        int[] intArray;
        String pageString = request.getParameter("page");
        String priceRange = request.getParameter("priceRange");
        String searchDeparture = request.getParameter("searchDeparture");
        String searchDestination = request.getParameter("searchDestination");
        String searchFromDateString = request.getParameter("searchFromDate");
        String searchToDateString = request.getParameter("searchToDate");
        try {
            if(searchDeparture != null){
                if(searchDeparture.isEmpty()){
                    searchDeparture = null;
                }
            }
            if(searchDestination != null){
                if(searchDestination.isEmpty()){
                    searchDestination = null;
                }
            }
            if (pageString != null) {
                page = Integer.parseInt(pageString);
            }
            if (searchFromDateString != null) {
                if (!searchFromDateString.isEmpty()) {
                    searchFromDate = Generate.generateTimestamp(searchFromDateString);
                }
            }
            if (searchToDateString != null) {
                if (!searchToDateString.isEmpty()) {
                    searchToDate = Generate.generateTimestamp(searchToDateString);
                }
            }
            if (priceRange != null) {
                if (!priceRange.isEmpty()) {
                    intArray = Generate.generatePriceArray(priceRange);
                    searchFromPrice = intArray[0];
                    searchToPrice = intArray[1];
                }
            }
            TourDAO tDAO = new TourDAO();
            tDAO.updateTourStatus();
            Logging.logDebug(logger, "page", page);
            Logging.logDebug(logger, "searchDeparture", searchDeparture);
            List<TourDTO> tourList = tDAO.search(page, searchDeparture, searchDestination, searchFromDate, searchToDate, searchFromPrice, searchToPrice);
            request.setAttribute("TOUR_LIST", tourList);
            LocationDAO lDAO = new LocationDAO();
            List<LocationDTO> locationList = lDAO.getLocationList();
            request.setAttribute("LOCATION_LIST", locationList);
            pagesAmount = tDAO.getPagesAmount(page, searchDeparture, searchDestination, searchFromDate, searchToDate, searchFromPrice, searchToPrice);
            request.setAttribute("PAGES_AMOUNT", pagesAmount);
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
