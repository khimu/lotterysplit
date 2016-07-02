java -Djetty.port=58080 -Dlog4j.configuration=jar:file:///Users/khimung/Documents/lotterysplit/jetty-pkg/target/com.lottery.split.jar\!/WEB-INF/resources/log4j.xml  -jar com.lottery.split.jar  start

# with a custom log4j file in a jar file
# java -Djetty.port=58080 -Dlog4j.configuration=jar:file:///Users/spicysquid/gogii/chatplus/userservice/jetty-pkg/target/userservice-1.0.0.jar\!/WEB-INF/classes/log4j.xml  -jar userservice-1.0.0.jar  start

# with a cutom log4j at a certain location
# java -Djetty.port=58080 -Dlog4j.configuration=file:///Users/spicysquid/gogii/chatplus/userservice/jetty-pkg/target/log4j.xml -jar userservice-1.0.0.jar

