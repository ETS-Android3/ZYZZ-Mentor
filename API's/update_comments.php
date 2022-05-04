<?php
include("db_info.php");

$exerciseID = $_POST["exerciseID"];
$comments = $_POST["comments"];

$updateExerciseCommentQuery = $mysqli->query("UPDATE exercise SET comments = '$comments' WHERE exercise_id = '$exerciseID'");

// Set client feedback to default
$updateClientFeedbackCommentQuery = $mysqli->query("UPDATE exercise SET client_feedback = '' WHERE exercise_id = '$exerciseID'");


if($updateExerciseCommentQuery){
    echo "Done";
}
?>