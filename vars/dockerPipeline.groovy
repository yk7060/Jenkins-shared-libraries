// vars/dockerPipeline.groovy

def call(Map config = [:]) {
    pipeline {
        agent { label config.agent ?: 'any' }

        environment {
            IMAGE_NAME = config.imageName ?: 'techwithyogesh/frontend'
            IMAGE_TAG = config.imageTag ?: 'latest'
        }

        stages {
            stage('Checkout') {
                steps {
                    git url: config.repo ?: 'https://github.com/yk7060/qs-web.git'
                }
            }

            stage('Build Docker Image') {
                steps {
                    echo "Building Docker image..."
                    sh "docker build -t ${env.IMAGE_NAME}:${env.IMAGE_TAG} ."
                }
            }

            stage('Login to Docker Hub') {
                steps {
                    withCredentials([usernamePassword(credentialsId: config.dockerCredId ?: 'dockerhub-cred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    }
                }
            }

            stage('Push Docker Image') {
                steps {
                    sh "docker push ${env.IMAGE_NAME}:${env.IMAGE_TAG}"
                }
            }
        }

        post {
            always {
                echo "Cleaning up..."
                sh 'docker logout'
            }
        }
    }
}
