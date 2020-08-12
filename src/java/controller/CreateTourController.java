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
import dto.errorObject.CreateTourErrorObject;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;
import utilities.Generate;
import utilities.Logging;
import utilities.Validate;

/**
 *
 * @author Mk
 */
//@WebServlet("/CreateTourController")
//@MultipartConfig
public class CreateTourController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateTourController.class);
    private static final String PREPARE = "admin/createTour.jsp";
    private static final String IMAGE_FOLDER = "images";
    private static final String HOME = "SearchTourController";
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
        String action = request.getParameter("action");
        String url = ERROR;
        try {
            if (action != null) {
                if (action.equals("prepare")) {
                    LocationDAO lDAO = new LocationDAO();
                    List<LocationDTO> locationList = lDAO.getLocationList();
                    request.setAttribute("LOCATION_LIST", locationList);
                    url = PREPARE;
                } else if (action.equals("create")) {
                    String name = request.getParameter("name");
                    String id = request.getParameter("id");
                    String departure = request.getParameter("departure");
                    String destination = request.getParameter("destination");
                    String fromDateString = request.getParameter("fromDate");
                    String toDateString = request.getParameter("toDate");
                    String priceString = request.getParameter("price");
                    String quotaString = request.getParameter("quota");
                    String image = null;

                    Part filePart = request.getPart("image");

                    CreateTourErrorObject error = Validate.validateTour(id, name, departure, destination, fromDateString, toDateString, priceString, quotaString, filePart);
                    if (error.isEmpty()) {
                        if (filePart != null) {
                            if (filePart.getSize() > 0) {
                                String appPath = request.getServletContext().getRealPath("/home.jsp");
                                String fullPath = Generate.getSaveImagePath(id, appPath, IMAGE_FOLDER);
                                filePart.write(fullPath);
                                image = IMAGE_FOLDER + "/" + id + ".jpg";
                            }
                        }
                        id = id.toUpperCase();
                        TourDTO tour = Generate.generateTour(id, name, departure, destination, fromDateString, toDateString, priceString, quotaString, image);
                        TourDAO tDAO = new TourDAO();
                        if (tDAO.add(tour)) {
                            url = HOME;
                        } else {
                            request.setAttribute("ERROR", "Error occured");
                            url = ERROR;
                        }
                    } else {
                        LocationDAO lDAO = new LocationDAO();
                        List<LocationDTO> locationList = lDAO.getLocationList();
                        request.setAttribute("LOCATION_LIST", locationList);
                        request.setAttribute("ERROR", error);
                        url = PREPARE;
                    }
                }
            } else {
                url = ERROR;
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
