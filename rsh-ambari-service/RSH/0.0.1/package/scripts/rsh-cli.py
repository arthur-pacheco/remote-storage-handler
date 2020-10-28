import sys
from resource_management import *
class Master(Script):
  def install(self, env):
    print('Install the RSH Cli component')

  def configure(self, env):
    print('Configure the RSH Cli component')

  def status(self, env):
    print('Status of the RSH Cli component')

if __name__ == "__main__":
  Master().execute()
