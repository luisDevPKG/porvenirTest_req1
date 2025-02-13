pipeline {
    // en que agente esto va a estar corriendo
    agent any

    // Que herramientas vamos a estar instalando al prinicipio
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven"
    }

    stages {
        stage('Build') {
            steps {
                // Agarrar el repositorio en el que esta el codigo que se va a correr
                git branch: 'main', url: 'https://github.com/luisDevPKG/porvenirTest_req1.git'

                // Run Maven clean.
                bat "mvn clean install"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage("Imprimo cosas"){
            steps{
                echo "Aqui va un stage aparte"
            }
        }
    }
}