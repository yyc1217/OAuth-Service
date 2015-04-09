package tw.edu.ncu.cc.oauth.server.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tw.edu.ncu.cc.oauth.server.service.domain.ClientService

@Component
class ApiUseTimesResetTask {

    @Autowired
    def ClientService clientService

    @Scheduled( cron = "0 0 3 1 * ?" ) //three o'clock of first day in each month
    void resetApiUseTimes() {
        clientService.resetAllApiUseTimes()
    }

}
