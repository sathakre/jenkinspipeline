pipeline {
    agent any

    stages {

        stage("pull") {
            steps {
                git 'https://github.com/cloudmaster2025/sonar.git'
            }
        }

        stage("build") {
            steps {
                sh '/opt/apache-maven-3.9.16/bin/mvn clean package'
            }
        }

        stage("test") {
            steps {
		withSonarQubeEnv(installationName: 'sonar' , credentialsId: 'sqube') {
                sh '''
                    /opt/apache-maven-3.9.16/bin/mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                    -Dsonar.projectKey=pipeline \
                    -Dsonar.projectName=pipeline \
                '''
            }
	}
        }

        stage("deploy") {
            steps {
                echo 'deployment complete'
            }
        }
    }
}
