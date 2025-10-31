    def call(Map config) {
        pipeline {
            agent any
            stages {
                stage('Checkout') {
                    steps {
                        git url: config.gitRepo, branch: config.gitBranch
                    }
                }
                stage('Build') {
                    steps {
                        script {
                            // Navigate and list contents
                            sh '''
                            ls -lrt
                            cd java-maven-sonar-argocd-helm-k8s/spring-boot-app
                            ls -lrt
                            '''

                        // Example build command based on config
                        if (config.buildTool == 'maven') {
                            sh '''
                            cd java-maven-sonar-argocd-helm-k8s/spring-boot-app
                            mvn clean install
                            '''
                         } else if (config.buildTool == 'gradle') {
                            sh '''
                            cd java-maven-sonar-argocd-helm-k8s/spring-boot-app
                            gradle build
                            '''
                        }
                    }
                }
            }
               /* stage('Test') {
                    steps {
                        sh "mvn test" // Assuming a script in your project
                    }
                }
                // Add more stages as needed
            }  */
            post {
                always {
                    echo "Pipeline finished for project: ${config.projectName}"
                }
            }
        }
    }

