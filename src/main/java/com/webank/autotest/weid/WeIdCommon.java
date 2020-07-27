package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.*;
import com.webank.weid.protocol.request.CreateCredentialArgs;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.rpc.CredentialService;
import com.webank.weid.rpc.WeIdService;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.service.impl.CredentialServiceImpl;
import com.webank.weid.service.impl.WeIdServiceImpl;
import com.webank.weid.util.DataToolUtils;
import com.webank.weid.util.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by v_wbyangwang on 2019/8/30.
 */
public class WeIdCommon {

    public static ResponseData<CredentialWrapper> createCredential(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];

        //System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 100
        Long timeStamp = DateUtils.converDateToTimeStamp(expirationDate);
        //Map<String, Object> claimMap = JSONObject.parseObject(claimJsonStr).getInnerMap();
        Map<String, Object> claimMap = null;

        CredentialService credentialService = new CredentialServiceImpl();
        CreateCredentialArgs createCredentialArgs = new CreateCredentialArgs();
        createCredentialArgs.setClaim(claimMap);
        createCredentialArgs.setCptId(cptId);
        createCredentialArgs.setExpirationDate(timeStamp);
        createCredentialArgs.setIssuer(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);

        createCredentialArgs.setWeIdPrivateKey(weIdPrivateKey);

        ResponseData<CredentialWrapper> response = credentialService.createCredential(createCredentialArgs);

        return response;
    }

    public static ResponseData<CredentialPojo> createCredentialPojo(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];
        String weIdPublicKeyId = command[6];

        Long timeStamp = DateUtils.converDateToTimeStamp(expirationDate);
        //Map<String, Object> claimMap = JSONObject.parseObject(claimJsonStr).getInnerMap();
        Map<String, Object> claimMap = null;
        
        CredentialPojoService credentialPojoService = new CredentialPojoServiceImpl();
        CreateCredentialPojoArgs<Map<String, Object>> createCredentialPojoArgs = new CreateCredentialPojoArgs<Map<String, Object>>();
        createCredentialPojoArgs.setCptId(cptId);
        createCredentialPojoArgs.setIssuer(weId);
        createCredentialPojoArgs.setExpirationDate(timeStamp);

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        weIdAuthentication.setWeIdPublicKeyId(weIdPublicKeyId);
        createCredentialPojoArgs.setWeIdAuthentication(weIdAuthentication);

        createCredentialPojoArgs.setClaim(claimMap);

        ResponseData<CredentialPojo> response = credentialPojoService.createCredential(createCredentialPojoArgs);

        return response;
    }

    public static ResponseData<PresentationE> createPresentation(String[] command) throws Exception {
        String weId = command[1];
        String privateKey = command[2];
        int cptId = Integer.valueOf(command[3]);
        //yyyy-mm-dd hh24:mi:ss
        String expirationDate = command[4];
        String claimJsonStr = command[5];
        String weIdPublicKeyId = command[6];
        String policyJson = command[7];

        Long timeStamp = DateUtils.converDateToTimeStamp(expirationDate);
        //Map<String, Object> claimMap = JSONObject.parseObject(claimJsonStr).getInnerMap();
        Map<String, Object> claimMap = null;

        CredentialPojoService credentialPojoService = new CredentialPojoServiceImpl();
        CreateCredentialPojoArgs<Map<String, Object>> createCredentialPojoArgs = new CreateCredentialPojoArgs<Map<String, Object>>();
        createCredentialPojoArgs.setCptId(cptId);
        createCredentialPojoArgs.setIssuer(weId);
        createCredentialPojoArgs.setExpirationDate(timeStamp);

        WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
        weIdAuthentication.setWeId(weId);

        WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
        weIdPrivateKey.setPrivateKey(privateKey);
        weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);

        weIdAuthentication.setWeIdPublicKeyId(weIdPublicKeyId);
        createCredentialPojoArgs.setWeIdAuthentication(weIdAuthentication);

        createCredentialPojoArgs.setClaim(claimMap);

        //创建CredentialPojo
        ResponseData<CredentialPojo> response = credentialPojoService.createCredential(createCredentialPojoArgs);

        List<CredentialPojo> credentialList = new ArrayList<CredentialPojo>();
        credentialList.add(response.getResult());

        //创建Challenge
        Challenge challenge = Challenge.create(weId, String.valueOf(System.currentTimeMillis()));

        //创建PresentationPolicyE
        //String policyJson = "{\"extra\" : {\"extra1\" : \"\",\"extra2\" : \"\"},\"id\" : 123456,\"version\" : 1,\"orgId\" : \"webank\",\"weId\" : \"did:weid:0x0231765e19955fc65133ec8591d73e9136306cd0\",\"policy\" : {\"1017\" : {\"fieldsToBeDisclosed\" : {\"gender\" : 0,\"name\" : 1,\"age\" : 0}}}}";
        PresentationPolicyE presentationPolicyE = PresentationPolicyE.fromJson(policyJson);

        //创建Presentation
        ResponseData<PresentationE>  presentationE = credentialPojoService.createPresentation(credentialList, presentationPolicyE, challenge, weIdAuthentication);

        return presentationE;
    }
}
