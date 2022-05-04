<?php 
include("db_info.php");

$clientID = $_POST['clientID'];

$clientFullNameQuery = $mysqli->prepare("SELECT full_name from client where client_id = '$clientID'");
$clientFullNameQuery->execute();
if($clientFullNameQuery->get_result()->num_rows==0){
    exit("Client ID not valid");
}

$fetchClientFullName = mysqli_fetch_assoc($clientFullNameQuery->get_result());
$clientFullName = $fetchClientFullName["full_name"];
echo $clientFullName;   

?>

