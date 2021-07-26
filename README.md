The jar file external property source when placed in ${catalina.home}/lib has a java class that allows
Tomcat's ${catalina.base}/conf/server.xml to use environment variable through an external property file(s)
located in ${catalina.base}/conf (i.e ${catalina.base}/conf/<any>.properties).

To compile
----------
- set CLASSPATH="$CLASSPATH:./bin:./lib"
mkdir build

${JAVA_HOME/javac ./org/tomcat/external/source/*.java
${JAVA_HOME/jar -cvf ../tomcat_externalpropertysource.jar .

To test
-------
- Update ${catalina.base}/conf/catalina.policy and make sure the following line exist
  org.apache.tomcat.util.digester.PROPERTY_SOURCE=org.tomcat.external.source.ExternalPropertySource
  
To debug
--------
- check ${catalina.base}/logs/catalina.out
  Look for line coming from org.tomcat.external.source.ExternalPropertySource:
  
  INFO [main] org.tomcat.external.source.ExternalPropertySource.loadProperties Reading properties from external file: /apps/tomcat/conf
- Switch log to WARN or DEBUG for more info.
