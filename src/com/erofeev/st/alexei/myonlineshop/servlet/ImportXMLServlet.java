package com.erofeev.st.alexei.myonlineshop.servlet;

import com.erofeev.st.alexei.myonlineshop.config.connection.ConfigurationManagerImpl;
import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.xml.XMLService;
import com.erofeev.st.alexei.myonlineshop.xml.impl.XMLServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImportXMLServlet extends HttpServlet {
    private XMLService xmlService = XMLServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        String xsdFilePath = config.getInitParameter("xsd");
        String dirPath = config.getInitParameter("dir");
        ServletContext app = getServletContext();
        File tmpDir = (File)app.getAttribute("javax.servlet.context.tempdir");
        File tmpFile = new File(tmpDir.getAbsolutePath() + dirPath);
        tmpFile.mkdir();
        File xsd = new File(xsdFilePath);



        System.out.println(tmpDir);


        Part part = req.getPart("file");
        InputStream inputStream = part.getInputStream();


        try {
            System.out.println("XML");
            itemService.importFromXml(inputStream, xsd);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(req, resp);

    }
}
