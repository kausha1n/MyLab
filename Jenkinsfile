pipeline{
    agent any
    tools{
        maven 'maven-3.8.4'
        jdk 'jdk11'
    }
    stages{
        stage("Build"){
            steps{
                sh "mvn clean package"
            }
        }
        stage("Test"){
            steps{
                echo "Testing"
            }
        }
        stage("Deploy"){
            steps {
                echo "Deploying"
            }
        }
    }
}