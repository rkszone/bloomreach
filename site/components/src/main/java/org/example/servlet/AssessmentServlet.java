package org.example.servlet;

import org.example.helper.PropertyHelper;
import org.hippoecm.hst.site.HstServices;
import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse; import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssessmentServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AssessmentServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String search = request.getParameter("search");
        Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
        Session session;
        try {

            session = repository.login(new SimpleCredentials(PropertyHelper.getProperty("cms.user.name"),
                    PropertyHelper.getProperty("cms.user.password").toCharArray()));
            Query q = session.getWorkspace().getQueryManager().createQuery("//*[jcr:contains(.,'"+search+"')]",Query.XPATH);
            QueryResult r = q.execute();

            PrintWriter printWriter = response.getWriter();
            printWriter.println("<html>");
            printWriter.println("<head><title>Search Result</title></title>");
            printWriter.println("<body>");
            printWriter.println("<h2>Number of results found for '"+search+"' : "+ r.getRows().getSize() +"</h2>");
            printWriter.println("<ol>");
            NodeIterator i = r.getNodes();
            while (i.hasNext()) {
                Node n = i.nextNode();
                printWriter.println("<li>jcr:name = " +n.getName() +
                        "<br> jcr:path = " +n.getPath()+
                        "<br> jcr:primaryType = " + n.getPrimaryNodeType() +
                        " </li>");
            }
            printWriter.println("</ol>");
            printWriter.println("</body></html>");

        } catch (RepositoryException | IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
        Session session;
        try {
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<html>");
            printWriter.println("<head><title>Node Finder</title></title>");
            printWriter.println("<body>");

            //2.3 List nodes assessment
            listNodesAssessment(session,printWriter);

            //2.4 Querying assessment
            queryingAssessment(printWriter);

            printWriter.println("</body></html>");
        } catch (RepositoryException | IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * List Nodes Assessment method to recursively find all the nodes under /content/documents/
     * and display the names of descendant nodes.
     * @param session Session of the user
     * @param printWriter print Writer to print names of sub node
     * @throws RepositoryException RepositoryException will be thrown if jcr throw exception
     */
    private void listNodesAssessment(Session session, PrintWriter printWriter) throws RepositoryException {
        String parentNodePath = "content/documents";
        Node node = session.getRootNode().getNode(parentNodePath);
        printWriter.println("<h2>All the nodes under /"+parentNodePath+"/</h2>");
        printAllSubNodeNameFromParentNode(node, printWriter);
    }

    /**
     * Method to print all sub node name from parent node
     * @param parentNode parent Node for which sub nodes will print
     * @param printWriter print Writer to print names of sub node
     * @throws RepositoryException RepositoryException will be thrown if jcr throw exception
     */
    private void printAllSubNodeNameFromParentNode(Node parentNode, PrintWriter printWriter) throws RepositoryException {
        printWriter.println("<ul>");
        for (NodeIterator i = parentNode.getNodes(); i.hasNext(); ) {
            if (!parentNode.isNodeType("hippofacnav:facetnavigation")) {
                Node n = i.nextNode();
                printWriter.println("<li>" + n.getName() + "</li>");
                if(n.hasNodes())
                    printAllSubNodeNameFromParentNode(n,printWriter);
            }
        }
        printWriter.println("</ul>");
    }

    /**
     * Querying Assessment method to execute queries against the repository for some user entered text
     * and display the names and properties of all the nodes that contain that text.
     * @param printWriter print Writer to print names of sub node
     */
    private void queryingAssessment(PrintWriter printWriter) {
        printWriter.println("<h2>Search all nodes : </h2>");
        printWriter.println("<form action=\"/site/assessment\" method=\"post\">");
        printWriter.println("<span class=\"icon\">Search Node : </i></span>");
        printWriter.println("<input type=\"search\" id=\"search\" name=\"search\" placeholder=\"Search\"/>");
        printWriter.println("</form>");
    }
}
