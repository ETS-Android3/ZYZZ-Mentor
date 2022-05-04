<?php
include("db_info.php");

$setID = $_POST["setID"];
$columnToUpdate = $_POST["column"];
$updatedInfo = $_POST["updatedInfo"];



$updateSetInfoQuery = $mysqli->prepare("UPDATE sets SET $columnToUpdate = '$updatedInfo' WHERE set_id = '$setID'");
$updateSetInfoQuery->execute();

// Set client inputs to default
// Not prone to SQL Injection
$updateSetInfoQuery = $mysqli->query("UPDATE sets SET client_weight = 0 WHERE set_id = '$setID'");
$updateSetInfoQuery = $mysqli->query("UPDATE sets SET client_reps = 0 WHERE set_id = '$setID'");
$updateSetInfoQuery = $mysqli->query("UPDATE sets SET complete = 0 WHERE set_id = '$setID'");

if($updateSetInfoQuery){
    echo "Done";
}
?>