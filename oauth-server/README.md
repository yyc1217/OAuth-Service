## OAuth-Server [![Build Status](http://140.115.3.96:8080/jenkins/buildStatus/icon?job=OAuth-Service)](http://140.115.3.96:8080/jenkins/job/OAuth-Service/)
OAuth Athorization Server writtern in Java

### Dependencies
- hikariCP 2.3.1
- ehcache 2.9.0
- hibernate 4.3.6
- oltu.oauth2 1.0.0
- spring-webmvc 4.1.1
- spring-security 4.0.0.M2
- openid-consumer 0.0.1 ( https://github.com/NCU-CC/OpenID-Consumer )

### Gradle
- jettyStart : run embedded server
- jettyStop  : stop embedded server above

### Resources
resources are divided into two environments for Spring

- develope : include embedded database and mocked elements

- production : put following files into **src/main/resources/production**
    - ehcache.xml ( standard ehcahe setting, must exist a cache 'permissionDictionary' )
    - connection.properties
    ```
        dataSourceClassName = [ your datasource class ] ( ex: com.mysql.jdbc.jdbc2.optional.MysqlDataSource )
        dataSource.url = [ your jdbc url ] ( ex: jdbc:mysql://localhost/dbname )
        dataSource.user = [ your user name ]
        dataSource.password = [ your password ]
        ... or other hikariCP settings
    ```
    - database.properties
    ```
        init_database = [ true / false ]
        init_database_file = classpath:develope/data.sql
    ```
    - hibernate.properties
    ```
        hibernate.dialect  = org.hibernate.dialect.MySQL5Dialect
        hibernate.show_sql = true
        ...or other hibernate setting
    ```
    - openid.properties ( https://github.com/NCU-CC/OpenID-Consumer )
    - security.properties
    ```
        api_management_access = hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1') ...etc
    ```
    - oauth.properties
    ```
        oauth.access_token.expire_second = [ number ]
        oauth.auth_code.expire_second = [ number ]
    ```
