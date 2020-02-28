server=193.196.38.185
server=localhost
echo "führe put aus, der recipe mit id 1 überschreibt"
echo "wenns klappt sollte der Titel \"neuer Sandkuchen\" danach lauten"
echo -n "Status Code ist: "
curl -X POST -d "@recipe.json" http://$server:8080/api/recipes -w "%{http_code}" -H "Content-Type: application/json"
echo
