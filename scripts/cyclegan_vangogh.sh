#!/bin/zsh
cd /home/kehan/android-workspace/PictureMasterServer/models/pytorch-CycleGAN-and-pix2pix && python3 test.py --dataroot '/home/kehan/android-workspace/PictureMasterServer/apache-tomcat-9.0.24/webapps/PictureMasterServer_war/input_imgs' --results_dir '/home/kehan/android-workspace/PictureMasterServer/apache-tomcat-9.0.24/webapps/PictureMasterServer_war/output_imgs' --name style_vangogh_pretrained --model test --no_dropout --load_size 1024 --crop_size 1024
