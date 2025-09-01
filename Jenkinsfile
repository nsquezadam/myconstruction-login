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

    stage('Build') {
      steps {
        bat """
          mvn -B -U -DskipTests clean package -s C:/Users/nsque/.m2/settings.xml
        """
      }
    }

    stage('Archive artifact') {
      steps {
        archiveArtifacts artifacts: 'target/myconstruction-jar-with-dependencies.jar', fingerprint: true
      }
    }

    stage('Publish to Artifactory') {
      steps {
        script {
          def server = Artifactory.newServer(
            url: 'https://trialgzc17e.jfrog.io',
            credentialsId: 'trial_credencial_token'
          )

          def buildInfo = Artifactory.newBuildInfo()
          buildInfo.env.capture = true

          def uploadSpec = """{
            "files": [{
              "pattern": "target/myconstruction-jar-with-dependencies.jar",
              "target": "libs-snapshot-local/myconstruction/",
              "props": "project=myconstruction;env=dev"
            }]
          }"""

          server.upload(spec: uploadSpec, buildInfo: buildInfo)
          server.publishBuildInfo(buildInfo)
        }
      }
    }
  }
}
