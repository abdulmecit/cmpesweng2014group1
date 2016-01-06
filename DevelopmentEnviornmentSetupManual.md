# Development Enviornment Setup Manual #

## 1.Eclipse ##
  * 1.1 Download Eclipse from http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1
## 2.Tomcat with Eclipse ##
  * 2.1  Download Apache Tomcat 8.0 from http://tomcat.apache.org/download-80.cgi (Binary Distributions/Core/zip)
  * 2.2 Unzip the file and copy the resulting folder to C drive in Windows, to /usr/local in Mac.
  * 2.3 Copy the ROOT folder in the apache-tomcat-8.0.12\webapps to your Eclipse workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps folder.
  * 2.4 Start Eclipse. Go to Window>Show View>Other>Server>Servers and click ok. Click create new server link in the Servers view.
  * 2.5 Select Apache Tomcat v8.0 Server. And click Finish.
  * 2.6 Right click to the server that you created, and click Start.
  * 2.7 Open http://localhost:8080/ in your web browser, now you should be able to see Apache Tomcat Welcome page.
  * 2.8 In Windows, if you encounter 404 Error;
    * 2.8.1 Click on Window > Show view > Server OR right click on the server in "Servers" view, select "Properties".
    * 2.8.2 In the "General" panel, click on the "Switch Location" button.
    * 2.8.3 The "Location: [metadata](workspace.md)" should replace by something else.
    * 2.8.4 Open the Overview screen for the server by double clicking it.
    * 2.8.5 In the Server locations tab , select "Use Tomcat location".
    * 2.8.6 Save the configurations and restart the Server.
## 3.Database Connection ##
  * 3.1 In Eclipse, click Open Perspective and choose Database Development and click ok.
  * 3.2 Open Database Development perspective.
  * 3.3 Right click Database Connections and click New.
  * 3.4 For the connection profile type, choose Generic JDBC and click next.
  * 3.5 In order to choose driver, click new driver definition.
  * 3.6 From New Driver Definiton Window, choose Generic JDBC Driver for the Name/Type section. Write Generic JDBC Driver to driver name and type below. For JAR List section, from http://dev.mysql.com/downloads/connector/j/5.1.html choose platform independent and download mysql-connector-java-5.1.33.zip. Unzip the folder. In JAR List section, click add JAR and specify the location of the mysql-connector-java-5.1.30-bin.jar file which is in this unzipped folder. Then go to Properties section. For Driver Class, click … and copy “com.mysql.jdbc.Driver” to Type class name. Click ok for New Driver Definiton window.
  * 3.7 In the New Connection Profile Window, choose the driver you just created as Drivers. Database is database1, URL is MySqlURL of the project, user name and password will be the project username and password given by the assistant. Click Test Connection. It should give ping succeeded message. Then click to finish.
## 4.Automatic documentation support (JavaDoc) ##
  * 4.1 Go to Project section inside Eclipse.
  * 4.2 Create a folder “projectDoc” in the project folder.
  * 4.3 Click Browse for Destination and choose projectDoc folder and click finish.
## 5.Git Repository ##
  * 5.1 Open Eclipse
  * 5.2 Go to Help and than Install New Software
  * 5.3 Choose “All Available Sites” in work with.
  * 5.4 Write “Eclipse Git Team Provider” in filter.
  * 5.5 Install Eclipse Git Team Provider
  * 5.6 Restart Eclipse
  * 5.7 Clone a Git Repository from Google Code
## 6.Create Dynamic Web Project ##
  * 6.1 Open File> New > Dynamic Web Project
  * 6.2 Write 451project in Project Name
  * 6.3 Choose Apache Tomcat v8.0 (Target runtime)
  * 6.4 Choose 3.1 (Dynamic web module version)
  * 6.5 Choose Default Configuration for Apache Tomcat v8.0 (Configuration)
  * 6.6 Create a servlet
  * 6.7 Right click the project
  * 6.8 Select New >Servlet
  * 6.9 Write first.servlet (java package)
  * 6.10 Write HelloWorld (Class Name)
  * 6.11 Edit this function protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {response.setContentType("text/html");PrintWriter pw = response.getWriter(); pw.println("Hello World");}