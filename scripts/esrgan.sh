#!/bin/zsh
export PYTHONPATH="/home/kehan/.local/lib/python3.5/site-packages":$PYTHONPATH
cd ./models/ESRGAN && python3 test.py
