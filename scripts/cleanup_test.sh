echo "bilder werden gelöscht und Datenbank auf den initialzustand gesetzt"
export DATASOURCE_NAME=pseDb
sudo mysql -u root -e "DROP DATABASE $DATASOURCE_NAME"
sudo mysql -u root <~/kochbuchserver/Db.sql
rm -rf ~/images/*
