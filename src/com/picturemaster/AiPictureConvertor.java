package com.picturemaster;

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

    public static ExecState runESRGAN() {
        try {
            // TODO relative path
            String[] cmd = {"/bin/zsh", "-c", "/home/kehan/android-workspace/PictureMasterServer/scripts/esrgan.sh"};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();

            return ExecState.FAILED;
        }
    }

    public static ExecState runCartoonGANHayao() {
        try {
            // TODO
            String[] cmd = {"/bin/zsh", "-c", "/home/kehan/android-workspace/PictureMasterServer/scripts/cartoongan_hayao.sh"};
            System.out.println(System.getProperty("user.dir"));
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();

            return ExecState.FAILED;
        }
    }

    public static ExecState runCartoonGANHosoda() {
        try {
            String[] cmd = {"/bin/zsh", "-c", "/home/kehan/android-workspace/PictureMasterServer/scripts/cartoongan_hosoda.sh"};
            callShell(cmd);

            return ExecState.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();

            return ExecState.FAILED;
        }
    }

}
