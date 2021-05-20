<?php
require_once("dbcontroller.php");
/* 
A domain Class to demonstrate RESTful web services
*/
Class ExamNet{
	private $exam= array();
	/*
		you should hookup the DAO here
	*/
	public function getAllUsers(){
		$query = "SELECT * FROM users";
		$dbcontroller = new DBController();
		$this->exam = $dbcontroller->executeSelectQuery($query);
		return $this->exam;
	}	

}
?>