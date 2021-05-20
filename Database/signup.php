<?php
require "dbcontroller.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['email']) && isset($POST['punctaj'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("users",$_POST['username'], $_POST['password']) , $_POST['email'], $POST['punctaj']) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>