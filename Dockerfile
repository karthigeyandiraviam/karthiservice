FROM store/oracle/serverjre:1.8.0_241-b07
ENV artifact karthi-service-1.0-jar-with-dependencies.jar
WORKDIR /app
COPY ./target/${artifact} /app
EXPOSE 2222
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar ${artifact}"]

