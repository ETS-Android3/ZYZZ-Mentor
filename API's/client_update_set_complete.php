<?php
include("db_info.php");

$setID = $_GET["setID"];
$complete = $_GET["complete"];

// Not prone to SQL Injection
$updateSetInfoQuery = $mysqli->query("UPDATE sets SET complete = '$complete' WHERE set_id = '$setID'");

if($updateSetInfoQuery){
    echo "Done";
}
?>