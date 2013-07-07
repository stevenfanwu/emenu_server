#!/usr/bin/env python

import subprocess
import os
import os.path

TOMCAT_HOME = os.environ['TOMCAT_HOME']

def main():
    subprocess.check_call(['bash', TOMCAT_HOME + '/bin/shutdown.sh'])

    subprocess.check_call(['mvn', 'clean'])
    subprocess.check_call(['mvn', 'package'])

    subprocess.check_call(['rm', '-fr', TOMCAT_HOME + '/webapps/ROOT'])

    subprocess.check_call(['cp', '-r', 'target/CloudMenuServer', TOMCAT_HOME + '/webapps/ROOT'])
    subprocess.check_call(['cp', 'lib/sqlite/libsqlite4java-osx.jnilib', TOMCAT_HOME + '/webapps/ROOT/WEB-INF/lib/'])
    subprocess.check_call(['rm', '-fr', TOMCAT_HOME + '/webapps/ROOT/WEB-INF/view'])
    subprocess.check_call(['ln', '-s', os.path.abspath('src/main/webapp/WEB-INF/view'), TOMCAT_HOME + '/webapps/ROOT/WEB-INF/view'])
    subprocess.check_call(['bash', TOMCAT_HOME + '/bin/startup.sh'])
    #subprocess.check_call(['less', TOMCAT_HOME + '/logs/catalina.out'])

if __name__ == '__main__':
    main()
