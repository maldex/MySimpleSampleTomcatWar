# Eclipse: add 'Dynamic Web Project' and war-file export
See this [Eclipse Dynamic Web Project missing / Java EE Tools not available + Installing External Tools](https://www.youtube.com/watch?v=zV3HhOMbD_o)
- Help -> install new software -> Work with: [luna|photon|whatever]  (open and select drop-down)
- select category Web, XML, Java EE and OSGi Enterprise Development
  - Eclipse Java EE Developer Tools
  - Eclipse Java Web Developer Tools
  - Eclipse Web Developer Tools
  - Eclipse SML Editors and Tools

# Eclipse: start new JServlet project
- File -> New -> Project
  - Web -> Dynamic Web Project
>
*Note*:
- you need to include a variant of your 'servlet-api-tomcat-x.y.z.jar' in your classpath to resolve classes like _javax.servlet.http.HttpServlet_. see [.classpath](.classpath)
>
# Eclipse: export to War file
TODO: improve this, there must be a better way
- Select your project lefthand in the _project explorer_
- right-click, _export project_ 
  - Web -> WAR file

# Eclipse: deploy from eclipse
TODO: improve this, there must be a better way
