pipeline {
    agent any

    environment {
        DEPLOY_DIR = '/opt/publico/agiles/backend/2025-UTN-GRUPO-4-BE'  // ruta en tu servidor donde vive el repo
    }

    triggers {
        githubPush()  // <-- ESTE es el método correcto
    }

    stages {
        stage('Deploy') {
            when {
                branch 'refactor'  // solo ejecuta si la rama del push es "refactor"
            }
            steps {
                dir("${env.DEPLOY_DIR}") {
                    sh '''
                    echo "🔻 Deteniendo contenedores..."
                    docker compose down || true

                    echo "📦 Actualizando código..."
                    git checkout refactor
                    git pull origin refactor

                    echo "🚀 Levantando contenedores..."
                    docker compose up -d --force-recreate --build

                    echo "✅ Despliegue completado con éxito."
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "✅ Despliegue exitoso en rama refactor"
        }
        failure {
            echo "❌ Error durante el despliegue"
        }
    }
}
