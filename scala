# statistics , nonce and tomcat embedded
#
# Takipi
#
# Installs and runs Takipi with a Java tester

# Pull base image.
FROM java:7

MAINTAINER Chen Harel "https://github.com/chook"

# Needed for Ubuntu 15.10, October 2015
RUN update-ca-certificates -f

# Get Takipi for containers
RUN echo "deb [arch=amd64] http://takipi-tfc-deb-repo.s3.amazonaws.com stable main" > /etc/apt/sources.list.d/takipi.list
RUN wget -O - http://s3.amazonaws.com/takipi-tfc-deb-repo/hello%40takipi.com.gpg.key | apt-key add -
RUN apt-get update
RUN apt-get install -y takipi
ENV PATH $PATH:/opt/takipi/bin
ENV TAKIPI_HEAP_SIZE 30m


# Setup Takipi key and name
RUN /opt/takipi/etc/takipi-setup-secret-key S13136#hQQtJMeUoKi4C/P/#NeahtjCVpNVHo5AaZWkYezWKlxiHD8JShqYWvmqpwdU=#66cc
RUN /opt/takipi/etc/takipi-setup-machine-name scala-docker

# Getting Java tester
RUN cd /opt ; wget https://s3.amazonaws.com/app-takipi-com/chen/scala-boom.jar -O scala-boom.jar
#RUN cd /opt ; git clone https://github.com/hodayagamliel/statistics-tester-java.git
#RUN cd /opt ; git clone https://github.com/hodayagamliel/simple-exception.git


# Running run_testers bash who run Java-statistics and Scala-boom with Takipi agent
CMD  (java -agentlib:TakipiAgent -jar /opt/scala-boom.jar) # && \
#(cd /opt ; javac ThrowExcep.java ;java -agentlib:TakipiAgent ThrowExcep)
