pipeline {

agent any

environment {
        DOCKER_TOKEN=credentials('github-ssh')
        DOCKER_USER='alexzarkalis'
        DOCKER_SERVER='ghcr.io'
        DOCKER_PREFIX='ghcr.io/alexzarkalis/realEstate'
    }


stages {

    stage('Test') {
        steps {
            sh '''
                echo "Start testing"
                ./mvnw test
            '''
        }
    }

    stage('Docker build and push') {
            steps {
                script {
                    env.HEAD_COMMIT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    env.TAG = "${env.HEAD_COMMIT}-${env.BUILD_ID}"
                }
                sh '''
                    docker build --rm -t $DOCKER_PREFIX:$TAG -t $DOCKER_PREFIX:latest -f Dockerfile .
                '''
                sh '''
                    echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
                    docker push $DOCKER_PREFIX --all-tags
                '''
            }
        }

    stage('Prepare Infrastructure Code') {
        steps {
            build job: 'job2'
        }
    }

    stage('Deploy with Ansible') {
        steps {
            sh '''
                JENKINS_CONFIG_WORKSPACE=${JENKINS_HOME}/workspace/job2
                cd ${JENKINS_CONFIG_WORKSPACE}
                ansible-playbook -i hosts.yaml playbooks/deploy-with-docker.yaml \
                    --extra-vars "image_tag=${TAG} docker_token=${DOCKER_TOKEN} docker_user=${DOCKER_USER} docker_server=${DOCKER_SERVER}"
            '''
        }
    }
}

}