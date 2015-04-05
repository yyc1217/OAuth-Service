package data


trait DomainTestData {

    public static Date laterTime() {
        return new Date( System.currentTimeMillis() + 10000 )
    }

}