package com.picturemaster;

import org.apache.commons.fileupload.FileItem;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class AiPictureConvertor {


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


    public static ExecState runESRGAN(@NotNull FileItem uploadedItem) {

        String uploadedFileName = new File(uploadedItem.getName()).getName();
        // TODO relative path
        String storeFilePath = "/home/kehan/android-workspace/PictureMasterServer/models/ESRGAN/LR" + File.separator + uploadedFileName;
        File storeFile = new File(storeFilePath);

        try {
            uploadedItem.write(storeFile);

            // TODO relative path
            String[] cmd = {"/bin/zsh", "-c", "/home/kehan/android-workspace/PictureMasterServer/scripts/esrgan.sh"};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception e) {

            e.printStackTrace();
            return ExecState.FAILED;
        }
    }

    public static ExecState runCartoonGANHayao(@NotNull FileItem uploadedItem) {

        String uploadedFileName = new File(uploadedItem.getName()).getName();
        // TODO relative path
        String storeFilePath = "/home/kehan/android-workspace/PictureMasterServer/models/CartoonGAN-Test-Pytorch-Torch/test_img" + File.separator + uploadedFileName;
        File storeFile = new File(storeFilePath);

        try {
            uploadedItem.write(storeFile);

            // TODO relative path
            String[] cmd = {"/bin/zsh", "-c", "/home/kehan/android-workspace/PictureMasterServer/scripts/cartoongan_hayao.sh"};
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
        String storeFilePath = "/home/kehan/android-workspace/PictureMasterServer/models/CartoonGAN-Test-Pytorch-Torch/test_img" + File.separator + uploadedFileName;
        File storeFile = new File(storeFilePath);

        try {
            uploadedItem.write(storeFile);

            String[] cmd = {"/bin/zsh", "-c", "/home/kehan/android-workspace/PictureMasterServer/scripts/cartoongan_hosoda.sh"};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception ex) {

            ex.printStackTrace();
            return ExecState.FAILED;
        }
    }

}
