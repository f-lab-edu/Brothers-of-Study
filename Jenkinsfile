pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
              checkout scm
              echo 'Git Checkout Success!'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
                echo 'test success'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
                echo 'build success'
            }
        }

        stage('Deploy') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "nginx-web-server",
                            verbose: true,
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "build/libs/*.jar", // 전송할 파일
                                    removePrefix: "build/libs", // 파일에서 삭제할 경로
                                    remoteDirectory: "app", // 배포할 위치
                                    execCommand: "sh ~/app/deploy.sh" // 원격 서버에서 실행
                                )
                            ]
                        )
                    ]
                )
            }
        }
    }
}