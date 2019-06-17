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
    image: sponmuth/maven-npm:latest
    command: ['cat']
    tty: true
  - name: mysql
    image: mysql:5.7
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
        stage('test') {
          steps {
            container('ansible') {
              sh 'ansible-playbook playbooks/database.yml'
            }
          }
        }
    }
}
