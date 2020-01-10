# kochbuchserver
## Installationsanleitung
Intellij Community Edition downloaden 

sudo mv Downloads/ideaIC-2019.3.1.tar.gz /opt/intellij/
cd /opt/intellij/
unp ideaIC-2019.3.1.tar.gz 


dieses Repository öffnen. 

## Bauen auf der Kommandozeile

./gradlew build

ausführen. 

damit es läuft muss man noch die Datei 
exzellenzkoch-firebase-adminsdk-35d6r-a48e2abb7e.json 

in den buildordner kopiert werden. 
diese liegt in 
kochbuchserver/exzellenzkoch

cd ~/kochbuchserver/exzellenzkoch/build/libs

cp ../../exzellenzkoch-firebase-adminsdk-35d6r-a48e2abb7e.json .



## Server starten
cd build/libs/

java -jar exzellenzkoch-0.0.1-SNAPSHOT.jar 

## API Kommandozeile
statt mit firefox/chrome http://localhost:8080/api/recipe/1  aufzurufen kann man auch

 curl -s http://localhost:8080/api/recipe/1 
 ausführen 
 
 curl -s http://localhost:8080/api/recipe/1 | jq gibts einem in Farbig. 
 
