
pipeline {
    agent any

    options {
        disableConcurrentBuilds()
      //  lock resource: 'shared_resource_lock'
    }
    
    stages {
            stage('Checkout'){
                steps {
                     script {
                        System.setProperty("org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL", "3800");
                        }
                         checkout([$class: 'GitSCM', branches: [[name: 'feature/test4']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://scm.cusa.canon.com/Q14705/multibranch_test.git']]])
                         
                    }
               }
            stage('changeset details') 
              {
                steps  {
                       getChangeString() 
                }
              }
          //  stage ('publish to confluence') {
          //      steps {
          //          sh (' cd ${WORKSPACE}')
                     
          //          publishConfluence editorList: [confluenceWritePage(confluenceFile('/opt/canon/test_canon/canon-dev/recentcommit.txt'))], pageName: 'Test', siteName: 'canon.go2group.com', spaceName: 'MCI'
          //  }
        }
    } 
@NonCPS
def getChangeString() {
    MAX_MSG_LEN = 100
    def changeString = ""

    echo "Gathering SCM changes"
    def changeLogSets = currentBuild.rawBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            truncated_msg = entry.msg.take(MAX_MSG_LEN)
            changeString += " - ${truncated_msg} [${entry.author}]\n"
        }
    }

    if (!changeString) {
        changeString = " - No new changes"
    }
    println changeString
}

