<?php
include("db_info.php");

$exerciseFrom = $_POST['exerciseFrom'];
$exerciseTo = $_POST['exerciseTo'];


$exerciseFrom = json_decode($exerciseFrom);
$exerciseTo = json_decode($exerciseTo);

$changeexerciseFromPositionQuery = $mysqli->query("UPDATE exercise SET position = '$exerciseTo->position' WHERE exercise_id = '$exerciseFrom->ExerciseID'");
$changeexerciseToPositionQuery = $mysqli->query("UPDATE exercise SET position = '$exerciseFrom->position' WHERE exercise_id = '$exerciseTo->ExerciseID'");


if($changeexerciseFromPositionQuery && $changeexerciseToPositionQuery){
    echo "Changed Saved";
}


?>