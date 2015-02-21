package tw.edu.ncu.cc.oauth.server.helper;

import tw.edu.ncu.cc.oauth.server.helper.data.TimeUnit;

import java.util.Date;

public class TimeBuilder {

    private Date date;

    private TimeBuilder( Date date ) {
        this.date = date;
    }

    public static TimeBuilder now() {
        return of( new Date() );
    }

    public static TimeBuilder of( Date date ) {
        return new TimeBuilder( date );
    }

    public TimeBuilder after( long number, TimeUnit unit ) {
        long offset = 0;
        switch ( unit ) {
            case DAY:
                offset = number * 1000 * 60 * 60 * 24;
                break;
            case HOUR:
                offset = number * 1000 * 60 * 60;
                break;
            case MINUTE:
                offset = number * 1000 * 60;
                break;
            case SECOND:
                offset = number * 1000;
                break;
            case MILLISECOND:
                offset = number;
                break;
        }
        date = new Date( date.getTime() + offset );
        return this;
    }

    public TimeBuilder before( long number, TimeUnit unit ) {
        return after( -number, unit );
    }

    public Date buildDate() {
        return date;
    }

}
