package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.suite.api.persistence.Persistence;
import com.webank.weid.suite.persistence.sql.driver.MysqlDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by v_wbyangwang on 2019/8/22.
 */
public class TestPersistence {

    private static final Logger logger = LoggerFactory.getLogger(TestPersistence.class);

    public static void main(String[] args) throws Exception {

        //logger.info("args = {}", Arrays.toString(args));
        logger.info("Show the args : {}", Arrays.toString(args));
        //System.out.printf("Show the args : %s\n", Arrays.toString(args));

        //String[] command = args[0].split("\\|");
        String[] command = args;
        //did:weid:1:0xddf4f93f098d0341a2bd0961bff0d6d6c97538a0
        logger.info("Begin to execute method : {}",command[0]);
        //System.out.printf("Begin to execute method : %s\n", command[0]);

        try {
            switch (command[0]) {
                case "save":
                    testSave(command);
                    break;
                case "batchSave":
                    testBatchSave(command);
                    break;
                case "get":
                    testGet(command);
                    break;
                case "delete":
                    testDelete(command);
                    break;
                case "update":
                    testUpdate(command);
                    break;
                default:
                    System.out.println("Not found the method, please check.");
                    break;
            }
        } catch (Exception e) {
            //logger.error("execute {} failed.{}",Arrays.toString(command), e.getMessage());
            System.out.println("Execute failed, please check the log.");
            System.exit(1);
        }

        System.exit(0);
    }

    private static void testSave(String[] command) throws Exception {
        String domain = command[1];
        String id = command[2];
        String data = command[3];

        Persistence persistence = new MysqlDriver();
        ResponseData<Integer> response = persistence.save(domain, id, data);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testBatchSave(String[] command) throws Exception {
        String domain = command[1];
        //逗号隔开
        String id = command[2];
        //逗号隔开
        String data = command[3];

        List<String> ids = Arrays.asList(id.split(","));
        List<String> datas = Arrays.asList(data.split(","));

        Persistence persistence = new MysqlDriver();
        ResponseData<Integer> response = persistence.batchSave(domain, ids, datas);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testGet(String[] command) throws Exception {
        String domain = command[1];
        String id = command[2];

        Persistence persistence = new MysqlDriver();
        ResponseData<String> response = persistence.get(domain, id);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testDelete(String[] command) throws Exception {
        String domain = command[1];
        String id = command[2];

        Persistence persistence = new MysqlDriver();
        ResponseData<Integer> response = persistence.delete(domain, id);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testUpdate(String[] command) throws Exception {
        String domain = command[1];
        String id = command[2];
        String data = command[3];

        Persistence persistence = new MysqlDriver();
        ResponseData<Integer> response = persistence.update(domain, id, data);

        System.out.println(JSONObject.toJSONString(response));
    }
}
