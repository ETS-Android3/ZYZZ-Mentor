<?php
include("db_info.php");

$setID = $_GET["setID"];
$weight = $_GET["weight"];
$reps = $_GET["reps"];



$updateSetWeightQuery = $mysqli->query("UPDATE sets SET client_weight = '$weight' WHERE set_id = '$setID'");
$updateSetRepsQuery = $mysqli->query("UPDATE sets SET client_reps = '$reps' WHERE set_id = '$setID'");
if($updateSetInfoQuery && $updateSetRepsQuery){
    echo "Done";
} 
?>