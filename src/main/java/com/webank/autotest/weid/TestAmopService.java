package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.constant.AmopMsgType;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.protocol.amop.GetEncryptKeyArgs;
import com.webank.weid.protocol.base.PolicyAndChallenge;
import com.webank.weid.protocol.response.AmopResponse;
import com.webank.weid.protocol.response.GetEncryptKeyResponse;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.AmopService;
import com.webank.weid.rpc.callback.AmopCallback;
import com.webank.weid.service.impl.AmopServiceImpl;
import com.webank.weid.service.impl.base.AmopCommonArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by v_wbyangwang on 2019/8/22.
 */
public class TestAmopService {

    private static final Logger logger = LoggerFactory.getLogger(TestAmopService.class);

    public static void main(String[] args) throws Exception {

        //logger.info("args = {}", Arrays.toString(args));
        logger.info("Show the args : {}", Arrays.toString(args));
        //System.out.printf("Show the args : %s\n", Arrays.toString(args));

        //String[] command = args[0].split("\\|");
        String[] command = args;
        //String[] command = {"registerCallback","TYPE_TRANSPORTATION","this is server"};
        //String[] command = {"request", "webank1", "this is client"};
        //did:weid:1:0xddf4f93f098d0341a2bd0961bff0d6d6c97538a0
        logger.info("Begin to execute method : {}",command[0]);
        //System.out.printf("Begin to execute method : %s\n", command[0]);

        try {
            switch (command[0]) {
                case "registerCallback":
                    testRegisterCallback(command);
                    break;
                case "request":
                    testRequest(command);
                    System.exit(0);
                    break;
                case "getPolicyAndChallenge":
                    testGetPolicyAndChallenge(command);
                    System.exit(0);
                    break;
                case "getEncryptKey":
                    testGetEncryptKey(command);
                    System.exit(0);
                    break;
                case "startPolicyAndChallenge":
                    startPolicyAndChallenge(command);
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

        //System.exit(0);
    }

    private static void testRegisterCallback(String[] command) throws Exception {
        //TYPE_ERROR, TYPE_CHECK_DIRECT_ROUTE_MSG_HEALTH, TYPE_TRANSPORTATION, GET_ENCRYPT_KEY, GET_POLICY_AND_CHALLENGE;
        String directRouteMsgType = command[1];
        String callBackMsg = command[2];

        AmopCallback amopcallBack = new AmopCallback(){

            @Override
            public AmopResponse onPush(AmopCommonArgs arg) {
                AmopResponse reponse = new AmopResponse();
                reponse.setMessageId(arg.getMessageId());
                reponse.setResult(callBackMsg);
                reponse.setErrorCode(ErrorCode.SUCCESS.getCode());
                reponse.setErrorMessage(ErrorCode.SUCCESS.getCodeDesc());
                return reponse;
            }
        };
        AmopService service = new AmopServiceImpl();
        service.registerCallback(AmopMsgType.valueOf(directRouteMsgType).getValue(), amopcallBack);

        System.out.println("{\"result:\"\"successful\"}");
    }

    private static void testRequest(String[] command) throws Exception {
        String toOrgId = command[1];
        String transMsg = command[2];

        AmopService amopService = new AmopServiceImpl();

        AmopCommonArgs arg = new AmopCommonArgs();
        arg.setMessage(transMsg);

        ResponseData<AmopResponse> response = amopService.request(toOrgId, arg);

        //System.out.println(response);
        System.out.println(JSONObject.toJSONString(response));

    }

    private static void testGetPolicyAndChallenge(String[] command) throws Exception {
        //需要先調用startPolicyAndChallenge，讓服務啟動
        String orgId = command[1];
        int policyId = Integer.valueOf(command[2]);
        String targetUserWeId = command[3];

        AmopService amopService = new AmopServiceImpl();
        ResponseData<PolicyAndChallenge> response = amopService.getPolicyAndChallenge(orgId, policyId, targetUserWeId);

        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testGetEncryptKey(String[] command) throws Exception {
        String orgId = command[1];
        String transMsg = command[2];

        AmopService amopService = new AmopServiceImpl();

        //Version version = new Version();

        GetEncryptKeyArgs arg = new GetEncryptKeyArgs();
        arg.setMessageId(transMsg);

        ResponseData<GetEncryptKeyResponse> response = amopService.getEncryptKey(orgId, arg);

        System.out.println(JSONObject.toJSONString(response));
    }

    /********************************************************************************************/
    private static void startPolicyAndChallenge(String[] command) throws Exception {
        String policyJson = command[1];

        new PolicyServiceImpl(policyJson);

        System.out.println("{\"result:\"\"successful\"}");
    }
}
