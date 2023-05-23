#!/usr/bin/env python3
# encoding=utf-8
import os

if __name__ == '__main__':
    android_sdk_root = os.path.abspath('ANDROID_SDK_ROOT')
    gradle_props_filepath = 'local.properties'

    with open(gradle_props_filepath, mode='ab+') as outfile:
        outfile.write(f'''
sdk.dir={android_sdk_root}
'''.encode('utf-8'))
