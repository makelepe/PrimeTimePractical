package za.co.bmw.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetPrimes", urlPatterns = {"/GetPrimes"})
public class GetPrimes extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(GetPrimes.class.getName());

    @Inject
    private PrimeNumberService service;

    private String calculatePrimes(int lowerNumber, int upperNumber, boolean sieve) {
        LOG.log(Level.INFO, "calculatePrimes: lowerNumber = {0}, upperNumber = {1}, sieve = {2}", new Object[]{lowerNumber, upperNumber, sieve});
        StringBuilder sb = new StringBuilder();
        long start = Calendar.getInstance().getTimeInMillis();

        // Calculate prime numbers here and add to StringBuilder output:
        List<Integer> primeNumbers;
        if (!sieve) {
            primeNumbers = service.calculatePrimeNumbers(lowerNumber, upperNumber);
        } else {
            primeNumbers = service.calculatePrimeSieveOfEratosthenes(lowerNumber, upperNumber);
        }
        long end = Calendar.getInstance().getTimeInMillis();

        // preparing output
        sb.append("Calculated in ").append(end-start).append(" ms.<br/>");
        sb.append("<ul>");
        
        LOG.log(Level.INFO, "Prime Numbers : {0}, calculated in {1}", new Object[]{primeNumbers, (end-start)});
        for (int num : primeNumbers) {
            sb.append("<li>").append(num).append("</li>");
        }
        sb.append("</ul>");

        return sb.toString();
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
            throws ServletException, IOException {
        LOG.info("processRequest method invoked");
        response.setContentType("text/html;charset=UTF-8");

        int lowerNumber = Integer.parseInt(request.getParameter("lowerNumber"));
        int upperNumber = Integer.parseInt(request.getParameter("upperNumber"));
        String sieveStr = (request.getParameter("sieve") != null ? request.getParameter("sieve") : "false");
        boolean sieve = Boolean.parseBoolean(sieveStr);

        try (PrintWriter out = response.getWriter()) {
            out.println(calculatePrimes(lowerNumber, upperNumber, sieve));
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
        return "Get Primes - to calcuate prime numbers";
    }// </editor-fold>

}
