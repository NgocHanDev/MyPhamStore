# Cấu hình SSH
$REMOTE_USER = "ngochan"
$REMOTE_HOST = "192.168.102.21"
$REMOTE_PATH = "/home/ngochan/myphamstore"
$SQL_FILE = "initdb.sql"
$MYSQL_DB = "myphamstoredb"
$MYSQL_USER = "root"
$MYSQL_PASS = "admin"

# Đảm bảo sử dụng UTF-8 encoding
[System.Console]::InputEncoding = [System.Text.Encoding]::UTF8
[System.Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$PSDefaultParameterValues['Out-File:Encoding'] = 'utf8'
$env:LANG = "en_US.UTF-8"

Write-Host "Đang copy project qua máy ảo..."
scp -r -o "SendEnv LANG" * "${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}"

Write-Host "Đang SSH vào máy ảo và khởi chạy docker-compose..."
ssh "${REMOTE_USER}@${REMOTE_HOST}" "
    cd ${REMOTE_PATH};
    docker rm -f $(docker ps -aq);
    docker rmi -f $(docker images -q);
    docker volume rm $(docker volume ls -q);
    docker-compose up -d;
"

Write-Host "Đang chờ MySQL container khởi động..."
ssh "${REMOTE_USER}@${REMOTE_HOST}" "
    until docker exec mysql mysqladmin -u${MYSQL_USER} -p${MYSQL_PASS} ping; do
        echo 'Đang chờ MySQL sẵn sàng...';
        sleep 1;
    done
"

Write-Host "Import dữ liệu từ initdb.sql..."
ssh "${REMOTE_USER}@${REMOTE_HOST}" "
    docker exec -i mysql mysql -u${MYSQL_USER} -p${MYSQL_PASS} ${MYSQL_DB} < ${REMOTE_PATH}/${SQL_FILE}
"

Write-Host "Triển khai & import dữ liệu hoàn tất!"