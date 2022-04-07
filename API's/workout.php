<?php
include("db_info.php");

$workoutName = $_POST['workoutName'];
$planID = $_POST['planID'];
$workoutID = gen_id(6,$mysqli);

// Check if the plan_id exist or not
$planIDquery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$planID'");
if($planIDquery->num_rows==0){
    exit("PLease create a plan with a client");
}

$registerWorkout = $mysqli->query("INSERT INTO workout (workout_id,workout_name,plan_id) VALUES('$workoutID','$workoutName','$planID')"); 

if($registerWorkout){
    echo "Workout Registered";
}

function gen_id($l,$mysqli){
    $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
    $clientIdQuery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$generatedID'");
    // To make sure the id generated is not already used.
    while($clientIdQuery->num_rows>0){
        $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
        $clientIdQuery = $mysqli->query("SELECT plan_id from login_trainer_client where plan_id='$generatedID'");
    }
    return $generatedID;
}