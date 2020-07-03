pipeline {
    agent any

    tools {
        jdk 'OpenJDK 1.8'
    }

    options {
        timestamps()
        gitLabConnection('gl-con1')
        gitlabBuilds(builds: ['build', 'jacoco', 'deploy'])
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
                        currentBuild.description = 'v' + props['build.version']
                    }
                }
            }
        }

        stage('Code Coverage') {
            steps {
                gitlabCommitStatus(name: 'jacoco') {
                    step([$class: 'JacocoPublisher', execPattern: 'build/jacoco/test.exec',
                            classPattern: 'build/classes/java', sourcePattern: 'src/main/java'])
                }
            }
        }

//         stage('Static Code Analysis') {
//             steps {
//                 gitlabCommitStatus(name: 'sonarqube') {
//                     withSonarQubeEnv('SonarQube Server') {
//                         sh './gradlew --info --stacktrace sonarqube -x test'
//                     }
//                     timeout(time: 5, unit: 'MINUTES') {
//                         script {
//                             def qualitygate = waitForQualityGate()
//                             if (qualitygate.status != "OK") {
//                                 error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"
//                             }
//                         }
//                     }
//                 }
//             }
//         }

//         stage('Publish') {
//             steps {
//                 gitlabCommitStatus(name: 'publish') {
//                     withCredentials([usernamePassword(credentialsId: 'rctiReleasesRepo',
//                                                       usernameVariable: 'ORG_GRADLE_PROJECT_deployerUsername',
//                                                       passwordVariable: 'ORG_GRADLE_PROJECT_deployerPassword')]) {
//                         sh './gradlew --info --stacktrace publish -x jar'
//                     }
//                 }
//             }
//         }

        stage('deploy') {
            steps {
                gitlabCommitStatus(name: 'deploy') {
                script{
                        def props = readProperties file: 'build/resources/main/META-INF/build-info.properties'
                        def version = props['build.version']
                        def artifactName = props['build.name']
                        println("createControl")
                        createControl(name,version)
                        println("createConf")
                        createConf(name)
                        println("createFolder")
                        createFolder(name,"msa")
                        createFolder(name,"logs")
                        sh "cp build/libs/rcserver* build/deploy/riocard/msa/$artifactName"
                    }
                }
            }
        }
    }

}
    def createControl(String nome, String version){
            println("inicio createControl")
            def dir = new File("build/deploy/DEBIAN/")

            if(!dir.exists()){
                dir.mkdirs()
            }
            dir = new File(dir.absolutePath+"/control")
            dir.write("Package: $nome\n")
            dir.append("Version: $version\n")
            dir.append("Architecture: all\n")
            dir.append("Maintainer: Riocard TI <desenvolvimento@riocardmais.com.br>\n")
            dir.append("Depends: systemd, ca-certificates\n")
            dir.append("Homepage: https://www.riocardmais.com.br\n")
            dir.append("Description: Aplicação $nome")
            dir.createNewFile()
            println("fim createControl")

        }

        def createFolder(String name, String folderName){
            println("inicio createFolder")

            def file = new File("build/deploy/riocard/$folderName/$name")
            if(!file.exists()){
                file.mkdirs()
            }
            println("fim createFolder")
        }


        def createConf(String name){
            println("inicio createConf")

            def file = new File("build/deploy/etc/systemd/system")

            if(!file.exists()){
                file.mkdirs()
            }
            file = new File(file.absolutePath+"/$name"+".conf")

            file.write("[Unit]\n")
            file.append("Description=$name\n")
            file.append("BindTo=network.target\n")
            file.append("After=network.target\n")
            file.append("Requires=network.target\n\n")
            file.append("[Service]\n")
            file.append("Type=simple\n")
            file.append("Environment=LANG=en_US.UTF-8\n")
            file.append("Environment=JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/\n")
            file.append("UMask=0002\n")
            file.append("User=msa\n")
            file.append("Group=msa\n")
            file.append("WorkingDirectory=/riocard/msa/$name/\n")
            file.append("StandardOutput=syslog\n")
            file.append("StandardError=syslog\n")
            file.append("SyslogIdentifier=$name\n")
            file.append("ExecStart="+'$'+"JAVA_HOME/bin/java -Xms32m -Xmx128m -jar $name"+".jar\n")
            file.append("Restart=on-failure\n")
            file.append("[Install]\n")
            file.append("WantedBy=multi-user.target\n")
            file.createNewFile()
            println("fim createConf")

        }

        def createPreInst(String name){
            println("inicio createPretInst")

            def file = new File("build/deploy/DEBIAN")
            if(!file.exists()){
                file.mkdirs()
            }
            file = new File(file.absolutePath+"/preinst")
            file.write("#!/bin/bash\n")
            file.append("stop servico com 2>/dev/null\n")
            file.append("if [ \"\$(id wagmar.queiroga 2>/dev/null)\" ]; then \n")
            file.append("    useradd -s /bin/false -d /riocard/msa/$name --system $name \n")
            file.append("fi\n")
            file.createNewFile()
            println("fim createPreInst")

        }

        def createPostInst(String name){
            println("inicio createPostInst")

            def file = new File("build/deploy/DEBIAN")
            if(!file.exists()){
                file.mkdirs()
            }
            file = new File(file.absolutePath+"/postinst")
            file.write("#!/bin/bash\n")
            file.append("chown -R $name:$name /riocard/msa/$name \n")
            file.append("chown -R $name:$name /riocard/logs/$name \n")
            file.append("systemctl reload $name \n")
            file.append("systemctl enable $name \n")
            file.append("systemctl start $name \n")
            file.append("systemctl daemon-reload \n")
            file.createNewFile()
                        println("fim createPostInst")

        }

        def createPostRM(String name){
            println("inicio createPostRM")

            def file = new File("build/deploy/DEBIAN")
            if(!file.exists()){
                file.mkdirs()
            }
            file = new File(file.absolutePath+"/postrm")
            file.write("#!/bin/bash \n")
            file.append("systemctl stop $name \n")
            file.append("systemctl disable  $name \n")
            file.append("userdel $name \n")
            file.createNewFile()
                        println("fim createPostRM")

        }

// vim: syntax=groovy
