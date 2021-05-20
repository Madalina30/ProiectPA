<?php
require "dbcontroller.php";
$db = new DataBase();
// if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['email']) && isset($POST['punctaj'])) {
//     if ($db->dbConnect()) {
//         if ($db->signUp("users",$_POST['username'], $_POST['password']) , $_POST['email'], $POST['punctaj']) {
//             echo "Sign Up Success";
//         } else echo "Sign up Failed";
//     } else echo "Error: Database connection";
// } else echo "All fields are required";
if (isset($_POST['data'])) {
    $data = $_POST['data'];
        if ($db->dbConnect()) {
            if ($db->signUp("users",$data['username'], $data['password'], $data['email'], '0') {
                echo "Sign Up Success";
            } else echo "Sign up Failed";
        } else echo "Error: Database connection";
    } else echo "All fields are required";
// require "DataBase.php";

?>