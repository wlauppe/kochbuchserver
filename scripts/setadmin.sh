#!/bin/bash
user=$1
echo "user $user wird mit Adminrechten ausgestattet"
mysql -u root -e "update pseDb.user set isAdmin=1 where user_Id='${user}'";
