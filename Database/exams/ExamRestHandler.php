<?php
require_once("SimpleRest.php");
require_once("ExamNet.php");
		
class ExamRestHandler extends SimpleRest {

	function getAllUsers() {	

		$exam = new ExamNet();
		$rawData = $exam->getAllUsers();

		if(empty($rawData)) {
			$statusCode = 404;
			$rawData = array('error' => 'No exam found!');		
		} else {
			$statusCode = 200;
		}

		$requestContentType = 'application/json';//$_POST['HTTP_ACCEPT'];
		$this ->setHttpHeaders($requestContentType, $statusCode);
		
		$result["exams"] = $rawData;
				
		if(strpos($requestContentType,'application/json') !== false){
			$response = $this->encodeJson($result);
			echo $response;
		}
	}

	
	public function encodeJson($responseData) {
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;		
	}
}
?>