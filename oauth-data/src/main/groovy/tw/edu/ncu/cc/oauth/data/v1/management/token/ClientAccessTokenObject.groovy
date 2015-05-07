package tw.edu.ncu.cc.oauth.data.v1.management.token;

import tw.edu.ncu.cc.oauth.data.v1.management.client.IdClientObject;

public class ClientAccessTokenObject {

    def String id
    def String user
    def String[] scope
    def Date last_updated
    def IdClientObject client

}
