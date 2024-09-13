import os
import time
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--github_actions', action='store_true')

args = parser.parse_args()
print(f'args {args}')

is_github_actions = args.github_actions

input_filepath = 'app/build/outputs/apk/debug/app-debug.apk'
if not os.path.exists(input_filepath):
    raise Exception(f'{input_filepath} does not exist')

artifact_filename = f'android_image_viewer_{time.time_ns()}.apk'
output_filepath = os.path.abspath(artifact_filename)
print(f'output_filepath {output_filepath}')

if is_github_actions:
    github_env_filepath = os.environ['GITHUB_ENV']
    with open(github_env_filepath, mode='ab+') as outfile:
        outfile.write(b'\n')
        outfile.write(f'GITHUB_ARTIFACT_PATH={output_filepath}\n'.encode('utf-8'))
        outfile.write(f'GITHUB_ARTIFACT_NAME={artifact_filename}\n'.encode('utf-8'))

os.rename(input_filepath, output_filepath)
