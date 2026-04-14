pipeline {

agent any

environment {
        DOCKER_TOKEN=credentials('github-ssh')
        DOCKER_USER='alexzarkalis'
        DOCKER_SERVER='ghcr.io'
        DOCKER_PREFIX='ghcr.io/alexzarkalis/realEstate'
    }


stages {

    stage('Checkout local code') {
        steps {
            sh '''
                rm -rf * .git || true
                cp -r /tmp/realEstate/. .
            '''
        }
    }


    stage('run ansible pipeline') {
        steps {
            build job: 'job1'
        }
    }

    stage('Test') {
        steps {
            sh '''
                echo "Start testing"
                chmod +x mvnw
                ./mvnw test
            '''
        }
    }

    stage('Docker build and push') {
            steps {
                sh '''
                    HEAD_COMMIT=$(git rev-parse --short HEAD)
                    TAG=$HEAD_COMMIT-$BUILD_ID
                    docker build --rm -t $DOCKER_PREFIX:$TAG -t $DOCKER_PREFIX:latest -f Dockerfile .
                '''

                sh '''
                    echo $DOCKER_TOKEN | docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
                    docker push $DOCKER_PREFIX --all-tags
                '''
            }
        }
}

}
