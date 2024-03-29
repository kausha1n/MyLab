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
                sh "mvn clean package -Dmaven.test.skip"
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
                            file: "target/${ArtifactId}-${Version}.war",
                            type: "war"
                        ]],
                        credentialsId: '56d4602f-0a78-4ace-968a-019561a2ca02',
                        groupId: "${GroupId}", 
                        nexusUrl: '172.20.10.226:8081', nexusVersion: 'nexus3', 
                        protocol: 'http', 
                        repository: "${NexusRepo}", 
                        version: "${Version}"
                }
                
            }
        }

        // stage("Deploy"){
        //     steps {
        //         sshPublisher(publishers: [sshPublisherDesc(
        //             configName: 'AnsibleController',
        //             transfers: [sshTransfer(
        //                 cleanRemote: false,
        //                 execCommand: 'ansible-playbook /opt/playbooks/downloadanddeploy.yaml -i /opt/playbooks/hosts', 
        //                 execTimeout: 120000, 
        //             )],
        //             usePromotionTimestamp: false, 
        //             useWorkspaceInPromotion: false,
        //             verbose: false)])
        //     }
        // }

        stage("Deploy to Docker"){
            steps {
                sshPublisher(publishers: [sshPublisherDesc(
                    configName: 'AnsibleController',
                    transfers: [sshTransfer(
                        cleanRemote: false,
                        execCommand: 'ansible-playbook /opt/playbooks/downloadanddeploy.yaml -i /opt/playbooks/hosts', 
                        execTimeout: 120000, 
                    )],
                    usePromotionTimestamp: false, 
                    useWorkspaceInPromotion: false,
                    verbose: false)])
            }
        }

        stage("Sonarqube scan") {
            steps {
                echo "Source code published to sonarqube for static code analysis"
                withSonarQubeEnv('sonarqube'){
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}