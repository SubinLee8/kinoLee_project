package com.kinolee.oracle;

public interface OracleJdbc {
	//오라클 데이터베이스 서버에 접속하기 위한 JDBC 라이브러리 정보와 서버 주소/포트/SID
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	//접속 계정
	String USER = "kinolee";
	
	//접속 계정 비밀번호
	String PASSWORD = "kinolee";
}
