import os
import time
import stat
import argparse

RS = '\033[0m'
R = '\033[91m'
G = '\033[92m'
Y = '\033[93m'

source_app_name = 'network_monitor'
source_package = 'io.github.ichisadashioko.android.network_monitor'

new_app_name = 'image_viewer'
new_package = 'io.github.ichisadashioko.android.imageviewer'

parser = argparse.ArgumentParser()

parser.add_argument(
    'new_app_name',
    type=str,
    default=new_app_name,
    nargs='?',
)

parser.add_argument(
    'new_package',
    type=str,
    default=new_package,
    nargs='?',
)

parser.add_argument(
    '--source_app_name',
    type=str,
    default='network_monitor',
)

parser.add_argument(
    '--source_package',
    type=str,
    default='io.github.ichisadashioko.android.network_monitor',
)

parser.add_argument('-r', '--r', '-run', '--run', dest='run', action='store_true')

args = parser.parse_args()
print(f'args {G}{args}{RS}')

source_app_name = args.source_app_name
source_package = args.source_package

new_app_name = args.new_app_name
new_package = args.new_package

is_run = args.run

# exclude current file
current_filepath = os.path.abspath(__file__)
current_filename = os.path.basename(current_filepath)

java_source_dir = 'app/src/main/java'

IGNORE_FILENAME_LIST = [
    '.git',
    '.gitignore',
    '.idea',
    'build',
    'gradle',
    'scripts',
    '.gitmodules',
    'gradlew',
    'gradlew.bat',
    '.gradle',

    current_filename,
]

IGNORE_FILENAME_LIST = list(map(lambda x: x.upper(), IGNORE_FILENAME_LIST))


def indexing_files(
    inpath: str,
    outlog: list,
):
    print(inpath, end='\r')

    filename = os.path.basename(inpath)
    if filename.upper() in IGNORE_FILENAME_LIST:
        return

    file_stat = os.stat(inpath)
    if stat.S_ISLNK(file_stat.st_mode):
        # TODO handle symlink
        return

    if stat.S_ISREG(file_stat.st_mode):
        outlog.append(inpath)
        return

    if stat.S_ISDIR(file_stat.st_mode):
        child_filename_list = os.listdir(inpath)
        for child_filename in child_filename_list:
            child_filepath = os.path.join(inpath, child_filename)
            indexing_files(
                inpath=child_filepath,
                outlog=outlog,
            )


input_dir = '.'
input_dir = os.path.abspath(input_dir)

indexing_outlog = []
indexing_files(
    inpath=input_dir,
    outlog=indexing_outlog,
)

print('', flush=True)

print(f'len(indexing_outlog) {len(indexing_outlog)}')

# find and replace package name

for filepath in indexing_outlog:
    print(f'{Y}{filepath}{RS}')
    file_content_str = open(filepath, 'rb').read().decode('utf-8')
    new_file_content_str = file_content_str
    if source_package in new_file_content_str:
        print(f'{R}source_package in file_content_str{RS}')
        new_file_content_str = file_content_str.replace(
            source_package,
            new_package,
        )

    if source_app_name in new_file_content_str:
        print(f'{R}source_app_name in file_content_str{RS}')
        new_file_content_str = file_content_str.replace(
            source_app_name,
            new_app_name,
        )

    if is_run:
        if new_file_content_str != file_content_str:
            open(filepath, 'wb').write(new_file_content_str.encode('utf-8'))

package_rel_dir = source_package.replace('.', '/')
package_abs_dir = os.path.join(input_dir, java_source_dir, package_rel_dir)
package_abs_dir = os.path.abspath(package_abs_dir)
print(f'package_abs_dir {R}{package_abs_dir}{RS}', os.path.exists(package_abs_dir))

new_package_rel_dir = new_package.replace('.', '/')
new_package_abs_dir = os.path.join(input_dir, java_source_dir, new_package_rel_dir)
new_package_abs_dir = os.path.abspath(new_package_abs_dir)
print(f'new_package_abs_dir {G}{new_package_abs_dir}{RS}', os.path.exists(new_package_abs_dir))

if not os.path.exists(package_abs_dir):
    raise Exception(f'{R}{package_abs_dir}{RS} does not exist')

if os.path.exists(new_package_abs_dir):
    raise Exception(f'{R}{new_package_abs_dir}{RS} already exists')

if is_run:
    new_package_parent_dir = os.path.dirname(new_package_abs_dir)
    if not os.path.exists(new_package_parent_dir):
        os.makedirs(new_package_parent_dir)

    os.rename(package_abs_dir, new_package_abs_dir)
