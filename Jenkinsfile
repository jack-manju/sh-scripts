pipeline {
    agent any
	options {
		disableConcurrentBuilds() 
		timestamps ()
        lock resource: 'shared_resource_lock'
}
    stages {
        stage('wait') { 
            steps {
				sh 'sleep 300'
              
             }   
       }
	  }
	 }
	 