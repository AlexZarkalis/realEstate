pipeline {

  agent any

  environment {
    GHCR_TOKEN = credentials('ghcr-token')
    DOCKER_USER = 'alexzarkalis'
    DOCKER_SERVER = 'ghcr.io'
    DOCKER_PREFIX = 'ghcr.io/alexzarkalis/realestate'
    JENKINS_CONFIG_WORKSPACE = "${JENKINS_HOME}/workspace/job2/04-microk8s-devops-pipeline"
  }

  stages {

    stage('Prepare Infrastructure Code') {
      steps {
        build job: 'job2'
      }
    }

    stage('Test') {
      steps {
        sh '''
          echo "Running unit tests..."
          ./mvnw test
        '''
      }
    }

    stage('Setup Ansible') {
      steps {
        sh '''
          # Install pip3 if not present
          if ! command -v pip3 &> /dev/null; then
            sudo apt-get update
            sudo apt-get install -y python3-pip
          fi

          # Install ansible if not present
          if ! command -v ansible-playbook &> /dev/null; then
            sudo pip3 install --break-system-packages ansible
          fi

          # Install required Python libraries
          sudo pip3 install --break-system-packages kubernetes

          # Install Ansible collection for Kubernetes
          ansible-galaxy collection install kubernetes.core
        '''
      }
    }

    stage('Setup Docker Infrastructure') {
      steps {
        sh '''
          cd ${JENKINS_CONFIG_WORKSPACE}
          ansible-playbook -i hosts.yaml playbooks/install-docker.yaml
        '''
      }
    }

    stage('Build and Push Image') {
      steps {
        script {
          env.HEAD_COMMIT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
          env.TAG = "${env.HEAD_COMMIT}-${env.BUILD_ID}"
        }
        sh '''
          echo "Building Docker image..."
          sudo docker build --rm -t $DOCKER_PREFIX:$TAG -t $DOCKER_PREFIX:latest -f Dockerfile .
        '''
        sh '''
          echo "Pushing image to registry..."
          echo $GHCR_TOKEN | sudo docker login $DOCKER_SERVER -u $DOCKER_USER --password-stdin
          sudo docker push $DOCKER_PREFIX --all-tags
        '''
      }
    }

    stage('Deploy to MicroK8s') {
      steps {
        sh '''
          HEAD_COMMIT=$(git rev-parse --short HEAD)
          TAG=$HEAD_COMMIT-$BUILD_ID
          cd ${JENKINS_CONFIG_WORKSPACE}

          # Setup kubeconfig for Jenkins user
          mkdir -p ~/.kube
          rm -f ~/.kube/config
          microk8s config > ~/.kube/config
          chmod 600 ~/.kube/config

          echo "Deploying to MicroK8s with image tag: $TAG"
          ansible-playbook -i hosts.yaml -e image_tag=$TAG playbooks/microk8s-deployment.yaml
        '''
      }
    }

  }

}