## OAuth-Server [![Build Status](http://140.115.3.96:8080/jenkins/buildStatus/icon?job=OAuth-Service)](http://140.115.3.96:8080/jenkins/job/OAuth-Service/)
OAuth Athorization Server writtern in Java

### Dependencies
- c3p0 0.9.1.2
- ehcache 2.9.0
- hibernate 4.3.6
- oltu.oauth2 1.0.0
- spring-webmvc 4.1.1
- spring-security 4.0.0.M2

### Gradle
- jettyStart : run embedded server
- jettyStop  : stop embedded server above

### Resources
resources are divided into two environments for Spring

- develope : include embedded database and mocked elements

- production : put following files into **src/main/resources/production**
    - cache-setting.xml ( standard ehcahe setting, must exist a cache 'permissionDictionary' )
    - connection.properties
    ```
        min_size = 5
        max_size = 75
        ...or other c3p0 settings
        user     = [ your database user name ]
        password = [ your database user password ]
    ```
    - database.properties
    ```
        jdbc.driver = com.mysql.jdbc.Driver
        jdbc.url = jdbc:mysql://localhost/dbname
        init_database = true
        init_database_file = classpath:develope/data.sql
    ```
    - hibernate.properties
    ```
        hibernate.dialect  = org.hibernate.dialect.MySQL5Dialect
        hibernate.show_sql = true
        ...or other hibernate setting
    ```
    - openid-setting.properties ( https://github.com/NCU-CC/OpenID-Consumer )
    - security.properties
    ```
        api_management_access = hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1') ...etc
    ```
