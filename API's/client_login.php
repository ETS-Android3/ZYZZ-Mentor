<?php 
include('db_info.php');

$username = $_POST['Username'];
$password = $_POST['Password'];
$name="";
$clientID="";
$usernameQuery = $mysqli->query("SELECT username from client where username='$username'");

// Check if the username exist
if($usernameQuery->num_rows==0){
    $result = "user does not exist!"; 
}
else{
// Get the hashed password of the account from the database
$passwordQuery = $mysqli->query("SELECT password from client where username='$username'");
$fetchHashedPassword = mysqli_fetch_assoc($passwordQuery);
$hashedPassword = $fetchHashedPassword["password"];

// Verify if the password is correct
$verifyPassword = password_verify($password,$hashedPassword);

if($verifyPassword){
    // Get the name of the user to welcome him
    $fullNameQuery = $mysqli->query("SELECT full_name, client_id from client where username='$username'");
    $fetchFullName = mysqli_fetch_assoc($fullNameQuery);
    $fullName = $fetchFullName['full_name'];
    $splitFullName = explode(" ", $fullName);
    $name = $splitFullName[0];
    
    $clientID = $fetchFullName['client_id'];
    $result= "accepted";
}
else{
    $result  = "Wrong password!";
}
}
$response= array("status" =>$result,"Name"=> $name,"clientID"=>$clientID);
echo json_encode($response);