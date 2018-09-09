
# Eclipse: add 'Dynamic Web Project' and war-file export
[Eclipse Dynamic Web Project missing / Java EE Tools not available + Installing External Tools](https://www.youtube.com/watch?v=zV3HhOMbD_o)
- Help -> install new software -> Work with: [luna|photon|whatever]  (open and select drop-down)
- select category Web, XML, Java EE and OSGi Enterprise Development
  - Eclipse Java EE Developer Tools
  - Eclipse Java Web Developer Tools
  - Eclipse Web Developer Tools
  - Eclipse SML Editors and Tools

# Eclipse: start new JServlet project
- File -> New -> Project
  - Web -> Dynamic Web Project

*Note*:
- you need to include a variant of 'servlet-api-tomcat-8.5.33.jar' in your classpath

# Eclipse: export to War file
TODO: improve this, there must be a better way
- Select your project lefthand in project explorer
- right-click, export project 
  - Web -> WAR file

# Eclipse: deploy from eclipse
TODO: improve this, there must be a better way
