mvn clean install package -DskipTests=true
java -jar lotterysplit/target/dependency/jetty-runner.jar lotterysplit/target/lottery.war 
