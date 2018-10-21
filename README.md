<==================STEPS TO RUN =========================>

PREREQUISITES:
MySQL

STEPS to follow before running application
Navigate to src/main/resources/application.properties and change the Database properties like password and database name

Create a database in your local system and give the same name in src/main/resources/application.properties

Run command gradlew bootRun. (chmod a+x gradlew && ./gradlew bootRun for Linux machines)
Tomcat is up and connected to database.

If you are seeing DDL errors while launching the application then:

Drop the database you created

RUN these two commands in your mysql client

SET GLOBAL default_storage_engine = 'InnoDB';

create database <DATABASE_NAME> character set latin1;

RUN the application again using command gradlew bootRun. (chmod a+x gradlew && ./gradlew bootRun for Linux machines)

The end point URLs for Stages are:


GET
http://localhost:8080/interviewPipeline/stages/

POST
http://localhost:8080/interviewPipeline/stages

{
	"stageName" : "Stage5"
}

PUT
http://localhost:8080/interviewPipeline/stages/{stageID}

{                                   {
	"newPosition" : 1      or            "newName" : "StageX"
}                                   }

DELETE
http://localhost:8080/interviewPipeline/stages/{stageId}





The end point URLs for Interviews are:

GET
http://localhost:8080/interviewPipeline/stages/{stageId}/interviews/

POST
http://localhost:8080/interviewPipeline/stages/{stageId}/interviews

{
	"interviewName" : "Interview1"
}

PUT
http://localhost:8080/interviewPipeline/stages/{stageId}/interviews/{interviewId}

{                                   {
	"newPosition" : 1      or            "newName" : "InterviewX"
}                                   }


DELETE
http://localhost:8080/interviewPipeline/stages/{stageId}/interviews/{interviewId}


By Default 2 tables are created in Database by names "Offered" and "Hired".


Please enter correct values in url path so as to avoid any NullPointerException as all the corner cases aren't handled here.