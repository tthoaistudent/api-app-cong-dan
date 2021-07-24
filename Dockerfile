# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
FROM openjdk:11
ADD build/libs/svc-basepad-1.0.0.jar svc-basepad-1.0.0.jar
EXPOSE 8080
EXPOSE 5701
ENTRYPOINT ["java", "-jar", "svc-basepad-1.0.0.jar"]