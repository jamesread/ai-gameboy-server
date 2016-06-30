node {
	stage "Build"
	checkout scm
	sh "./installLibToLocalRepo.py"
	sh "mvn test"
}
