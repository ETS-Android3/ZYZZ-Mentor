<?php
include("db_info.php");

$exerciseName = $_POST['exerciseName'];
$workoutID = $_POST['workoutID'];
$postion = $_POST['position'];
$exerciseID = gen_id(6,$mysqli);

$exerciseName = ucwords($exerciseName);

$registerExercise = $mysqli->query("INSERT INTO exercise (exercise_id,exercise_name,workout_id,position) VALUES('$exerciseID','$exerciseName','$workoutID','$postion')"); 

if($registerExercise){
    $response= array("status" =>"Exercise Added!","ExerciseID"=> $exerciseID);
    echo json_encode($response);
}

function gen_id($l,$mysqli){
    $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
    $clientIdQuery = $mysqli->query("SELECT exercise_id from exercise where exercise_id='$generatedID'");
    // To make sure the id generated is not already used.
    while($clientIdQuery->num_rows>0){
        $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
        $clientIdQuery = $mysqli->query("SELECT exercise_id from exercise where exercise_id='$generatedID'");
    }
    return $generatedID;
}