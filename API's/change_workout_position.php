<?php
include("db_info.php");

$workoutFrom = $_POST['workoutFrom'];
$workoutTo = $_POST['workoutTo'];


$workoutFrom = json_decode($workoutFrom);
$workoutTo = json_decode($workoutTo);

$changeWorkoutFromPositionQuery = $mysqli->query("UPDATE workout SET position = '$workoutTo->position' WHERE workout_id = '$workoutFrom->WorkoutID'");
$changeWorkoutToPositionQuery = $mysqli->query("UPDATE workout SET position = '$workoutFrom->position' WHERE workout_id = '$workoutTo->WorkoutID'");


if($changeWorkoutFromPositionQuery && $changeWorkoutToPositionQuery){
    echo "Changed Saved";
}


?>