pipeline {
    agent any

    environment {
        DEPLOY_DIR = '/opt/publico/agiles/backend/2025-UTN-GRUPO-4-BE'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Deploy') {
            when {
                expression {
                    // Jenkins puede usar BRANCH_NAME o GIT_BRANCH según el plugin
                    return env.BRANCH_NAME == 'refactor' || env.GIT_BRANCH == 'origin/refactor'
                }
            }
            steps {
                sh '''
                echo "📦 Actualizando código..."
                cd ${DEPLOY_DIR}
                git checkout refactor
                git pull origin refactor

                echo "🚀 Desplegando contenedores..."
                docker compose down || true
                docker compose build --no-cache
                docker compose up -d --force-recreate
                '''
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
