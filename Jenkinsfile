pipeline {
    agent any
	options {
		disableConcurrentBuilds()
		quietPeriod 480
		timestamps
	}
    stages {
        stage('checkout scm') { 
            steps {
	
             checkout scm
             }   
       }
	  }
	 }
	 