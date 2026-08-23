pipeline {
    agent any
    stages {
        stage ("pull"){
            steps {
                git 'https://github.com/cloudmaster2025/sonar.git'            
            }
        }
        
        stage ("build "){
            steps {
                  sh '/opt/apache-maven-3.9.16/bin/mvn clean package'         
                }
        }
        
        stage ("test"){
            steps {
               sh ''' sudo -u jenkins /opt/apache-maven-3.9.16/bin/mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \  
                     -Dsonar.projectKey=pipeline \ 
                     -Dsonar.projectName='pipeline' \   
                     -Dsonar.host.url=http://54.198.6.54:9000 \   
                     -Dsonar.token=sqp_efc9f9390bb0530130ea5d8ac1f8bfca729aadc0'''            
                
            }
        }
    
        stage ("deploy") {
            steps {
                echo 'deployment complete'
            }
        }
    }
}
