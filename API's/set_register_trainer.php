<?php
include("db_info.php");

$setName = $_POST['setName'];
$reps = $_POST['reps'];
$weight = $_POST['weight'];
$exerciseID = $_POST['exerciseID'];

$setID = gen_id(6,$mysqli);

$registerSet = $mysqli->prepare("INSERT INTO sets (set_id,set_name,exercise_id,reps,weight) VALUES('$setID','$setName','$exerciseID','$reps','$weight')"); 
$registerSet->execute();

if($registerSet){
    $response= array("status" =>"Set Added!","setID"=> $setID);
    echo json_encode($response);
}


function gen_id($l,$mysqli){
    $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
    $clientIdQuery = $mysqli->query("SELECT workout_id from workout where workout_id='$generatedID'");
    // To make sure the id generated is not already used.
    while($clientIdQuery->num_rows>0){
        $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
        $clientIdQuery = $mysqli->query("SELECT set_id from sets where set_id='$generatedID'");
    }
    return $generatedID;
}