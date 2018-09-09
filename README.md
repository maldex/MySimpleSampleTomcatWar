# My Simple Sample Tomcat War Project
This project aims a slight bit further than apache's [sample application](https://tomcat.apache.org/tomcat-7.0-doc/appdev/sample/), including some debugging and a jdbc example. Just as much as my humble java allowed. 

## goals
- A deployable .war-file, the same deployment format as found in various enterprise release pipelines.
- A display of environment variables that are actually inherited to the JVM and eventually to the application.
- A set of commands to be run against the the host. 
- A demonstration/checker for JDBC datasources.

# get started in ecliplse
- File -> Import -> git -> Projects from git -> clone project
- unzip the dist/lib/libs.zip, this contains JDBC drivers.
- Select Project -> right-click properties
- Java Build Path ->  Libraries -> add your variant of your _servlet-api.jar_ for your tomcat.

# get started inside tomcat
TODO: document how to install into a classic tomcat. Manager-ui?
