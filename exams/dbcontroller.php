<?php
class DBController {
	private $conn = "";
	private $host = "eu-cdbr-west-01.cleardb.com";
	private $user = "bf9422ed9ab87d";
	private $password = "71d97a72";
	private $database = "heroku_9eed7796d02a0d0";

	function __construct() {
		$conn = $this->connectDB();
		if(!empty($conn)) {
			$this->conn = $conn;			
		}
	}

	function connectDB() {
		$conn = mysqli_connect($this->host,$this->user,$this->password,$this->database);
		return $conn;
	}

	function executeSelectQuery($query) {
		$result = mysqli_query($this->conn,$query);
		while($row=mysqli_fetch_assoc($result)) {
			$resultset[] = $row;
		}
		if(!empty($resultset))
			return $resultset;
	}

}
?>
