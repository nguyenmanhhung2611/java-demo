<?php
class DB_Query 
{
    private $db_host;
    private $db_name;
    private $username;
    private $password;
    
    function __construct() {
        $this->db_host = "localhost";
        $this->db_name = "salary";
        $this->username = "root";
        $this->password = "";
    }
    
    public function connect() {
        $db_con=mysql_connect($this->db_host, $this->username, $this->password);
        if (!$db_con) {
              die('Could not connect: ' . mysql_error());
          }
        $connection_string=mysql_select_db($this->db_name);
        // set utf for all page
        mysql_query('SET NAMES UTF-8');
        return $db_con;
    }
    
    public function disconnect($con) {
        mysql_close($con);
    }
}