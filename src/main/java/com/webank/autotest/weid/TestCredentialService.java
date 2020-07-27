package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.Credential;
import com.webank.weid.protocol.base.CredentialWrapper;
import com.webank.weid.protocol.base.WeIdPublicKey;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CredentialService;
import com.webank.weid.service.impl.CredentialServiceImpl;
import com.webank.weid.util.DataToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by v_wbyangwang on 2019/8/21.
 */
public class TestCredentialService {

    private static final Logger logger = LoggerFactory.getLogger(TestCredentialService.class);

    public static void main(String[] args) throws Exception {

        //logger.info("args = {}", Arrays.toString(args));
        logger.info("Show the args : {}", Arrays.toString(args));
        //System.out.printf("Show the args : %s\n", Arrays.toString(args));

        //String[] command = args[0].split("\\|");
        String[] command = args;
        //String[] command = {"verify", "did:weid:1:0x04cff4fe4751c5743a545131ca66a7367ed37b1d", "11462269189168621297876452042617042515202332156770407612746009304611022455743", "1000", "2019-10-01 00:00:00", "{\"name\":\"wangyang\",\"gender\":\"M\",\"age\":30}"};
        //String[] command = {"verifyNew", "{\"claim\":{\"age\":30,\"gender\":\"M\",\"name\":\"wangyang\"},\"context\":\"https://github.com/WeBankFinTech/WeIdentity/blob/master/context/v1\",\"cptId\":1000,\"expirationDate\":1569859200000,\"id\":\"822fe453-0f12-4f8b-bda5-4cef3891abc6\",\"issuanceDate\":1567505818081,\"issuer\":\"did:weid:1:0x04cff4fe4751c5743a545131ca66a7367ed37b1d\",\"proof\":{\"created\":\"1567505818081\",\"creator\":\"did:weid:1:0x04cff4fe4751c5743a545131ca66a7367ed37b1d\",\"signature\":\"G9GCjq/bqZKk9IEjpqt1MDH3p6bcf5MGvg9cx/q3NJH8B7FxSNv2mUCLyp+jQcpnHAKQizslUL4ySZZ1rK1Oi9U=\",\"type\":\"EcdsaSignature\"}}"};
        logger.info("Begin to execute method : {}",command[0]);
        //System.out.printf("Begin to execute method : %s\n", command[0]);

        try {
            switch (command[0]) {
                case "createCredential":
                    testCreateCredential(command);
                    break;
                case "createCredentialNew":
                    testCreateCredentialNew(command);
                    break;
                case "verify":
                    testVerify(command);
                    break;
                case "verifyNew":
                    testVerifyNew(command);
                    break;
                case "verifyCredentialWithSpecifiedPubKey":
                    testVerifyCredentialWithSpecifiedPubKey(command);
                    break;
                case "getCredentialHash":
                    testGetCredentialHash(command);
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

    private static void testCreateCredential(String[] command) throws Exception {
        /*String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];*/

        CredentialService credentialService = new CredentialServiceImpl();
        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);

        String credentialHash = credentialService.getCredentialHash(response.getResult().getCredential()).getResult();

        StringBuilder stringBuilder = new StringBuilder(JSONObject.toJSONString(response));
        stringBuilder.insert(stringBuilder.lastIndexOf("}"), String.format(",\"%s\" : \"%s\"", "credentialHash", credentialHash));

        System.out.println(stringBuilder.toString());
    }

    private static void testCreateCredentialNew(String[] command) throws Exception {
        /*String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];*/

        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);
        Credential credential = response.getResult().getCredential();
        String rst = DataToolUtils.serialize(credential);

        System.out.println(rst);
    }

    private static void testVerify(String[] command) throws Exception {
        /*String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];*/

        CredentialService credentialService = new CredentialServiceImpl();
        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);

        //验证Credential
        ResponseData<Boolean> responseVerify = credentialService.verify(response.getResult().getCredential());

        System.out.println(JSONObject.toJSONString(responseVerify));
    }

    private static void testVerifyNew(String[] command) throws Exception {
        String credentialJson = command[1];

        CredentialService credentialService = new CredentialServiceImpl();
        Credential credential = DataToolUtils.deserialize(credentialJson, Credential.class);

        //验证Credential
        ResponseData<Boolean> responseVerify = credentialService.verify(credential);

        System.out.println(JSONObject.toJSONString(responseVerify));
    }

    private static void testVerifyCredentialWithSpecifiedPubKey(String[] command) throws Exception {
        /*String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];*/
        String publicKey = command[6];

        CredentialService credentialService = new CredentialServiceImpl();
        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);

        WeIdPublicKey weIdPublicKey = new WeIdPublicKey();
        weIdPublicKey.setPublicKey(publicKey);

        //使用公钥验证
        ResponseData<Boolean> responseVerify = credentialService
                .verifyCredentialWithSpecifiedPubKey(response.getResult(), weIdPublicKey);

        System.out.println(JSONObject.toJSONString(responseVerify));
    }

    private static void testGetCredentialHash(String[] command) throws Exception {
        /*String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];*/

        CredentialService credentialService = new CredentialServiceImpl();
        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);

        //获取Credentia的Hash
        ResponseData<String> responseHash = credentialService.getCredentialHash(response.getResult().getCredential());

        System.out.println(JSONObject.toJSONString(responseHash));
    }
}
