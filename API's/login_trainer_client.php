<?php 
include("db_info.php");

$clientID = $_POST['clientID'];
$trainerUsername = $_POST['trainerUsername'];
$dayPerWeek = $_POST['dayPerWeek'];
$objective = $_POST['objective'];


$planIDQuery = $mysqli->query("SELECT plan_id from login_trainer_client where trainer_username='$trainerUsername' AND client_id = '$clientID'");

if($planIDQuery->num_rows>0){
    echo "Already have a plan with this client";
}
else{
    $planID = gen_id(6,$mysqli);
    $registerPlan = $mysqli->query("INSERT INTO login_trainer_client (plan_id,client_id,trainer_username,day_per_week,objective) VALUES('$planID','$clientID',
    '$trainerUsername','$dayPerWeek','$objective')"); 
    if($registerPlan){
        echo 'plan registered successfully!';
    }
}   

function gen_id($l,$mysqli){
    $generatedID = substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyz"), 0, $l);
    $clientIdQuery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$generatedID'");
    // To make sure the id generated is not already used.
    while($clientIdQuery->num_rows>0){
        $generatedID = substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyz"), 0, $l);
        $clientIdQuery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$generatedID'");
    }
    return $generatedID;
}