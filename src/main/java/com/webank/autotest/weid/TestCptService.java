package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.*;
import com.webank.weid.protocol.request.CptStringArgs;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CptService;
import com.webank.weid.service.impl.CptServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by v_wbyangwang on 2019/8/20.
 */
public class TestCptService {

    private static final Logger logger = LoggerFactory.getLogger(TestCptService.class);

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
                case "registerCpt_1":
                    testRegisterCpt_1(command);
                    break;
                case "registerCpt_2":
                    testRegisterCpt_2(command);
                    break;
                case "queryCpt":
                    testQueryCpt(command);
                    break;
                case "updateCpt_1":
                    testUpdateCpt_1(command);
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

    private static void testRegisterCpt_1(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];
        String cptJsonSchema = command[3];

        CptService cptService = new CptServiceImpl();

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        CptStringArgs cptStringArgs = new CptStringArgs();
        cptStringArgs.setCptJsonSchema(cptJsonSchema);
        cptStringArgs.setWeIdAuthentication(weIdAuthentication);

        ResponseData<CptBaseInfo> response = cptService.registerCpt(cptStringArgs);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testRegisterCpt_2(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];
        String cptJsonSchema = command[3];
        int cptId = Integer.valueOf(command[4]);

        CptService cptService = new CptServiceImpl();

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        CptStringArgs cptStringArgs = new CptStringArgs();
        cptStringArgs.setCptJsonSchema(cptJsonSchema);
        cptStringArgs.setWeIdAuthentication(weIdAuthentication);

        ResponseData<CptBaseInfo> response = cptService.registerCpt(cptStringArgs, cptId);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testQueryCpt(String[] command) throws Exception {
        int cptId = Integer.valueOf(command[1]);

        CptService cptService = new CptServiceImpl();

        ResponseData<Cpt> response = cptService.queryCpt(cptId);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testUpdateCpt_1(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];
        String jsonSchema = command[3];
        int cptId = Integer.valueOf(command[4]);

        CptService cptService = new CptServiceImpl();

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        CptStringArgs cptStringArgs = new CptStringArgs();
        cptStringArgs.setCptJsonSchema(jsonSchema);
        cptStringArgs.setWeIdAuthentication(weIdAuthentication);

        ResponseData<CptBaseInfo> response = cptService.updateCpt(cptStringArgs, cptId);

        System.out.println(JSONObject.toJSONString(response));
    }
}
