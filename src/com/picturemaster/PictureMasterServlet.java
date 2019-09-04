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


            String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
            String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");

            File tempFile = new File(tempPath);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }

            try {
                List<FileItem> items = fileUpload.parseRequest(request);

                if (items != null && items.size() > 0) {
                    for (FileItem item : items) {
                        if (!item.isFormField()) {

                            String fileName = new File(item.getName()).getName();
                            String filePath = "/home/kehan/android-workspace/PictureMasterServer/models/CartoonGAN-Test-Pytorch-Torch/test_img" + File.separator + fileName;
                            File storeFile = new File(filePath);
                            item.write(storeFile);
                            ExecState execState = AiPictureConvertor.runCartoonGANHayao();

                            request.setAttribute("message", execState.toString());
                        }
                    }
                }
//                for (FileItem item : items) {
//                    if (item.isFormField()) {
//                        String name = item.getFieldName();
//                    } else {
//                        String longFileName = item.getName();
//
//                    }
//                }
            } catch (Exception e) {
                request.setAttribute("message", "Error:" + e.getMessage());
                e.printStackTrace();
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
