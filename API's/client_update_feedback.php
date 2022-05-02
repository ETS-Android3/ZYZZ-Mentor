<?php
include("db_info.php");

$exerciseID = $_POST["exerciseID"];
$feedback = $_POST["feedback"];

$updateExerciseFeedbackQuery = $mysqli->query("UPDATE exercise SET client_feedback = '$feedback' WHERE exercise_id = '$exerciseID'");

if($updateExerciseFeedbackQuery){
    echo "Done";
}
?>