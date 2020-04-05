FROM store/oracle/serverjre:1.8.0_241-b07
RUN yum install curl libcurl libcurl-devel sqlite sqlite-devel sqlite-doc sqlite-tcl lemon -y
ENV artifact karthi-service-1.0.jar
WORKDIR /app
COPY ./target/${artifact} /app
COPY ./target/lib/ /app/lib/
EXPOSE 2222
ENTRYPOINT ["sh", "-c"]
CMD ["java -Xms1024m -Xmx1024m -classpath \"lib/*:${artifact}\" com.ddk.karthi.server.StartServer"]

