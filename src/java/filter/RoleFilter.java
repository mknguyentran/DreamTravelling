/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import dto.AccountDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utilities.Logging;

/**
 *
 * @author Mk
 */
public class RoleFilter implements Filter {

    private static final boolean debug = true;
    private Logger logger = Logger.getLogger(RoleFilter.class);
    private static final String UNAUTHENTICATED = "/unauthenticated.jsp";
    private static final String UNAVAILABLE = "/unavailable.jsp";
    private static final String HOME = "SearchTourController";
    private static final ArrayList<String> RESOURCE_FOR_ALL = new ArrayList<>();
    private static final ArrayList<String> RESOURCE_FOR_GUEST = new ArrayList<>();
    private static final ArrayList<String> RESOURCE_FOR_ADMIN = new ArrayList<>();
    private static final ArrayList<String> RESOURCE_FOR_USER = new ArrayList<>();

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public RoleFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("RoleFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        String url = null;
        try {
            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);
            if (resource.length() > 0) {
                boolean isStaticResource = resource.contains(".css") || uri.contains("/font/") || uri.contains("/template/") || uri.contains("/images/") || resource.contains(".js");
                if (isStaticResource) {
                    url = resource;
                } else {
                    boolean isResourceForAll = RESOURCE_FOR_ALL.contains(resource);
                    boolean isResourceForAdmin = RESOURCE_FOR_ADMIN.contains(resource);
                    boolean isResourceForUser = RESOURCE_FOR_USER.contains(resource);
                    boolean isResourceForGuest = RESOURCE_FOR_GUEST.contains(resource);
                    boolean isNotAvailableResource = !isResourceForAll && !isResourceForAdmin && !isResourceForUser && !isResourceForGuest;
                    AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
                    String role = null;
                    if (isNotAvailableResource) {
                        logger.debug(resource + " is not available");
                        url = UNAVAILABLE;
                    } else if (account != null) {
                        role = account.getRole();
                        if (!isResourceForAll && ((role.equals("admin") && !isResourceForAdmin) || (role.equals("user") && !isResourceForUser))) {
                            logger.debug(resource + " is invalid access");
                            url = UNAUTHENTICATED;
                        }
                    } else {
                        if (!isResourceForAll && !isResourceForGuest) {
                            logger.debug(resource + " is invalid access");
                            url = UNAUTHENTICATED;
                        }
                    }
                }
            } else {
                url = HOME;
            }

            if (url != null) {
                req.getRequestDispatcher(url).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            Logging.logError(logger, e);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("RoleFilter:Initializing filter");
            }
        }
        RESOURCE_FOR_ALL.add("searchTour");
        RESOURCE_FOR_ALL.add("loadTour");

        RESOURCE_FOR_ADMIN.add("CreateTourController");
        RESOURCE_FOR_ADMIN.add("createTour");
        RESOURCE_FOR_ADMIN.add("logout");

        RESOURCE_FOR_USER.add("manageCart");
        RESOURCE_FOR_USER.add("placeOrder");
        RESOURCE_FOR_USER.add("loadCart");
        RESOURCE_FOR_USER.add("loadOrder");
        RESOURCE_FOR_USER.add("logout");
        RESOURCE_FOR_USER.add("applyDiscountCode");

        RESOURCE_FOR_GUEST.add("login.jsp");
        RESOURCE_FOR_GUEST.add("login");
        
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("RoleFilter()");
        }
        StringBuffer sb = new StringBuffer("RoleFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
