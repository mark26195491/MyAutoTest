package com.webank.autotest.eventlog;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.event.filter.EventLogUserParams;
import org.fisco.bcos.channel.event.filter.TopicTools;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by v_wbyangwang on 2019/9/19.
 */
public class TestEventLog {
    public static void main(String[] args) throws Exception {
        //String[] command = {"1","1","latest","latest","0xa2b26df2312fe12d3351e7e54652e071e37a7792","Set(string,uint256,string);string:wangyang;integer:11"};
        String[] command = args;

        int groupId = Integer.valueOf(command[1]);
        String fromBlock = command[2];
        String toBlock = command[3];
        String addressList = command[4];
        String topicList = command[5];

        // init the Service
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(5000);
        Web3j web3j = Web3j.build(channelEthereumService, groupId);

        // init Credentials
        Credentials credentials = Credentials.create(Keys.createEcKeyPair());

        /*EventLogTest contract =
                EventLogTest.load(
                        "0xa2b26df2312fe12d3351e7e54652e071e37a7792",
                        web3j,
                        credentials,
                        new StaticGasProvider(
                                new BigInteger("111111111111111"),
                                new BigInteger("11111111111111111")));*/

        EventLogUserParams params1 = new EventLogUserParams();
        params1.setFromBlock(fromBlock);
        params1.setToBlock(toBlock);
        //ArrayList<String> addresses = new ArrayList<String>();
        //addresses.add("0xa2b26df2312fe12d3351e7e54652e071e37a7792");
        List<String> addresses = Arrays.asList(addressList.split(","));
        params1.setAddresses(addresses);


        // topics, topic0设置为Event接口对应的topic
        ArrayList<Object> topics = new ArrayList<>();
        //topics.add(TopicTools.stringToTopic("Set(string,uint256,string)"));
        String[] topicParam = topicList.split(";");
        for(int i=0; i<topicParam.length; i++) {
            if(i == 0) {
                topics.add(TopicTools.stringToTopic(topicParam[i]));
            } else if(topicParam[i].indexOf("string") == 0) {
                topics.add(TopicTools.stringToTopic(topicParam[i].split(":")[1]));
                //topics.add(TopicTools.stringToTopic("wangyang"));
            } else if(topicParam[i].indexOf("integer") == 0) {
                topics.add(TopicTools.integerToTopic(new BigInteger(String.valueOf(topicParam[i].split(":")[1]))));
                //topics.add(TopicTools.integerToTopic(new BigInteger(String.valueOf(11))));
            } else if(topicParam[i].indexOf("address") == 0) {
                topics.add(TopicTools.addressToTopic(topicParam[i].split(":")[1]));
            } else if(topicParam[i].indexOf("bool") == 0) {
                topics.add(TopicTools.boolToTopic(Boolean.valueOf(topicParam[i].split(":")[1])));
            } else if(topicParam[i].indexOf("bytes") == 0) {
                topics.add(TopicTools.bytesToTopic(topicParam[i].split(":")[1].getBytes()));
            }
        }

        params1.setTopics(topics);

        // 回调，用户可以替换为自己实现的类的回调对象
        //ServiceEventLogPushCallback callback = new ServiceEventLogPushCallback();
        service.registerEventLogFilter(params1, new MyEventLogPushCallBack());

        Thread.sleep(3000);
        //contract.set("ww", new BigInteger("20"), "wang@sina.com");

        if("0".equals(command[0])) {
            System.exit(0);
        }
    }
}
