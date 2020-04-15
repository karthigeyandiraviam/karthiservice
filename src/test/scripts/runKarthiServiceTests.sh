#!/usr/bin/env bash

JAVA_OPTS="-Djava.util.logging.config.file=src/main/resources/logging.properties -Dserver=localhost -Dport=2222"
CLASSPATH=".:target/test-classes:target/lib/*"
TEST_FILE="src/test/java/com/ddk/karthi/e2etests/test-E2E.xml"
LISTENER="-listener \"org.uncommons.reportng.HTMLReporter,org.uncommons.reportng.JUnitXMLReporter\""
/usr/bin/java ${JAVA_OPTS} -classpath ${CLASSPATH} org.testng.TestNG ${TEST_FILE} ${LISTENER}
