pipeline {
    agent any

    tools {
        jdk 'OpenJDK 1.8'
    }

    options {
        timestamps()
        gitLabConnection('congit')
        gitlabBuilds(builds: ['build', 'jacoco','sonarqube', 'publish'])
    }

    stages {

        stage('Build') {

            steps {
                gitlabCommitStatus(name: 'build') {
                    sh './gradlew --info --stacktrace clean build'
                }
            }

            post {
//                 always {
//                     junit 'target/surefire-reports/*.xml'
//                 }
                success {
                    script {
                        def props = readProperties file: 'target/classes/custom.properties'
                        currentBuild.description = props['app.version']
                    }
                }
            }
        }

        stage('Code Coverage') {
            steps {
                gitlabCommitStatus(name: 'jacoco') {
                    step([$class: 'JacocoPublisher', execPattern: 'target/coverage-reports/jacoco-unit.exec',
                            classPattern: 'target/classes', sourcePattern: 'src/main/java'])
                }
            }
        }

//        stage('Static Code Analysis') {
//                    steps {
//                        gitlabCommitStatus(name: 'sonarqube') {
//                            withSonarQubeEnv('SonarQube Server') {
//                                sh './gradlew --info --stacktrace sonarqube -x test'
//                            }
//                            timeout(time: 5, unit: 'MINUTES') {
//                                script {
//                                    def qualitygate = waitForQualityGate()
//                                     if (qualitygate.status != "OK") {
//                                         error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"
//                                     }
//                                }
//                            }
//                        }
//                    }
//                }
//
        stage('Publish') {
            steps {
                gitlabCommitStatus(name: 'publish') {
                    sh './gradlew --info --stacktrace publish -x jar'
                }
            }
        }
    }
}

// vim: syntax=groovy