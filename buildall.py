import os
import time
from os import walk
import sys

# get args
args = sys.argv
build_filter = args[1:]

f = []
for (dirpath, dirnames, filenames) in walk('.'):
  f.extend(dirnames)
  break

# filter only directories that start with service_
f = filter(lambda x: x.startswith('service_'), f)


# check if a folder matches any of the filters
def matches_filter(folder):
  for filter in build_filter:
    if filter in folder:
      return True
  return False

# filter out folders that don't match the filter
if len(build_filter) > 0:
  f = filter(matches_filter, f)

# start timer
start = time.time()

# build all the directories
for d in f:
  print("=====================================")
  print("Building " + d)
  print("=====================================")
  os.chdir(d)
  # check if on unix
  if os.name == 'posix':
    os.system('./mvnw package')
  else:
    os.system('mvnw package')
  os.system(f'docker build -t {d} .')
  os.chdir('..')

# end timer, print in seconds
end = time.time()
print("=====================================")
print(f"Total time: {round(end - start)} seconds")
print("=====================================")