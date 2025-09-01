pipeline {
  agent any
  options { timestamps() }
  tools {
    maven 'maven-3.9'
    jdk   'jdk-17'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
        bat 'git rev-parse HEAD'
      }
    }

    stage('Build & Deploy with Artifactory Plugin') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'trial_credencial_token', usernameVariable: 'JFROG_USER', passwordVariable: 'JFROG_PASS')]) {
          bat """
            dir
            mvn -B -U -DskipTests -s C:/Users/nsque/.m2/settings.xml deploy
          """
        }
      }
    }

    stage('Archive artifact') {
      steps {
        archiveArtifacts artifacts: 'target/myconstruction-jar-with-dependencies.jar', fingerprint: true
      }
    }
  }
}
