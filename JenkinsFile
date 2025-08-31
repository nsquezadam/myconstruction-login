pipeline {
  agent any
  options { timestamps() }
  tools { maven 'maven-3.9' }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Ver archivos') {
      steps {
        bat 'dir'
        bat 'git rev-parse HEAD'
      }
    }
  }
}
