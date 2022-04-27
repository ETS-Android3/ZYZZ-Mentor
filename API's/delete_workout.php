<?php 
include("db_info.php");

// Get the data from user
$workoutID = $_GET['workoutID'];



$workoutIDQuery = $mysqli->query("SELECT workout_id from workout where workout_id='$workoutID'");

if($workoutIDQuery->num_rows==0){
    exit("Nothing to delete");
}
 
$exerciseIDQuery = $mysqli->query("SELECT exercise_id from exercise where workout_id='$workoutID'");

while($row2 =mysqli_fetch_assoc($exerciseIDQuery))
    {
        $deletesetsQuery = $mysqli->query("DELETE from sets where exercise_id='$row2[exercise_id]'");
        $deleteExerciseQuery = $mysqli->query("DELETE from exercise where exercise_id='$row2[exercise_id]'");
    }

$deleteWorkoutQuery = $mysqli->query("DELETE from workout where workout_id='$workoutID'");

echo "Workout Deleted!";
