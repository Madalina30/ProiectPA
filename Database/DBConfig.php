<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        $this->servername = 'eu-cdbr-west-01.cleardb.com';
        $this->username = 'bf9422ed9ab87d';
        $this->password = '71d97a72';
        $this->databasename = 'heroku_9eed7796d02a0d0';

    }
}

?>