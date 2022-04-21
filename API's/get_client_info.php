<?php 
include("db_info.php");

// Get the data from user
$username = $_POST['username'];



// Calculate the age of the client
$clientIDquery = $mysqli->query("SELECT client_id from client where username='$username'");
$fetchClientID = mysqli_fetch_assoc($clientIDquery);
$clientID = $fetchClientID['client_id'];

if(!$clientID){
    exit("Please register your info!");
}

$clientInfoQuery = $mysqli->query("SELECT client_id,age,height,weight,past_injuries,health_issues,days_per_week,hours_per_day,training_preference,objectives from client_info where client_id='$clientID'");
$fetchClientInfo = mysqli_fetch_assoc($clientInfoQuery);


$clientInfo= array("client_id" =>$fetchClientInfo['client_id'],"age" =>$fetchClientInfo['age'],"weight" =>$fetchClientInfo['weight'],"height" =>$fetchClientInfo['height'],"past_injuries" =>$fetchClientInfo['past_injuries'],
"health_issues" =>$fetchClientInfo['health_issues'],"days_per_week" =>$fetchClientInfo['days_per_week'],"hours_per_day" =>$fetchClientInfo['hours_per_day'],"training_preference" =>$fetchClientInfo['training_preference'],"objectives" =>$fetchClientInfo['objectives'],);
echo json_encode($clientInfo);


?>