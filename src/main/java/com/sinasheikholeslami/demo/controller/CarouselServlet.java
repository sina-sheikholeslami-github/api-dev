package com.microsoft.brandcentral.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jcr.Session;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "= Carousel Servlet",
        "sling.servlet.methods=" + "GET",
        "sling.servlet.methods=" + "POST",
        "sling.servlet.paths=" + "/bin/carousel"
})
public class CarouselServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String carouselId = request.getParameter("carouselId");
        if (carouselId == null) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = resolver.getResource("/var/carousels/" + carouselId);
        try {
            JSONObject jsonObject = new JSONObject();
            if (resource != null) {
                ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
                if (properties != null && properties.containsKey("StoreInitialIndex")) {
                    jsonObject.put("carouselId", carouselId);
                    jsonObject.put("StoreInitialIndex", properties.get("StoreInitialIndex", String.class));
                } else {
                    jsonObject.put("carouselId", carouselId);
                    jsonObject.put("StoreInitialIndex", "0");
                }
            } else {
                jsonObject.put("carouselId", carouselId);
                jsonObject.put("StoreInitialIndex", "0");
            }
            response.getWriter().write(jsonObject.toString());
        } catch (JSONException e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error creating JSON response.");
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String carouselId = request.getParameter("carouselId");
        String index = request.getParameter("StoreInitialIndex");
        if (carouselId == null || index == null) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ResourceResolver resolver = request.getResourceResolver();
        Session session = resolver.adaptTo(Session.class);
        if (session != null) {
            try {
                Node rootNode = session.getRootNode();
                Node varNode = rootNode.hasNode("var") ? rootNode.getNode("var") : rootNode.addNode("var");
                Node carouselsNode = varNode.hasNode("carousels") ? varNode.getNode("carousels") : varNode.addNode("carousels");
                Node carouselNode = carouselsNode.hasNode(carouselId) ? carouselsNode.getNode(carouselId) : carouselsNode.addNode(carouselId);

                carouselNode.setProperty("StoreInitialIndex", index);
                session.save();
            } catch (RepositoryException e) {
                response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error saving carousel index.");
            }
        }
    }
}
