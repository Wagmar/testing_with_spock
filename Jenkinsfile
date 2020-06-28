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
                always {
                    junit 'build/test-results/test/*.xml'
                }
                success {
                    script {
                        def props = readProperties file: 'build/resources/main/META-INF/build-info.properties'
                        currentBuild.description = props['build.version']
                    }
                }
            }
        }

        stage('Code Coverage') {
            steps {
                gitlabCommitStatus(name: 'jacoco') {
                    step([$class: 'JacocoPublisher', execPattern: 'build/jacoco/test.exec',
                            classPattern: 'build/classes', sourcePattern: 'src/main/java'])
                }
            }
        }

       stage('Static Code Analysis') {
                   steps {
                       gitlabCommitStatus(name: 'sonarqube') {
                           withSonarQubeEnv('SonarQube Server') {
                               sh './gradlew --info --stacktrace sonarqube -x test'
                           }
                           timeout(time: 5, unit: 'MINUTES') {
                               script {
                                   def qualitygate = waitForQualityGate()
                                    if (qualitygate.status != "OK") {
                                        error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"
                                    }
                               }
                           }
                       }
                   }
               }

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