#!/bin/bash
# start-backend.sh: bring up MySQL, Redis and the Spring Boot backend

set -euo pipefail

# helper to check if a process is running
is_running() {
    pgrep -f "$1" >/dev/null 2>&1
}

# start MySQL
echo "🔧 Starting MySQL..."
if ! is_running mysqld; then
    if command -v brew >/dev/null 2>&1; then
        brew services start mysql || true
    else
        mysql.server start || true
    fi
else
    echo "MySQL is already running."
fi

# start Redis
echo "🔧 Starting Redis..."
if ! is_running redis-server; then
    if command -v brew >/dev/null 2>&1; then
        brew services start redis || true
    else
        # try to launch a standalone server in the background
        if command -v redis-server >/dev/null 2>&1; then
            redis-server --daemonize yes || true
        fi
    fi
else
    echo "Redis is already running."
fi

# start the backend application
echo "🚀 Starting Spring Boot backend..."

# ensure port 8082 is free
if lsof -i :8082 >/dev/null 2>&1; then
    OLD_PID=$(lsof -ti :8082)
    echo "Port 8082 already in use by PID $OLD_PID; attempting to kill it."
    kill "$OLD_PID" || echo "Failed to kill process $OLD_PID. You may need to stop it manually."
    sleep 1
fi

# run in background and record pid
./mvnw spring-boot:run &
BACKEND_PID=$!
echo $BACKEND_PID > .backend.pid

echo "Backend started with PID $BACKEND_PID"
echo "✅ All services up. You can access the API at http://localhost:8082"