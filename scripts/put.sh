echo "führe put aus, der recipe mit id 1 überschreibt"
echo "wenns klappt sollte der Titel \"neuer Sandkuchen\" danach lauten"
echo -n "Status Code ist: "
curl -X PUT -d "@recipe.json" http://193.196.38.185:8080/api/recipes/1 -w "%{http_code}" -H "Content-Type: application/json"
echo
