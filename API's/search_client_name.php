<?php 
include("db_info.php");

$clientID = $_POST['clientID'];

$clientFullNameQuery = $mysqli->query("SELECT full_name from client where client_id = '$clientID'");

if($clientFullNameQuery->num_rows==0){
    exit("Client ID not valid");
}

$fetchClientFullName = mysqli_fetch_assoc($clientFullNameQuery);
$clientFullName = $fetchClientFullName["full_name"];
echo $clientFullName;   

?>

