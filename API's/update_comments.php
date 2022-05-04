<?php
include("db_info.php");

$exerciseID = $_POST["exerciseID"];
$comments = $_POST["comments"];

$updateExerciseCommentQuery = $mysqli->prepare("UPDATE exercise SET comments = '$comments' WHERE exercise_id = '$exerciseID'");
$updateExerciseCommentQuery->execute();
// Set client feedback to default
// Not prone to SQL Injection
$updateClientFeedbackCommentQuery = $mysqli->query("UPDATE exercise SET client_feedback = '' WHERE exercise_id = '$exerciseID'");


if($updateExerciseCommentQuery){
    echo "Done";
}
?>