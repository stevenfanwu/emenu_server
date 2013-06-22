#!/usr/bin/env python

import subprocess
import os

TOMCAT_HOME = os.environ['TOMCAT_HOME']

def main():
    subprocess.check_call(['mvn', 'clean'])
    subprocess.check_call(['mvn', 'package'])
    subprocess.check_call(['rm', '-fr', TOMCAT_HOME + '/webapps/CloudMenuServer'])
    subprocess.check_call(['cp', '-r', 'target/CloudMenuServer', TOMCAT_HOME + '/webapps/'])
    subprocess.check_call(['cp', 'lib/sqlite/libsqlite4java-osx.jnilib', TOMCAT_HOME + '/webapps/CloudMenuServer/WEB-INF/lib/'])
    subprocess.check_call(['bash', TOMCAT_HOME + '/bin/shutdown.sh'])
    subprocess.check_call(['bash', TOMCAT_HOME + '/bin/startup.sh'])
    #subprocess.check_call(['less', TOMCAT_HOME + '/logs/catalina.out'])

if __name__ == '__main__':
    main()
