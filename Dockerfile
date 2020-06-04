FROM payara/micro:jdk11
COPY target/beverage-suggester.war $DEPLOY_DIR
