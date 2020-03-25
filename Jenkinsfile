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
           stage('Deploying to Staging2'){
               sh script: "echo t_$tag > /var/lib/jenkins/.latest_cl_dev_tag"
               build job: 'cl_deploy_to_staging2', parameters: [string(name: 'tag_name', value: "t_${tag}")]   
           }
     