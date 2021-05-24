<?php
require_once("dbcontroller.php");


Class ExamNet{
	private $exam= array();
	
	public function getAllStatus(){
		$query = "SELECT * FROM grile_last";
		$dbcontroller = new DBController();
		$this->exam = $dbcontroller->executeSelectQuery($query);
		return $this->exam;
	}	

}
?>