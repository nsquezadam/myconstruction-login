pipeline {
  agent any
  options { timestamps() }
  tools {
    maven 'maven-3.9'   // Global Tool Configuration
    jdk   'jdk-17'       // Global Tool Configuration
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
        // Empaqueta fat-jar (jar-with-dependencies) según tu POM
        bat 'mvn -B -U -DskipTests clean package'
      }
    }

    stage('Archive artifact') {
      steps {
        // Asegúrate que coincide con el nombre generado en target/
        archiveArtifacts artifacts: 'target/myconstruction-jar-with-dependencies.jar', fingerprint: true
      }
    }

    stage('Publish to Artifactory') {
      steps {
        script {
          // Usa la instancia configurada en Manage Jenkins → Configure System → Artifactory
          def server = Artifactory.server('local-jfrog')

          // Info de build (quedará asociado en Artifactory)
          def buildInfo = Artifactory.newBuildInfo()
          buildInfo.env.capture = true

          // Sube el jar al repo de releases
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
