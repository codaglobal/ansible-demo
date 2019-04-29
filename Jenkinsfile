pipeline {
    agent {
        kubernetes {
            label "demo-app-${BUILD_NUMBER}"
            defaultContainer 'jnlp'
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: ansible
    image: dgreenstein/ansible:latest
    command: ['cat']
    tty: true
  resources:
    requests:
      memory: "1024Mi"
      cpu: "300m"
    limits:
      memory: "1024Mi"
      cpu: "300m"
"""
        }
    }
    stages {
        stage('build') {
            steps {
                container('ansible') {
                    sh 'ansible --version'
                }
            }
        }
    }
}