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

   stage('Build & Deploy (Maven)') {
     steps {
       bat 'mvn -B -U -DskipTests -s C:\\ProgramData\\Jenkins\\.m2\\settings.xml clean deploy'
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
            url: 'http://localhost:8082/artifactory',
            credentialsId: 'local-jfrog'
          )
          server.connection.timeout = 300  // opcional

          def buildInfo = Artifactory.newBuildInfo()
          buildInfo.env.capture = true

          def uploadSpec = """{
            "files": [{
              "pattern": "target/myconstruction-jar-with-dependencies.jar",
              "target": "libs-release-local/myconstruction/",
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
