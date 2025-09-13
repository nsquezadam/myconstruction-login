pipeline {
  agent any
  options { timestamps() }
  tools {
    maven 'maven-3.9'
    jdk   'jdk-17'
  }

  environment {
    MAVEN_OPTS = '-Xmx512m'
    BRANCH     = 'main'
    REPO_URL   = 'https://github.com/nsquezadam/myconstruction-login.git'
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: "${BRANCH}", url: "${REPO_URL}", credentialsId: 'git-credentials'
        bat 'git rev-parse HEAD'
      }
    }

    stage('Build & Test') {
      steps {
        bat 'mvn -B -U clean test'
      }
      post {
        always { junit '**/target/surefire-reports/*.xml' }
      }
    }

    stage('Package WAR') {
      steps {
        bat 'mvn -B package -DskipTests=false'
      }
      post {
        success {
          archiveArtifacts artifacts: 'target/*.war', fingerprint: true
        }
      }
    }

    stage('Deploy to Artifactory (Maven deploy)') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'artifactory-creds',
                                         usernameVariable: 'ART_USER',
                                         passwordVariable: 'ART_PASS')]) {
          bat """
            echo ^<settings xmlns=\\"http://maven.apache.org/SETTINGS/1.0.0\\"^> > settings.xml
            echo   ^<servers^> >> settings.xml
            echo     ^<server^> >> settings.xml
            echo       ^<id^>artifactory^</id^> >> settings.xml
            echo       ^<username^>%ART_USER%^</username^> >> settings.xml
            echo       ^<password^>%ART_PASS%^</password^> >> settings.xml
            echo     ^</server^> >> settings.xml
            echo   ^</servers^> >> settings.xml
            echo ^</settings^> >> settings.xml

            mvn -B deploy --settings settings.xml -DskipTests=true
          """
        }
      }
    }
  }

  post {
    success { echo 'WAR construido y publicado en Artifactory.' }
    always  { cleanWs() }
  }
}
