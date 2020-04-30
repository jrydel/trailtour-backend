pipeline {
  agent any

  stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'cp target/trailtour.war /data/tomcat/webapps'
            }
        }
    }
}
