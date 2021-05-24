<?php
	$host = "eu-cdbr-west-01.cleardb.com";
	$user = "bf9422ed9ab87d";
	$password = "71d97a72";
	$database = "heroku_9eed7796d02a0d0";


   // echo $database;

   $conn = mysqli_connect($host,$user,$password,$database);
   if(!conn){
      echo "Problems";
      die("Connection failed: " . mysqli_connect_error());
   }
   else{
      echo "All good";
   }

   $username = $_POST["username"];
   $password = $_POST["password"];

   $sql = "UPDATE users SET password = $password WHERE username = '$username';";

   if(mysqli_query($conn,$sql)){
      echo "Succes!";
   }
   else{
      echo "Failed!";
   }
?>