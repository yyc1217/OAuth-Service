package helper


trait BasicAuthHelper {

    def basicAuth( String id, String secret ) {
        "Basic " + ( id + ":" + secret ).bytes.encodeBase64()
    }

}