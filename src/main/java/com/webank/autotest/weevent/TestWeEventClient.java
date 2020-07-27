package com.webank.autotest.weevent;

import com.alibaba.fastjson.JSONObject;
import com.webank.weevent.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by v_wbyangwang on 2019/9/19.
 */
public class TestWeEventClient {
    private static final Logger logger = LoggerFactory.getLogger(TestWeEventClient.class);

    /*public static void main(String[] args) throws Exception {

        logger.info("Show the args : {}", Arrays.toString(args));
        String[] command = args;
        logger.info("Begin to execute method : {}",command[0]);

        try {
            switch (command[1]) {
                case "subscribe":
                    testSubscribe(command);
                    break;
                case "publish":
                    testPublish(command);
                    break;
                case "unSubscribe":
                    testUnSubscribe(command);
                    break;
                case "exist":
                    testExist(command);
                    break;
                case "list":
                    testList(command);
                    break;
                case "stat":
                    testStat(command);
                    break;
                case "getEvent":
                    testGetEvent(command);
                    break;
                default:
                    System.out.println("Not found the method, please check.");
                    System.exit(0);
                    break;
            }
        } catch (Exception e) {
            //logger.error("execute {} failed.{}",Arrays.toString(command), e.getMessage());
            System.out.println("Execute failed, please check the log.");
            System.exit(1);
        }

        if("0".equals(command[0])) {
            System.exit(0);
        }

    }

    private static void testSubscribe(String[] command) throws Exception {
        String brokerUrl = command[2];
        String groupId = command[3];
        String topicName = command[4];
        String offset = command[5];

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        // ensure topic exist
        client.open(topicName, groupId);

        // subscribe topic
        String subscriptionId = client.subscribe(topicName, offset, new IWeEventClient.EventListener() {
            @Override
            public void onEvent(WeEvent event) {
                //System.out.println("content: " + new String(event.getContent()));
                System.out.println("eventId:"+event.getEventId());
                System.out.println("content:"+new String(event.getContent()));
                Map<String, String> map = event.getExtensions();
                for(Map.Entry<String, String> entry : map.entrySet()) {
                    System.out.println("extensions:"+entry.getKey()+"|"+entry.getValue());
                }

            }

            @Override
            public void onException(Throwable e) {
                System.out.println("ERROR!!!");
                e.printStackTrace();
            }

        });

        System.out.println("subscriptionId:"+subscriptionId);
    }

    private static void testPublish(String[] command) throws Exception {
        String brokerUrl = command[2];
        String groupId = command[3];
        String topicName = command[4];
        String extensionsStr = command[5];
        String[] tmp_ext = extensionsStr.split(":");
        String content = command[6];

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        // ensure topic exist
        client.open(topicName, groupId);

        // set extensions
        Map<String, String> extensions = new HashMap<>();
        extensions.put(tmp_ext[0], tmp_ext[1]);

        // publish
        SendResult sendResult = client.publish(topicName, content.getBytes(), extensions);
        System.out.println(JSONObject.toJSONString(sendResult));
    }

    private static void testUnSubscribe(String[] command) throws Exception {
        String brokerUrl = command[2];
        String subscriptionId = command[3];

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        // ensure topic exist
        //client.open(topicName, groupId);

        boolean result = client.unSubscribe(subscriptionId);

        System.out.println(result);
    }

    private static void testExist(String[] command) throws Exception {
        String brokerUrl = command[2];
        String groupId = command[3];
        String topicName = command[4];

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        boolean result = client.exist(topicName, groupId);

        System.out.println(result);
    }

    private static void testList(String[] command) throws Exception {
        String brokerUrl = command[2];
        String groupId = command[3];
        int pageIndex = Integer.valueOf(command[4]);
        int pageSize = Integer.valueOf(command[5]);

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        TopicPage topicPage = client.list(pageIndex, pageSize, groupId);

        System.out.println(JSONObject.toJSONString(topicPage));
    }

    private static void testStat(String[] command) throws Exception {
        String brokerUrl = command[2];
        String groupId = command[3];
        String topicName = command[4];

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        TopicInfo topicInfo = client.state(topicName, groupId);

        System.out.println(JSONObject.toJSONString(topicInfo));
    }

    private static void testGetEvent(String[] command) throws Exception {
        String brokerUrl = command[2];
        String groupId = command[3];
        String eventId = command[4];

        // get client
        IWeEventClient client = IWeEventClient.build(brokerUrl);

        WeEvent weEvent = client.getEvent(eventId, groupId);

        System.out.println(JSONObject.toJSONString(weEvent));
    }*/
}
