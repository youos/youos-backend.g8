FROM openjdk:8-alpine

ADD target/scala-2.12/$name$.jar /usr/share/$name$/$name$.jar
ADD src/main/resources/*.* /usr/share/$name$/resources/
ADD src/main/resources/emails /usr/share/$name$/resources/emails

WORKDIR /usr/share/$name$/

ENTRYPOINT ["java", "-XX:+UseConcMarkSweepGC", "-Dfile.encoding=UTF8", "-jar", "$name$.jar"]