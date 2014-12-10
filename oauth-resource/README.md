## OAuth-Resource 
OAuth Resource Server Tool Sets writtern in Java

## TokenAccessDecisionFilter
Verify oauth resource request using **Authorization** header and **Bearer** token. 
Set **GrantedAuthority** in security context if authorized.

- Inject a TokenConfirmService instance with set function
- Built-in implementation : TokenConfirmServiceImpl