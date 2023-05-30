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
    with open('github_actions_artifact_path.log', mode='wb') as outfile:
        content = f'GITHUB_ARTIFACT_PATH={output_filepath}\nGITHUB_ARTIFACT_NAME={artifact_filename}'
        outfile.write(output_filepath.encode('utf-8'))

os.rename(input_filepath, output_filepath)
