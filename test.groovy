Skip to content
 
Search…
All gists
Back to GitHub
@parmar-gaurav 
@eriadam eriadam/example-jenkinsfile-pipeline.groovy
Last active 10 days ago • Report abuse
3
5
 Code Revisions 4 Stars 3 Forks 5
<script src="https://gist.github.com/eriadam/e20583d63e561587c727be629622acc1.js"></script>
  
example-jenkinsfile-pipeline.groovy
 example-jenkinsfile-pipeline.groovy
#!groovy

pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Dependecies') {
      steps {
        sh '/usr/local/bin/pod install'
      }
    }

    stage('Running Tests') {
      steps {
        parallel (
          "Unit Tests": {
            sh 'echo "Unit Tests"'
            sh 'fastlane scan'
          },
          "UI Automation": {
            sh 'echo "UI Automation"'
          }
        )
      }
    }

    stage('Documentation') {
      when {
        expression {
          env.BRANCH_NAME == 'develop'
        }
      }
      steps {
        // Generating docs
        sh 'jazzy'
        // Removing current version from web server
        sh 'rm -rf /path/to/doc/ios'
        // Copy new docs to web server
        sh 'cp -a docs/source/. /path/to/doc/ios'
      }
    }
  }

  post {
    always {
      // Processing test results
      junit 'fastlane/test_output/report.junit'
      // Cleanup
      sh 'rm -rf build'
    }
    success {
      notifyBuild()
    }
    failure {
      notifyBuild('ERROR')
    }
  }
}

// Slack notification with status and code changes from git
def notifyBuild(String buildStatus = 'SUCCESSFUL') {
  buildStatus = buildStatus

  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def changeSet = getChangeSet()
  def message = "${subject} \n ${changeSet}"

  if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  slackSend (color: colorCode, message: message)
}

@NonCPS

// Fetching change set from Git
def getChangeSet() {
  return currentBuild.changeSets.collect { cs ->
    cs.collect { entry ->
        "* ${entry.author.fullName}: ${entry.msg}"
    }.join("\n")
  }.join("\n")
}
@parmar-gaurav
 
Leave a comment

Attach files by dragging & dropping, selecting or pasting them.
© 2020 GitHub, Inc.
Terms
Privacy
Security
Status
Help
Contact GitHub
Pricing
API
Training
Blog
About
