package com.ezen.common.database;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * connection 생성 및 반환
 * 싱글톤 디자인 패턴 적용
 * db 연결정보를 properties 설정 파일로 분리
 */
public class ConnectionFactory {

    private String driver ;
    private String username ;
    private String password ;
    private String url ;

    private static ConnectionFactory instance = new ConnectionFactory();

    private DataSource dataSource;

    private ConnectionFactory(){
            loadProperties();
            createDataSource();
//            Class.forName(driver); // 드라이버 로딩 - 필요없어짐 위로 대체
        }


    public static ConnectionFactory getInstance(){
        return instance;
    }


    private void loadProperties() {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/config/db.properties");
        try {
            prop.load(in);
            this.driver = prop.getProperty("database.driver");
            this.url = prop.getProperty("database.url");
            this.username = prop.getProperty("database.username");
            this.password = prop.getProperty("database.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     // 캡슐화
    private void createDataSource(){
        //DB연결 설정 정보(알려주는거)
        //BasicDataSource : CB connection pool을 구현하는 데에 사용
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        //connection pool 설정 정보
        dataSource.setInitialSize(5); // 초기화 5개 만들고
        dataSource.setMaxTotal(10); // 최대 10개
        dataSource.setMaxIdle(5); // 더이상 연결없으면 5개로 돌려
        dataSource.setMaxWaitMillis(2000); // 2초연결안되면 끊기
        this.dataSource = dataSource;

    }

    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(url,username,password); 지워진다 커넥션을 데이터 소스를 통해서 한다.
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        // DAO 클래스 영역이라 가정
        ConnectionFactory factory = ConnectionFactory.getInstance();
        Connection con = factory.getConnection();
        System.out.println(con);


    }
}
