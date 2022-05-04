<?php
include("db_info.php");

$setID = $_GET["setID"];
$weight = $_GET["weight"];
$reps = $_GET["reps"];



$updateSetWeightQuery = $mysqli->prepare("UPDATE sets SET client_weight = '$weight' WHERE set_id = '$setID'");
$updateSetWeightQuery->execute();

$updateSetRepsQuery = $mysqli->prepare("UPDATE sets SET client_reps = '$reps' WHERE set_id = '$setID'");
$updateSetRepsQuery->execute(); 

if($updateSetWeightQuery && $updateSetRepsQuery){
    echo "Done";
} 

?>