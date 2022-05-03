<?php
include("db_info.php");

$setID = $_GET["setID"];
$reps = $_GET["reps"];


$updateSetInfoQuery = $mysqli->query("UPDATE sets SET client_reps = '$reps' WHERE set_id = '$setID'");

if($updateSetInfoQuery){
    echo "Done";
}
?>