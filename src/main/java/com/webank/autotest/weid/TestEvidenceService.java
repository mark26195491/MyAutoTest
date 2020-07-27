package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.CredentialWrapper;
import com.webank.weid.protocol.base.EvidenceInfo;
import com.webank.weid.protocol.base.WeIdPrivateKey;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.EvidenceService;
import com.webank.weid.service.impl.EvidenceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by v_wbyangwang on 2019/8/21.
 */
public class TestEvidenceService {

    private static final Logger logger = LoggerFactory.getLogger(TestEvidenceService.class);

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
                case "createEvidence":
                    testCreateEvidence(command);
                    break;
                case "getEvidence":
                    testGetEvidence(command);
                    break;
                case "verify":
                    testVerify(command);
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

    private static void testCreateEvidence(String[] command) throws Exception {
        //String weId = command[1];
        String privateKey = command[2];
        //int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        //String expirationDate = command[4];
        //String claimJsonStr = command[5];

        EvidenceService evidenceService = new EvidenceServiceImpl();
        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        //创建Evidence Address
        ResponseData<String> responseCreateEvidence = evidenceService.createEvidence(response.getResult().getCredential(), weIdPrivateKey);

        System.out.println(JSONObject.toJSONString(responseCreateEvidence));
    }

    private static void testGetEvidence(String[] command) throws Exception {
        String evidenceAddress = command[1];

        EvidenceService evidenceService = new EvidenceServiceImpl();
        ResponseData<EvidenceInfo> response = evidenceService.getEvidence(evidenceAddress);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testVerify(String[] command) throws Exception {
        //String weId = command[1];
        String privateKey = command[2];
        //int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        //String expirationDate = command[4];
        //String claimJsonStr = command[5];

        EvidenceService evidenceService = new EvidenceServiceImpl();
        ResponseData<CredentialWrapper> response = WeIdCommon.createCredential(command);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        //创建Evidence Address
        ResponseData<String> responseCreateEvidence = evidenceService.createEvidence(response.getResult().getCredential(), weIdPrivateKey);

        String evidenceAddress = responseCreateEvidence.getResult();

        //验证Credential by evidenceAddress
        //ResponseData<Boolean> responseVerify = evidenceService.verify(response.getResult().getCredential(), evidenceAddress);

        //System.out.println(JSONObject.toJSONString(responseVerify));
    }
}
