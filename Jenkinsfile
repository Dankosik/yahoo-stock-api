pipeline {
    agent any
    stages{
        stage('Build project'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Dankosik/yahoo-stock-api']]])
                withGradle(){
                   sh './gradlew clean build'
                }
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t dankosik/yahoo-stock-api .'
                }
            }
        }
        stage('Push image to dockerhub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u dankosik -p ${dockerhubpwd}'
                   }
                   sh 'docker push dankosik/yahoo-stock-api'
                }
            }
        }
    }
}