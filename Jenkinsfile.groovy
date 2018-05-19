def persistenceService = new Structure(
        "smart-roads",
        "build/Dockerfile",
        8000)

def modules = [
        persistenceService
]

def dockerHub = 'gleblubchenko'

pipeline {
    agent any

    stages {
        stage("build maven") {
            steps {
                withMaven(maven: 'maven3') {
                    sh 'mvn -B -Dmaven.test.skip=true clean package'
                }
            }
        }

        stage('docker-build') {
            steps {
                script {
                    for (int i = 0; i < modules.size(); i++) {
                        sh 'docker build -t ' + dockerHub + '/' + modules[i].serviceName + ' -f ' + modules[i].dockerParams + ' .'
                    }
                }
            }
        }

//        stage('docker-push') {
//            steps {
//                script {
//                    for (int i = 0; i < modules.size(); i++) {
//                        sh 'docker push ' + dockerHub + '/' + modules[i].serviceName
//                    }
//                }
//            }
//        }

        stage('deploy') {
            steps {
                script {
                    for (int i = 0; i < modules.size(); i++) {
//                        pullCommand += 'docker pull ' + dockerHub + '/' + modules[i].serviceName + ':google;'
                        sh 'docker run --net=host -p ' + modules[i].port + ':' + modules[i].port + ' ' + dockerHub + '/' + modules[i].serviceName + '&'
                    }
                }
            }
        }
    }
}

class Structure {

    String serviceName
    String dockerParams
    Integer port

    Structure(String serviceName, String dockerParams, Integer port) {
        this.dockerParams = dockerParams
        this.serviceName = serviceName
        this.port = port
    }
}