# My Simple Sample Tomcat War Project
This project aims a slight bit further than apache's [sample application](https://tomcat.apache.org/tomcat-7.0-doc/appdev/sample/), including some debugging and a jdbc example. Just as much as my humble java allowed.

## project goals
- A deployable .war-file, the same deployment format as found in various enterprise release pipelines.
- A display of environment variables that are actually inherited to the JVM and eventually to the application.
- A set of commands to be run against the the host. 
- A demonstration/checker for JDBC datasources.
- [A sample how to create a Docker image of your application embedded.](dist/Docker)

# get started in eclipse
- go to your favorite git-clone place, clone this repo
- unzip the contents of _dist/lib/libs.zip_ (JDBC drivers and variant of Servlet-API.jar)
- open eclipse, ensure you have the [Java Web Devel](README.eclipse.md) stuff installed.
- Create new Project, _Web_ -> _Dynamic Web Project_
- untick _Use default location_, specify the clone directory here.
- tick _Generate web.xml deployment descriptor_
- Select Project -> right-click properties
- Java Build Path ->  Libraries -> add your variant of your _servlet-api.jar_ for your tomcat.

# get started inside tomcat
TODO: document how to install into a classic tomcat. Manager-ui?
