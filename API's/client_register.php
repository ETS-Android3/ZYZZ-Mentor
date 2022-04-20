<?php 
include("db_info.php");

// Get the data from user
$email = $_POST['email'];
$username = $_POST['username'];
$fullName = $_POST['fullName'];
$gender = $_POST['gender'];
$dob = $_POST['dob'];
$password = $_POST['password'];

// Check if the email is a valid email
if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    exit("$email is not a valid email address");
  }

// Check if username or email address are already used
$usernameQuery = $mysqli->query("SELECT username from client where username='$username'");
$emailQuery = $mysqli->query("SELECT username from client where email='$email'");



if($usernameQuery->num_rows>0){
    exit('username already exists, please enter another');
}
if($emailQuery->num_rows>0){
    exit('email already exists, please enter another');
}

// Get date and time for each conversion
date_default_timezone_set('Asia/Beirut'); 
$date = date('Y-m-d H:i:s');

// Hash the password 
$hashedPassword =password_hash($password,PASSWORD_BCRYPT);

// Register the client to the Database
$registerclient = $mysqli->query("INSERT INTO client (username,full_name,password,dob,email,gender,register_date) VALUES('$username','$fullName',
'$hashedPassword','$dob','$email','$gender','$date')"); 

if($registerclient){
    echo "Client registered";
}

?>