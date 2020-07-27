package com.webank.autotest.eventlog;

import org.fisco.bcos.channel.event.filter.ServiceEventLogPushCallback;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;

import java.util.List;

/**
 * Created by v_wbyangwang on 2019/9/17.
 */
public class MyEventLogPushCallBack extends ServiceEventLogPushCallback {
    @Override
    public void onPushEventLog(int status, List<LogResult> logs) {
        System.out.println("status:" + status);

        for (LogResult log:logs) {
            System.out.println(log.toString());
        }
    }
}
