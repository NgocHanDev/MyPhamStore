## Sử dụng image Tomcat chính thức
#FROM tomcat:10.0-jdk17-temurin
#
## Xóa các ứng dụng mặc định của Tomcat (nếu cần)
#RUN rm -rf /usr/local/tomcat/webapps/*
#
## Copy file ROOT.war vào thư mục webapps của Tomcat
#COPY build/libs/ROOT.war /usr/local/tomcat/webapps/
#
## Expose cổng 8080
#EXPOSE 8080
#
## Khởi động Tomcat
#CMD ["catalina.sh", "run"]