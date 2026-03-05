#!/bin/bash
# stop-backend.sh: stop backend, Redis and MySQL services

set -euo pipefail

# stop the backend application
if [ -f .backend.pid ]; then
    PID=$(cat .backend.pid)
    echo "🛑 Stopping backend (PID $PID)..."
    kill "$PID" 2>/dev/null || true
    rm -f .backend.pid
else
    echo "⚠️  No .backend.pid file; attempting to kill any running Java app."
    pkill -f SimpleCurdAppApplication || true
fi

# stop Redis
echo "🛑 Stopping Redis..."
if command -v brew >/dev/null 2>&1; then
    brew services stop redis || true
else
    pkill redis-server || true
fi

# stop MySQL
echo "🛑 Stopping MySQL..."
if command -v brew >/dev/null 2>&1; then
    brew services stop mysql || true
else
    mysql.server stop || true
fi

echo "✅ All services stopped."