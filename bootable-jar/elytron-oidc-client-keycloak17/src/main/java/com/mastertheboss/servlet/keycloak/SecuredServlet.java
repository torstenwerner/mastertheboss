
package com.mastertheboss.servlet.keycloak;

import org.wildfly.security.http.oidc.OidcPrincipal;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;


@WebServlet("/secure")
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "GET", rolesAllowed = {"customer-manager"})})
public class SecuredServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("<html>");
            writer.println("  <body>");
            writer.println("    <h1>Keycloak OIDC Secured Servlet</h1>");
            writer.println("    <p>");
            writer.print(" Current Principal '");
            Principal user = req.getUserPrincipal();
            final String name;
            if (user instanceof OidcPrincipal) {
                final OidcPrincipal<?> oidcUser = (OidcPrincipal<?>) user;
                name = oidcUser.getOidcSecurityContext().getIDToken().getPreferredUsername();
            } else {
                name = user != null ? user.getName() : "NONE";
            }
            writer.print(name);
            writer.print("'");
            writer.println("    </p>");
            writer.println("  </body>");
            writer.println("</html>");
        }
    }

}
