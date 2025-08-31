pipeline {
  agent any
  options { timestamps() }
  tools {
    maven 'maven-3.9'
    jdk   'JDK17'
  }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
        bat 'git rev-parse HEAD'
      }
    }
    stage('Build (Maven)') {
      steps {
        bat 'mvn -B -U -DskipTests clean package'
      }
    }
    stage('Archive artifact') {
      steps {
        // Según el <finalName> del pom.xml
        archiveArtifacts artifacts: 'target/myconstruction-jar-with-dependencies.jar', fingerprint: true
      }
    stage('Publish to Artifactory') {
      steps {
        script {
          def server = Artifactory.server('local-jfrog') // nombre configurado en Jenkins
          def buildInfo = Artifactory.newBuildInfo()

          // Sube el JAR al repo de releases
          def uploadSpec = """{
            "files": [{
              "pattern": "target/myconstruction-jar-with-dependencies.jar",
              "target": "libs-release-local/myconstruction/"
            }]
          }"""
          server.upload(spec: uploadSpec, buildInfo: buildInfo)

          server.publishBuildInfo(buildInfo)
        }
      }
    }

    }
  }
}
