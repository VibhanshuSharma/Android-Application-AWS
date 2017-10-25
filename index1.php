<?php

if($_GET['variable']=="bills"){
$url = "http://104.198.0.197:8080/bills?&history.active=true&last_version.urls.pdf__exists=true&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=50";
$json1=file_get_contents($url,0,null,null);
$json_response1=json_encode($json1,true);
echo $json1;  
}
else if($_GET['variable']=="new"){
$url = "http://104.198.0.197:8080/bills?&history.active=false&last_version.urls.pdf__exists=true&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=50";
$json=file_get_contents($url,0,null,null);
$json_response=json_encode($json,true);
echo $json;  
}
else if($_GET['variable']=="legis"){
$url = "http://104.198.0.197:8080/legislators?&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=all";
$json=file_get_contents($url,0,null,null);
$json_response=json_encode($json,true);
echo $json;  
}
else if($_GET['variable']=="commit"){
$url = "http://104.198.0.197:8080/committees?&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=all";
$json2=file_get_contents($url,0,null,null);
$json_response2=json_encode($json2,true);
echo $json2;  
}
else if($_GET['variable']=="id"){
$a=$_GET["param1"];    
$url = "http://104.198.0.197:8080/legislators?&apikey=07daebe9dade4bc784ababb5c5f81b03&bioguide_id=".$a;
$json3=file_get_contents($url,0,null,null);
$json_response3=json_encode($json3,true);
echo $json3;  
}
else if($_GET['variable']=="cid"){
$c=$_GET["param3"];    
$url = "http://104.198.0.197:8080/committees?&chamber=".$c."&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=5";
$json5=file_get_contents($url,0,null,null);
$json_response5=json_encode($json5,true);
echo $json5;  
}
else if($_GET['variable']=="bid"){
$d=$_GET["param4"];    
$url = "http://104.198.0.197:8080/bills?&chamber=".$d."&apikey=07daebe9dade4bc784ababb5c5f81b03&per_page=5";
$json5=file_get_contents($url,0,null,null);
$json_response5=json_encode($json5,true);
echo $json5;  
}
else if($_GET['variable']=="bill"){
$b=$_GET["param2"];    
$url = "http://104.198.0.197:8080/bills?&apikey=07daebe9dade4bc784ababb5c5f81b03&bill_id=".$b;
$json4=file_get_contents($url,0,null,null);
$json_response4=json_encode($json4,true);
echo $json4;  
}
else if($_GET['variable']=="billin"){
$e=$_GET["param5"];    
$url = "http://104.198.0.197:8080/bills?&apikey=07daebe9dade4bc784ababb5c5f81b03&bill_id=".$e;
$json6=file_get_contents($url,0,null,null);

echo $json6;  
}
?>
