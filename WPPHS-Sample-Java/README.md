This application contains samples for Website Payments Pro Hosted Solution. 

Prerequisites:
---------------
*	Java jdk-1.5 or higher
*	Apache Maven 3 or higher
* Please refer to the [maven tutorial](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) if you are new to maven.

To build and run this application:
----------------------------------

* Update the credentials in 'sdk_config.properties' in the 'src/main/resources' directory with your API credentials.
*	Run `mvn install` to build war file.
* Run `mvn jetty:run` to run the war file.
*	Access `http://localhost:<jetty-port>/paymentspro-0.1/` in your browser to play with the test pages.`<jetty-port>` is configurable in `pom.xml`.

 
