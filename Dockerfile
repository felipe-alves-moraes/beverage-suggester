FROM payara/micro:5-SNAPSHOT
COPY target/beverage-suggester.war $DEPLOY_DIR
