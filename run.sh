mvn clean install -DskipTests=true -Pprod
#scp lotterysplit/target/lotterysplit.war root@107.170.234.144:/var/lib/tomcat7/webapps/lotto.war

# this wont work on digitalocean 
#scp jetty-pkg/target/lottery.jar root@107.170.234.144:/opt/lottery/

# switching to this and hopefully will work on digitalocean
scp lotterysplit/target/lottery.war root@107.170.234.144:/opt/lottery/lottery.war
scp lotterysplit/target/dependency/jetty-runner.jar root@107.170.234.144:/opt/lottery/jetty-runner.jar
