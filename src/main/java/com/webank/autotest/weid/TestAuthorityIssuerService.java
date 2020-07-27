package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.util.FileUtils;
import com.webank.weid.protocol.base.AuthorityIssuer;
import com.webank.weid.protocol.base.WeIdAuthentication;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.request.RegisterAuthorityIssuerArgs;
import com.webank.weid.protocol.request.RemoveAuthorityIssuerArgs;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.AuthorityIssuerService;
import com.webank.weid.service.impl.AuthorityIssuerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by v_wbyangwang on 2019/8/19.
 */
public class TestAuthorityIssuerService {

    private static final Logger logger = LoggerFactory.getLogger(TestAuthorityIssuerService.class);
    private static final String SDK_PRIVKEY_PATH = "ecdsa_key";

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
                case "registerAuthorityIssuer":
                    testRegisterAuthorityIssuer(command);
                    break;
                case "removeAuthorityIssuer":
                    testRemoveAuthorityIssuer(command);
                    break;
                case "isAuthorityIssuer":
                    testIsAuthorityIssuer(command);
                    break;
                case "queryAuthorityIssuerInfo":
                    testQueryAuthorityIssuerInfo(command);
                    break;
                case "getAllAuthorityIssuerList":
                    testGetAllAuthorityIssuerList(command);
                    break;
                case "registerIssuerType":
                    testRegisterIssuerType(command);
                    break;
                case "addIssuerIntoIssuerType":
                    testAddIssuerIntoIssuerType(command);
                    break;
                case "removeIssuerFromIssuerType":
                    testRemoveIssuerFromIssuerType(command);
                    break;
                case "isSpecificTypeIssuer":
                    testIsSpecificTypeIssuer(command);
                    break;
                case "getAllSpecificTypeIssuerList":
                    testGetAllSpecificTypeIssuerList(command);
                    break;
                case "getSDKPrivateKey":
                    getSDKPrivateKey();
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

    private static void testRegisterAuthorityIssuer(String[] command) throws Exception {
        String weId = command[1];
        String name = command[2];
        String accValue = command[3];
        String privateKey = command[4];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        AuthorityIssuer authorityIssuer = new AuthorityIssuer();
        authorityIssuer.setWeId(weId);
        authorityIssuer.setName(name);
        authorityIssuer.setAccValue(accValue);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        RegisterAuthorityIssuerArgs registerAuthorityIssuerArgs = new RegisterAuthorityIssuerArgs();
        registerAuthorityIssuerArgs.setAuthorityIssuer(authorityIssuer);
        registerAuthorityIssuerArgs.setWeIdPrivateKey(weIdPrivateKey);

        ResponseData<Boolean> response = authorityIssuerService.registerAuthorityIssuer(registerAuthorityIssuerArgs);
        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testRemoveAuthorityIssuer(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        RemoveAuthorityIssuerArgs removeAuthorityIssuerArgs = new RemoveAuthorityIssuerArgs();
        removeAuthorityIssuerArgs.setWeId(weId);
        removeAuthorityIssuerArgs.setWeIdPrivateKey(weIdPrivateKey);

        ResponseData<Boolean> response = authorityIssuerService.removeAuthorityIssuer(removeAuthorityIssuerArgs);
        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testIsAuthorityIssuer(String[] command) throws Exception {
        String weId = command[1];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        ResponseData<Boolean> response = authorityIssuerService.isAuthorityIssuer(weId);
        //System.out.println(response.getResult().booleanValue());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testQueryAuthorityIssuerInfo(String[] command) throws Exception {
        String weId = command[1];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        ResponseData<AuthorityIssuer> response = authorityIssuerService.queryAuthorityIssuerInfo(weId);
        /*String rst = "".concat("{")
                .concat("\"WeId\" : \"" + response.getResult().getWeId() + "\"")
                .concat("\"AccValue\" : \"" + response.getResult().getAccValue() + "\"")
                .concat("\"Name\" : \"" + response.getResult().getName() + "\"")
                .concat("\"Created\" : \"" + response.getResult().getCreated() + "\"")
                .concat("}");
        System.out.println(rst);*/
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testGetAllAuthorityIssuerList(String[] command) throws Exception {
        int index = Integer.valueOf(command[1]);
        int num = Integer.valueOf(command[2]);

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        ResponseData<List<AuthorityIssuer>> response = authorityIssuerService.getAllAuthorityIssuerList(index, num);
        /*String rst = JSONObject.toJSONString(response.getResult());
        System.out.println(rst);*/
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testRegisterIssuerType(String[] command) throws Exception {
        String weId = command[1];
        String weIdPublicKeyId = command[2];
        String privateKey = command[3];
        String issuerType = command[4];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);
        weIdAuthentication.setWeIdPublicKeyId(weIdPublicKeyId);

        ResponseData<Boolean> response = authorityIssuerService.registerIssuerType(weIdAuthentication, issuerType);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testAddIssuerIntoIssuerType(String[] command) throws Exception {
        String weId = command[1];
        String weIdPublicKeyId = command[2];
        String privateKey = command[3];
        String issuerType = command[4];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        weIdAuthentication.setWeIdPublicKeyId(weIdPublicKeyId);
        ResponseData<Boolean> response = authorityIssuerService.addIssuerIntoIssuerType(weIdAuthentication, issuerType, weId);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testRemoveIssuerFromIssuerType(String[] command) throws Exception {
        String weId = command[1];
        String weIdPublicKeyId = command[2];
        String privateKey = command[3];
        String issuerType = command[4];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        weIdAuthentication.setWeIdPublicKeyId(weIdPublicKeyId);
        ResponseData<Boolean> response = authorityIssuerService.removeIssuerFromIssuerType(weIdAuthentication, issuerType, weId);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testIsSpecificTypeIssuer(String[] command) throws Exception {
        String weId = command[1];
        String issuerType = command[2];

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        ResponseData<Boolean> response = authorityIssuerService.isSpecificTypeIssuer(issuerType, weId);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testGetAllSpecificTypeIssuerList(String[] command) throws Exception {
        String issuerType = command[1];
        int index = Integer.valueOf(command[2]);
        int num = Integer.valueOf(command[3]);

        AuthorityIssuerService authorityIssuerService = new AuthorityIssuerServiceImpl();

        ResponseData<List<String>> response = authorityIssuerService.getAllSpecificTypeIssuerList(issuerType, index, num);

        System.out.println(JSONObject.toJSONString(response));
    }

    /***********************************************************************************/
    private static void getSDKPrivateKey() throws Exception {
        String sdkPrivateKey = FileUtils.readFile(SDK_PRIVKEY_PATH);

        System.out.println(sdkPrivateKey);
    }
}
