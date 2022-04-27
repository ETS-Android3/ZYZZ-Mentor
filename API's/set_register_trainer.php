<?php
include("db_info.php");

$setName = $_POST['setName'];
$reps = $_POST['reps'];
$weight = $_POST['weight'];
$position = $_POST['position'];
$exerciseID = $_POST['exerciseID'];

$setNameQuery = $mysqli->query("SELECT set_name from sets where set_name='$setName' AND exercise_id = '$exerciseID'");

if($setNameQuery->num_rows>0){
    exit("set name: ".$setName. " already exist please choose another");
}

$registerSet = $mysqli->query("INSERT INTO sets (set_name,exercise_id,reps,weight,position) VALUES('$setName','$exerciseID','$reps','$weight','$position')"); 

if($registerSet){
    echo "Set Registered";
}
