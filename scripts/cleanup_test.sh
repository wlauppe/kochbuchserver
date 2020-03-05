echo "bilder werden gelöscht und Datenbank auf den initialzustand gesetzt"
sudo mysql -u root <~/kochbuchserver/Db.sql
rm -rf ~/images/*
