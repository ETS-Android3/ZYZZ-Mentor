<?php 
include("db_info.php");

$exerciseID = $_GET['exerciseID'];


// Not prone to SQL Injection
$exerciseIDQuery = $mysqli->query("SELECT exercise_id from exercise where exercise_id='$exerciseID'");

if($exerciseIDQuery->num_rows==0){
    exit("Nothing to delete");
}
 // Not prone to SQL Injection
$deleteSetsQuery = $mysqli->query("DELETE from sets where exercise_id='$exerciseID'");
// Not prone to SQL Injection
$deleteExerciseQuery = $mysqli->query("DELETE from exercise where exercise_id='$exerciseID'");

echo "Exercise Deleted!";
?>
