<?php 
include("db_info.php");

// Get the data from user
$exerciseID = $_GET['exerciseID'];



$exerciseIDQuery = $mysqli->query("SELECT exercise_id from exercise where exercise_id='$exerciseID'");

if($exerciseIDQuery->num_rows==0){
    exit("Nothing to delete");
}
 
$deleteSetsQuery = $mysqli->query("DELETE from sets where exercise_id='$exerciseID'");

$deleteExerciseQuery = $mysqli->query("DELETE from exercise where exercise_id='$exerciseID'");

echo "Exercise Deleted!";
?>
