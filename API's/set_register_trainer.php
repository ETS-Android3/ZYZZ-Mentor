<?php
include("db_info.php");

$setName = $_POST['setName'];
$reps = $_POST['reps'];
$weight = $_POST['weight'];
$exerciseID = $_POST['exerciseID'];

$setNameQuery = $mysqli->query("SELECT set_name from sets where set_name='$setName' AND exercise_id = '$exerciseID'");

if($setNameQuery->num_rows>0){
    exit("set name: ".$setName. " already exist please choose another");
}

$registerSet = $mysqli->query("INSERT INTO sets (set_name,exercise_id,reps,weight) VALUES('$setName','$exerciseID','$reps','$weight')"); 

if($registerSet){
    echo "Set Registered";
}
