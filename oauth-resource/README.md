## OAuth-Resource 
OAuth Resource Server Tool Sets writtern in Java

### TokenAccessDecisionFilter
verify oauth resource request using **Authorization** header and **Bearer** token.
set **GrantedAuthority** in security context if authorized.

- inject a TokenConfirmService instance with set function
- built-in implementation : TokenConfirmServiceImpl
    ```
        <bean id="tokenCheckFilter" class="tw.edu.ncu.cc.oauth.resource.filter.TokenAccessDecisionFilter">
                <property name="tokenConfirmService">
                    <bean class="tw.edu.ncu.cc.oauth.resource.service.TokenConfirmServiceImpl">
                        <property name="config">
                            <bean class="tw.edu.ncu.cc.oauth.resource.config.RemoteConfig">
                                <property name="addrPrefix" value="https://localhost:8080/token/string/"/>
                            </bean>
                        </property>
                    </bean>
                </property>
        </bean>
    ```
    ```
        <security:http pattern="/api/**" create-session="never">
            <security:custom-filter ref="tokenCheckFilter" after="LAST"/>
        </security:http>
    ```

- remote oauth server implementation ( :oauth-server module )
    > tw.edu.ncu.cc.oauth.server.controller.management.TokenController::getTokenByString