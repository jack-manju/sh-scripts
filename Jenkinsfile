pipeline {
    agent any
    triggers {
        githubPullRequests events: [Open()], preStatus: true, skipFirstRun: true, spec: 'H/20 * * * *', triggerMode: 'CRON'
    }
    stages {
        stage('checkout scm') { 
            steps {
             checkout scm
             }   
       }
	  }
	 }