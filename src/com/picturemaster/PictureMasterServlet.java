package com.picturemaster;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class PictureMasterServlet
 */
@WebServlet("/PictureMasterServlet")
public class PictureMasterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;    // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;      // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;   // 50MB

    public PictureMasterServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (ServletFileUpload.isMultipartContent(request)) {


            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);
            fileUpload.setHeaderEncoding("UTF-8");

            String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
            File tempFile = new File(tempPath);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }

            try {
                List<FileItem> items = fileUpload.parseRequest(request);

                if (items != null && items.size() > 0) {

                    int cnt = 0;
                    for (FileItem item : items) {

                        System.out.println("Item count: " + cnt);
                        cnt++;
                        if (item.isFormField()) {
                            System.out.println(item.getFieldName());
                            System.out.println(item.getString());
                        } else {
                            System.out.println(item.getName());
                        }
                    }

                    String convert_type = new String("");
                    for (FileItem item : items) {

                        if (item.isFormField() && item.getFieldName().equals("convert_type")) {
                            System.out.println("type param: " + item.getString());
                            convert_type = item.getString();
                        }
                    }

                    AiPictureConvertor.runCleanOldImgs();

                    for (FileItem item : items) {
                        if (!item.isFormField()) {

                            ExecState execState = ExecState.START;

                            if (convert_type.equals(AiConvertMethod.ESRGAN.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.ESRGAN);
                            }
                            else if (convert_type.equals(AiConvertMethod.CartoonGAN_Hayao.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.CartoonGAN_Hayao);
                            }
                            else if (convert_type.equals(AiConvertMethod.CartoonGAN_Hosoda.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.CartoonGAN_Hosoda);
                            }
                            else if (convert_type.equals(AiConvertMethod.CycleGAN_Cezanne.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.CycleGAN_Cezanne);
                            }
                            else if (convert_type.equals(AiConvertMethod.CycleGAN_Monet.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.CycleGAN_Monet);
                            }
                            else if (convert_type.equals(AiConvertMethod.CycleGAN_Ukiyoe.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.CycleGAN_Ukiyoe);
                            }
                            else if (convert_type.equals(AiConvertMethod.CycleGAN_Vangogh.toString())) {
                                execState = AiPictureConvertor.convert(item, AiConvertMethod.CycleGAN_Vangogh);
                            }
                            else {
                                System.out.println("Wrong Input Convert Type");
                                request.setAttribute("message", "Wrong Input Convert Type");
                            }
                            request.setAttribute("message", execState.toString());
                            System.out.println("Run complete!");
                        }
                    }
                }

            } catch (Exception ex) {
                request.setAttribute("message", "Error:" + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("Error: must contain \'enctype=multipart/form-data\'");
            printWriter.flush();
            return;
        }

        request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }

}
