<?php
include("db_info.php");

$setID = $_GET["setID"];
$weight = $_GET["weight"];


$updateSetInfoQuery = $mysqli->query("UPDATE sets SET client_weight = '$weight' WHERE set_id = '$setID'");

if($updateSetInfoQuery){
    echo "Done";
}
?>