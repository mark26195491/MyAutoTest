package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.autotest.fisco.FiscoService;
import com.webank.weid.protocol.base.WeIdDocument;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.CreateWeIdArgs;
import com.webank.weid.protocol.request.SetAuthenticationArgs;
import com.webank.weid.protocol.request.SetPublicKeyArgs;
import com.webank.weid.protocol.request.SetServiceArgs;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.WeIdServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by v_wbyangwang on 2019/8/16.
 */
public class TestWeIdService {

    private static final Logger logger = LoggerFactory.getLogger(TestWeIdService.class);

    public static void main(String[] args) throws Exception {

        //logger.info("args = {}", Arrays.toString(args));
        logger.info("Show the args : {}", Arrays.toString(args));
        //System.out.printf("Show the args : %s\n", Arrays.toString(args));

        //String[] command = args[0].split("\\|");
        String[] command = args;
        //String[] command = {"start"};
        //did:weid:1:0xddf4f93f098d0341a2bd0961bff0d6d6c97538a0
        logger.info("Begin to execute method : {}",command[0]);
        //System.out.printf("Begin to execute method : %s\n", command[0]);

        try {
            switch (command[0]) {
                case "createWeId_1":
                    testCreateWeId_1();
                    System.exit(0);
                    break;
                case "createWeId_2":
                    testCreateWeId_2(command);
                    System.exit(0);
                    break;
                case "getWeIdDocumentJson":
                    testGetWeIdDocumentJson(command);
                    System.exit(0);
                    break;
                case "getWeIdDocument":
                    testGetWeIdDocument(command);
                    System.exit(0);
                    break;
                case "setPublicKey":
                    testSetPublicKey(command);
                    System.exit(0);
                    break;
                case "setService":
                    testSetService(command);
                    System.exit(0);
                    break;
                case "setAuthentication":
                    testSetAuthentication(command);
                    System.exit(0);
                    break;
                case "isWeIdExist":
                    testIsWeIdExist(command);
                    System.exit(0);
                    break;
                case "createKey":
                    createKey();
                    System.exit(0);
                    break;
                case "startWeId":
                    startWeId();
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


    }

    private static void testCreateWeId_1() throws Exception {
        WeIdService weIdService = new WeIdServiceImpl();

        ResponseData<CreateWeIdDataResult> createResponse = weIdService.createWeId();
        /*CreateWeIdDataResult weIdData = createResponse.getResult();
        ResponseData<String> weIdDocumentJson = weIdService.getWeIdDocumentJson(weIdData.getWeId());

        String rst = "".concat("[\n")
                       .concat(weIdDocumentJson.getResult() + ",\n")
                       .concat("{ \"privateKey\" : \"" + weIdData.getUserWeIdPrivateKey().getPrivateKey() + "\" }\n")
                       .concat("]\n");
        System.out.println(rst);*/
        System.out.println(JSONObject.toJSONString(createResponse));

    }

    private static void testCreateWeId_2(String[] command) throws Exception {
        String publicKey = command[1];
        String privateKey = command[2];

        WeIdService weIdService = new WeIdServiceImpl();

        CreateWeIdArgs createWeIdArgs = new CreateWeIdArgs();
        createWeIdArgs.setPublicKey(publicKey);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        createWeIdArgs.setWeIdPrivateKey(weIdPrivateKey);

        ResponseData<String> response = weIdService.createWeId(createWeIdArgs);
        /*ResponseData<String> weIdDocumentJson = weIdService.getWeIdDocumentJson(response.getResult());

        String rst = "".concat("[\n")
                .concat(weIdDocumentJson.getResult() + ",\n")
                .concat("{ \"privateKey\" : \"" + privateKey + "\" }\n")
                .concat("]\n");
        System.out.println(rst);*/

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testGetWeIdDocumentJson(String[] command) throws Exception {
        String weId = command[1];

        WeIdService weIdService = new WeIdServiceImpl();

        ResponseData<String> response = weIdService.getWeIdDocumentJson(weId);

        System.out.println(response.getResult());
    }

    private static void testGetWeIdDocument(String[] command) throws Exception {
        String weId = command[1];

        WeIdService weIdService = new WeIdServiceImpl();

        ResponseData<WeIdDocument> response = weIdService.getWeIdDocument(weId);
        //System.out.println(response.getResult().toJson());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testSetPublicKey(String[] command) throws Exception {
        String weId = command[1];
        String publicKey = command[2];
        String privateKey = command[3];

        WeIdService weIdService = new WeIdServiceImpl();

        SetPublicKeyArgs setPublicKeyArgs = new SetPublicKeyArgs();
        setPublicKeyArgs.setWeId(weId);
        setPublicKeyArgs.setPublicKey(publicKey);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        setPublicKeyArgs.setUserWeIdPrivateKey(weIdPrivateKey);

        ResponseData<Boolean> response = weIdService.setPublicKey(setPublicKeyArgs);

        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testSetService(String[] command) throws Exception {
        String weId = command[1];
        String type = command[2];
        String serviceEndpoint = command[3];
        String privateKey = command[4];

        WeIdService weIdService = new WeIdServiceImpl();

        SetServiceArgs setServiceArgs = new SetServiceArgs();
        setServiceArgs.setWeId(weId);
        setServiceArgs.setType(type);
        setServiceArgs.setServiceEndpoint(serviceEndpoint);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        setServiceArgs.setUserWeIdPrivateKey(weIdPrivateKey);

        ResponseData<Boolean> response = weIdService.setService(setServiceArgs);

        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testSetAuthentication(String[] command) throws Exception {
        String weId = command[1];
        //String owner = command[2];
        String publicKey = command[2];
        String privateKey = command[3];

        WeIdService weIdService = new WeIdServiceImpl();

        SetAuthenticationArgs setAuthenticationArgs = new SetAuthenticationArgs();
        setAuthenticationArgs.setWeId(weId);
        setAuthenticationArgs.setPublicKey(publicKey);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        setAuthenticationArgs.setUserWeIdPrivateKey(weIdPrivateKey);

        ResponseData<Boolean> response = weIdService.setAuthentication(setAuthenticationArgs);

        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testIsWeIdExist(String[] command) throws Exception {
        String weId = command[1];

        WeIdService weIdService = new WeIdServiceImpl();
        ResponseData<Boolean> response = weIdService.isWeIdExist(weId);

        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    /***********************************************************************************/
    private static void createKey() throws Exception {
        FiscoService fiscoService = new FiscoService();

        String rst = fiscoService.createKey();

        System.out.println(rst);
    }

    private static void startWeId() throws Exception {
        WeIdService weIdService = new WeIdServiceImpl();

        System.out.println("Start successful");
    }
}
