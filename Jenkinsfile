pipeline{
    agent any
    tools{
        maven 'maven-3.8.4'
        jdk 'jdk11'
    }
    environment {
        GroupId = readMavenPom().getGroupId()
        ArtifactId = readMavenPom().getArtifactId()
        Version = readMavenPom().getVersion()
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

        stage("Print Vars") {
            steps {
                echo "GroupId: ${GroupId}"
                echo "ArtifactId: ${ArtifactId}"
                echo "Version: ${Version}"
            }
        }

        stage("Publish to Nexus"){
            steps {
                script {
                    String NexusRepo = Version.endsWith("SNAPSHOT") ? "MyLab-SNAPSHOT": "MyLab-RELEASE"
                    nexusArtifactUploader artifacts: [[
                            artifactId: "${ArtifactId}",
                            classifier: "",
                            file: "target/${ArtifactId}-${Version}.jar",
                            type: "jar"
                        ]],
                        credentialsId: '3eecb0ec-0c6d-4ee2-ac91-c89b8e8f549c',
                        groupId: "${GroupId}", 
                        nexusUrl: '172.20.10.106:8081', nexusVersion: 'nexus3', 
                        protocol: 'http', 
                        repository: "${NexusRepo}", 
                        version: "${Version}"
                }
                
            }
        }

        stage("Deploy"){
            steps {
                echo "Deploying"
            }
        }
    }
}