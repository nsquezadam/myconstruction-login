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
       withCredentials([usernamePassword(credentialsId: 'trial_credencial_token', usernameVariable: 'JFROG_USER', passwordVariable: 'JFROG_PASS')]) {
           bat """
               mvn -B -U -DskipTests deploy \
               -s C:/Users/nsque/.m2/settings.xml \
               -Dusername=%JFROG_USER% \
               -Dpassword=%JFROG_PASS%
           """
       }

       bat 'mvn -B -U -DskipTests -s C:\\Windows\\System32\\config\\systemprofile\\.m2\\settings.xml clean deploy'
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
            url: 'https://trialgzc17e.jfrog.io/artifactory',
            credentialsId: 'trial-jfrog'
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
