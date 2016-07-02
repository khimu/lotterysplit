mvn clean install -DskipTests=true -Pprod
#scp lotterysplit/target/lotterysplit.war root@107.170.234.144:/var/lib/tomcat7/webapps/lotto.war
scp jetty-pkg/target/lottery.jar root@107.170.234.144:/opt/lottery/
