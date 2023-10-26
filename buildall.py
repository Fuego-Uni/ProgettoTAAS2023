import os
import time
from os import walk

f = []
for (dirpath, dirnames, filenames) in walk('.'):
  f.extend(dirnames)
  break

# filter only directories that start with service_
f = filter(lambda x: x.startswith('service_'), f)

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
  os.chdir('..')

# end timer, print in seconds
end = time.time()
print("=====================================")
print(f"Total time: {round(end - start)} seconds")
print("=====================================")