- Install emacs
apt-get install emacs

- Install orcale java7
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | tee -a /etc/apt/sources.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu precise main" | tee -a /etc/apt/sources.list
apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886
apt-get update
apt-get install oracle-java7-installer

- Install tomcat7
apt-get install tomcat7
add following to /etc/default/tomcat7: JAVA_HOME=/usr/lib/jvm/java-7-oracle
service tomcat7 start

- Remove default web app, change permission for webapps
rm -rf /var/lib/tomcat7/webapps/ROOT
chmod o+w /var/lib/tomcat7/webapps

- Copy web app
scp -r /home/zhou/workspace/hummingbird/emenu_server/target/CloudMenuServer ubuntu@54.201.188.91:/var/lib/tomcat7/webapps/ROOT
scp /home/zhou/workspace/hummingbird/emenu_server/lib/thrift.jar ubuntu@54.201.188.91:/var/lib/tomcat7/webapps/ROOT/WEB-INF/lib/
scp /home/zhou/workspace/hummingbird/emenu_server/lib/sqlite/libsqlite4java-linux-amd64.so ubuntu@54.201.188.91:/var/lib/tomcat7/webapps/ROOT/WEB-INF/lib/

mkdir /var/lib/sqlite
chmod a+w /var/lib/sqlite
