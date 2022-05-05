<?php 
include('db_info.php');

$username = $_POST["Username"];
$password = $_POST["Password"];
$name="";
$usernameQuery = $mysqli->prepare("SELECT username from trainer where username='$username'");
$usernameQuery->execute();

// Check if the username exist
if($usernameQuery->get_result()->num_rows==0){
    $result = "user does not exist!"; 

}
else{
// Get the hashed password of the account from the database
$passwordQuery = $mysqli->prepare("SELECT password from trainer where username='$username'");
$passwordQuery->execute();
$fetchHashedPassword = mysqli_fetch_array($passwordQuery->get_result());
$hashedPassword = $fetchHashedPassword["password"];

// Verify if the password is correct
$verifyPassword = password_verify($password,$hashedPassword);

if($verifyPassword){
    // Get the name of the user to welcome him
    $fullNameQuery = $mysqli->prepare("SELECT full_name from trainer where username='$username'");
    $fullNameQuery->execute();
    $fetchFullName = mysqli_fetch_assoc($fullNameQuery->get_result());
    $fullName = $fetchFullName['full_name'];
    $splitFullName = explode(" ", $fullName);
    $name = $splitFullName[0];
    
    $result= "accepted";
}
else{
    $result  = "Wrong password!";
    
}


}
$response= array("status" =>$result,"Name"=> $name);
echo json_encode($response);