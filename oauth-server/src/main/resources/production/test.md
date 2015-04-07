1. add permissions to database 

2. post to `management/v1/users` in json: `{ "name" : "username" }`

3. post to `/management/v1/client` in json and store response:

    ```
    {
        "name" : "ncu-conflux",
        "description" : "none",
        "url" : "http://www.gamer.com.tw",
        "callback" : "http://www.gamer.com.tw",
        "owner" : "101502549"
    }
    ```

4. get `/oauth/authorize` with parameters in `x-www-form-urlencoded` and store response:

    - `response_type` : code
    - `scope` : [ permissions, divided by '+' ]
    - `state` : [ optional, whatever you want ]
    - `client_id` : [ in previous response 3 ]

5. get `/oauth/token` with parameters in `x-www-form-urlencoded` and store response:
    
    - `code` : [ in previous response 4 ]
    - `grant_type` : authorization_code
    - `client_id` : [ in previous response 3 ]
    - `client_secret` : [ in previous response 3 ]
    
6. get `/oauth/token` with parameters in `x-www-form-urlencoded` and store response:

    - `grant_type` : client_credentials
    - `client_id` : [ in previous response 3 ]
    - `client_secret` : [ in previous response 3 ]