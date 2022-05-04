<?php
include("db_info.php");

$exerciseID = $_POST["exerciseID"];
$feedback = $_POST["feedback"];

$updateExerciseFeedbackQuery = $mysqli->prepare("UPDATE exercise SET client_feedback = '$feedback' WHERE exercise_id = '$exerciseID'");
$updateExerciseFeedbackQuery->execute();

if($updateExerciseFeedbackQuery){
    echo "Done";
}
?>