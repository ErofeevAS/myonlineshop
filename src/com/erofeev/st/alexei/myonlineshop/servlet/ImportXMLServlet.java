package com.erofeev.st.alexei.myonlineshop.servlet;

import com.erofeev.st.alexei.myonlineshop.repository.exception.ServiceException;
import com.erofeev.st.alexei.myonlineshop.repository.model.Item;
import com.erofeev.st.alexei.myonlineshop.service.ItemService;
import com.erofeev.st.alexei.myonlineshop.service.converter.ItemConverter;
import com.erofeev.st.alexei.myonlineshop.service.impl.ItemServiceImpl;
import com.erofeev.st.alexei.myonlineshop.servlet.command.CommandEnum;
import com.erofeev.st.alexei.myonlineshop.xml.XMLService;
import com.erofeev.st.alexei.myonlineshop.xml.impl.XMLServiceImpl;
import com.erofeev.st.alexei.myonlineshop.xml.model.ItemXML;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

public class ImportXMLServlet extends HttpServlet {
    private XMLService xmlService = XMLServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        System.out.println("File name " + fileName);
        InputStream fileContent = filePart.getInputStream();
        ServletContext context = getServletContext();
        ServletConfig servletConfig = getServletConfig();
        String xsdPath = servletConfig.getInitParameter("xsdPath");
        String xsdFullPath = context.getRealPath(xsdPath);
        File xsdScheme = new File(xsdFullPath);
        if (xmlService.isValidXML(fileContent, xsdScheme)) {
            fileContent = filePart.getInputStream();
            List<ItemXML> itemsXML = xmlService.importItemsFromFile(fileContent, xsdScheme);
            List<Item> items = ItemConverter.convertItemsXMLtoItems(itemsXML);
            try {
                itemService.importItems(items);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("error", "XML FILE NOT VALID");
        }
        String baseURL = "/myonlineshop/shop?command=";
        String page = baseURL + CommandEnum.ITEMS_DELETE.name().toLowerCase();
        resp.sendRedirect(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private String convert(InputStream inputStream, Charset charset) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ServletConfig config = getServletConfig();
//        String xsdFilePath = config.getInitParameter("xsd");
//        String dirPath = config.getInitParameter("dir");
//        ServletContext app = getServletContext();
//        File tmpDir = (File)app.getAttribute("javax.servlet.context.tempdir");
//        File tmpFile = new File(tmpDir.getAbsolutePath() + dirPath);
//        tmpFile.mkdir();
//        File xsd = new File(xsdFilePath);
//        System.out.println(tmpDir);
//        Part part = req.getPart("file");
//        InputStream inputStream = part.getInputStream();
//        try {
//            System.out.println("XML");
//            itemService.importItems(inputStream, xsd);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigurationManagerImpl.ITEMS_PAGE);
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//        dispatcher.forward(req, resp);
//
//    }
}
