<?php 
include("db_info.php");

$clientID = $_POST['clientID'];
$trainerUsername = $_POST['trainerUsername'];

$clientFullNameQuery = $mysqli->prepare("SELECT full_name from client where client_id = '$clientID'");
$clientFullNameQuery->execute();

if($clientFullNameQuery->get_result()->num_rows==0){
    exit( "Client ID not valid");
}
else{
// Check if the trainer already has a plan with the client 
$planIDQuery = $mysqli->prepare("SELECT plan_id from login_trainer_client where client_id = '$clientID'");
$planIDQuery->execute();

if($planIDQuery->get_result()->num_rows>0){
    // If the trainer already has a plan with the client he entered
    exit("The client is already subscribed to a trainer!");
}
else{

    // Register the plan to the database
    $fetchClientFullName = mysqli_fetch_array($clientFullNameQuery->get_result());
    $clientFullName = $fetchClientFullName["full_name"];

    $planID = gen_id(6,$mysqli);
    // Not prone to SQL Injection
    $registerPlan = $mysqli->query("INSERT INTO login_trainer_client (plan_id,client_id,trainer_username,client_fullname) VALUES('$planID','$clientID',
    '$trainerUsername','$clientFullName')"); 
    if($registerPlan){
        echo 'client registered successfully!';
    }
}   
}

function gen_id($l,$mysqli){
    $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
    $clientIdQuery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$generatedID'");
    // To make sure the id generated is not already used.
    // Not prone to SQL Injection
    while($clientIdQuery->num_rows>0){
        $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
        $clientIdQuery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$generatedID'");
    }
    return $generatedID;
}