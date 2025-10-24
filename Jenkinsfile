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
                expression {
                    env.GIT_BRANCH == 'origin/refactor' ||
                    env.BRANCH_NAME == 'refactor'
                }
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
                    docker compose build --no-cache
                    docker compose up -d --force-recreate

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
