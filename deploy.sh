#!/bin/bash

# Cấu hình SSH
REMOTE_USER="ngochan"
REMOTE_HOST="192.168.102.21"
REMOTE_PATH="/home/ngochan/myphamstore"
SQL_FILE="initdb.sql"
MYSQL_DB="myphamstoredb"
MYSQL_USER="root"
MYSQL_PASS="admin"

# Đảm bảo sử dụng UTF-8 encoding
export LANG="en_US.UTF-8"
export LC_ALL="en_US.UTF-8"

echo "Đang copy project qua máy ảo..."
scp -r * "${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}"

echo "Đang SSH vào máy ảo và khởi chạy docker-compose..."
ssh "${REMOTE_USER}@${REMOTE_HOST}" "
    cd ${REMOTE_PATH};
    docker rm -f \$(docker ps -aq);
    docker rmi -f \$(docker images -q);
    docker volume rm \$(docker volume ls -q);
    docker-compose up -d;
"

echo "Đang chờ MySQL container khởi động..."
ssh "${REMOTE_USER}@${REMOTE_HOST}" "
    until docker exec mysql mysqladmin -u${MYSQL_USER} -p${MYSQL_PASS} ping; do
        echo 'Đang chờ MySQL sẵn sàng...';
        sleep 1;
    done
"

echo "Import dữ liệu từ initdb.sql..."
ssh "${REMOTE_USER}@${REMOTE_HOST}" "
    if [ ! -f ${REMOTE_PATH}/${SQL_FILE} ]; then
        echo 'Lỗi: File ${SQL_FILE} không tồn tại!';
        exit 1;
    fi;
    docker exec -i mysql mysql -u${MYSQL_USER} -p${MYSQL_PASS} ${MYSQL_DB} < ${REMOTE_PATH}/${SQL_FILE}
"

echo "Triển khai & import dữ liệu hoàn tất!"