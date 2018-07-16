pipeline {
    agent { label 'local' }
	environment {
		jar_file = 'oneroster-api-1.0.jar'
	}
	
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
				dir("api") {
					sh 'chmod +x gradlew'
					sh './gradlew clean unitTest'
					sh './gradlew clean assemble'
					dir("build/libs") {
						stash includes: jar_file, name: 'oneRoster'
						archiveArtifacts artifacts: '**', fingerprint: true
					}
				}
            }
        }
        stage('Test') {
            steps {
				echo 'Testing...'
				node ('stage') {
					unstash "oneRoster"
					sh 'pkill -f oneroster-api || true'
					script {
						withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
							sh 'nohup java -jar oneroster-api-1.0.jar &'
						}
					}
				}
				dir("api") {
					sh 'sleep 25'
					sh './gradlew clean karateTest -Dkarate.env=stg'
				}
			}
		}
        stage('Deploy') {
            steps {
				unstash "oneRoster"
				sh 'scp oneroster-api-1.0.jar deployment@thenewcarag.com:/usr/springboot/oneroster'
                sh 'ssh deployment@thenewcarag.com'
				sh 'rm  oneroster-api-1.0.jar'
            }
        }
    }
}