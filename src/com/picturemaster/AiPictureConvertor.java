package com.picturemaster;

import org.apache.commons.fileupload.FileItem;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class AiPictureConvertor {

    private static final String STORE_FILE_PATH =
            "/home/kehan/android-workspace/PictureMasterServer/apache-tomcat-9.0.24/webapps/PictureMasterServer_war/input_imgs";

    private static final String CLEAN_INPUT_IMG_DIR_SCRIPT_PATH =
            "/home/kehan/android-workspace/PictureMasterServer/scripts/clean_input_img_dir.sh";

    private static final String ESRGAN_SCRIPT_PATH =
            "/home/kehan/android-workspace/PictureMasterServer/scripts/esrgan.sh";

    private static final String CARTOONGAN_HAYAO_SCRIPT_PATH =
            "/home/kehan/android-workspace/PictureMasterServer/scripts/cartoongan_hayao.sh";

    private static final String CARTOONGAN_HOSODA_SCRIPT_PATH =
            "/home/kehan/android-workspace/PictureMasterServer/scripts/cartoongan_hosoda.sh";


    private static void callShell(String[] shellCommand) {

        try {
            Process process = Runtime.getRuntime().exec(shellCommand);

            int exitValue = process.waitFor();
            if (0 != exitValue) {
                System.out.println("Runtime.getRuntime().exec() return code: " + exitValue);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static ExecState runCleanOldImgs() {

        try {

            // TODO relative path
            String[] cmd = {"/bin/zsh", "-c", CLEAN_INPUT_IMG_DIR_SCRIPT_PATH};
            callShell(cmd);

            // TODO!
            return ExecState.SUCCESS;

        } catch (Exception ex) {

            ex.printStackTrace();
            return ExecState.FAILED;
        }

    }


    public static ExecState runESRGAN(@NotNull FileItem uploadedItem) {

        String uploadedFileName = new File(uploadedItem.getName()).getName();
        // TODO relative path
        String storeFilePath = STORE_FILE_PATH + File.separator + uploadedFileName;
        File storeFile = new File(storeFilePath);

        try {
            uploadedItem.write(storeFile);

            // TODO relative path
            String[] cmd = {"/bin/zsh", "-c", ESRGAN_SCRIPT_PATH};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception ex) {

            ex.printStackTrace();
            return ExecState.FAILED;
        }
    }

    public static ExecState runCartoonGANHayao(@NotNull FileItem uploadedItem) {

        String uploadedFileName = new File(uploadedItem.getName()).getName();
        // TODO relative path
        String storeFilePath = STORE_FILE_PATH + File.separator + uploadedFileName;
        File storeFile = new File(storeFilePath);

        try {
            uploadedItem.write(storeFile);

            // TODO relative path
            String[] cmd = {"/bin/zsh", "-c", CARTOONGAN_HAYAO_SCRIPT_PATH};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception ex) {

            ex.printStackTrace();
            return ExecState.FAILED;
        }

    }

    public static ExecState runCartoonGANHosoda(@NotNull FileItem uploadedItem) {

        String uploadedFileName = new File(uploadedItem.getName()).getName();
        // TODO relative path
        String storeFilePath = STORE_FILE_PATH + File.separator + uploadedFileName;
        File storeFile = new File(storeFilePath);

        try {
            uploadedItem.write(storeFile);

            String[] cmd = {"/bin/zsh", "-c", CARTOONGAN_HOSODA_SCRIPT_PATH};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception ex) {

            ex.printStackTrace();
            return ExecState.FAILED;
        }
    }

}
