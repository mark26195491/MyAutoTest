package com.webank.autotest.weid;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.*;
import com.webank.weid.protocol.request.CreateCredentialPojoArgs;
import com.webank.weid.protocol.response.ResponseData;
import com.webank.weid.rpc.CredentialPojoService;
import com.webank.weid.service.impl.CredentialPojoServiceImpl;
import com.webank.weid.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by v_wbyangwang on 2019/8/21.
 */
public class TestCredentialPojoService {

	private static final Logger logger = LoggerFactory.getLogger(TestCredentialPojoService.class);

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * //logger.info("args = {}", Arrays.toString(args));
	 * logger.info("Show the args : {}", Arrays.toString(args));
	 * //System.out.printf("Show the args : %s\n", Arrays.toString(args));
	 * 
	 * //String[] command = args[0].split("\\|"); String[] command = args;
	 * //did:weid:1:0xddf4f93f098d0341a2bd0961bff0d6d6c97538a0
	 * logger.info("Begin to execute method : {}",command[0]);
	 * //System.out.printf("Begin to execute method : %s\n", command[0]);
	 * 
	 * try { switch (command[0]) { case "createCredential":
	 * testCreateCredential(command); break; case "createSelectiveCredential":
	 * testCreateSelectiveCredential(command); break; case "verify_1":
	 * testVerify_1(command);// use weId break; case "verify_2":
	 * testVerify_2(command);// use publicKey break; case "verify_3":
	 * testVerify_3(command);//verify presentation break; case "createPresentation":
	 * testCreatePresentation(command); break; case "getCredentialPojoHash":
	 * testGetCredentialPojoHash(command); break; default:
	 * System.out.println("Not found the method, please check."); break; } } catch
	 * (Exception e) {
	 * //logger.error("execute {} failed.{}",Arrays.toString(command),
	 * e.getMessage()); System.out.println("Execute failed, please check the log.");
	 * System.exit(1); }
	 * 
	 * System.exit(0); }
	 * 
	 * private static void testCreateCredential(String[] command) throws Exception {
	 * String weId = command[1]; String privateKey = command[2]; int cptId =
	 * Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String expirationDate =
	 * command[4]; String claimJsonStr = command[5]; String weIdPublicKeyId =
	 * command[6];
	 * 
	 * ResponseData<CredentialPojo> response =
	 * WeIdCommon.createCredentialPojo(command);
	 * 
	 * System.out.println(JSONObject.toJSONString(response)); }
	 * 
	 * private static void testCreateSelectiveCredential(String[] command) throws
	 * Exception { String weId = command[1]; String privateKey = command[2]; int
	 * cptId = Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String
	 * expirationDate = command[4]; String claimJsonStr = command[5]; String
	 * weIdPublicKeyId = command[6]; String discloseJsonStr = command[7];
	 * 
	 * ResponseData<CredentialPojo> response =
	 * WeIdCommon.createCredentialPojo(command); CredentialPojoService
	 * credentialPojoService = new CredentialPojoServiceImpl();
	 * 
	 * // 选择性披露 ClaimPolicy claimPolicy = new ClaimPolicy();
	 * claimPolicy.setFieldsToBeDisclosed(discloseJsonStr);
	 * ResponseData<CredentialPojo> selectiveResponse =
	 * credentialPojoService.createSelectiveCredential(response.getResult(),
	 * claimPolicy);
	 * 
	 * System.out.println(JSONObject.toJSONString(selectiveResponse)); }
	 * 
	 * private static void testVerify_1(String[] command) throws Exception { String
	 * weId = command[1]; String privateKey = command[2]; int cptId =
	 * Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String expirationDate =
	 * command[4]; String claimJsonStr = command[5]; String weIdPublicKeyId =
	 * command[6];
	 * 
	 * ResponseData<CredentialPojo> response =
	 * WeIdCommon.createCredentialPojo(command); CredentialPojoService
	 * credentialPojoService = new CredentialPojoServiceImpl();
	 * 
	 * ResponseData<Boolean> responseVerify = credentialPojoService.verify(weId,
	 * response.getResult());
	 * 
	 * System.out.println(JSONObject.toJSONString(responseVerify)); }
	 * 
	 * private static void testVerify_2(String[] command) throws Exception { String
	 * weId = command[1]; String privateKey = command[2]; int cptId =
	 * Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String expirationDate =
	 * command[4]; String claimJsonStr = command[5]; String weIdPublicKeyId =
	 * command[6]; String publicKey = command[7];
	 * 
	 * ResponseData<CredentialPojo> response =
	 * WeIdCommon.createCredentialPojo(command); CredentialPojoService
	 * credentialPojoService = new CredentialPojoServiceImpl();
	 * 
	 * WeIdPublicKey weIdPublicKey = new WeIdPublicKey();
	 * weIdPublicKey.setPublicKey(publicKey);
	 * 
	 * ResponseData<Boolean> responseVerify =
	 * credentialPojoService.verify(weIdPublicKey, response.getResult());
	 * 
	 * System.out.println(JSONObject.toJSONString(responseVerify)); }
	 * 
	 * private static void testVerify_3(String[] command) throws Exception { String
	 * weId = command[1]; String privateKey = command[2]; int cptId =
	 * Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String expirationDate =
	 * command[4]; String claimJsonStr = command[5]; String weIdPublicKeyId =
	 * command[6]; String policyJson = command[7];
	 * 
	 * Long timeStamp = DateUtils.converDateToTimeStamp(expirationDate); Map<String,
	 * Object> claimMap = JSONObject.parseObject(claimJsonStr).getInnerMap();
	 * 
	 * CredentialPojoService credentialPojoService = new
	 * CredentialPojoServiceImpl(); CreateCredentialPojoArgs<Map<String, Object>>
	 * createCredentialPojoArgs = new CreateCredentialPojoArgs<Map<String,
	 * Object>>(); createCredentialPojoArgs.setCptId(cptId);
	 * createCredentialPojoArgs.setIssuer(weId);
	 * createCredentialPojoArgs.setExpirationDate(timeStamp);
	 * 
	 * WeIdAuthentication weIdAuthentication = new WeIdAuthentication();
	 * weIdAuthentication.setWeId(weId);
	 * 
	 * WeIdPrivateKey weIdPrivateKey = new WeIdPrivateKey();
	 * weIdPrivateKey.setPrivateKey(privateKey);
	 * weIdAuthentication.setWeIdPrivateKey(weIdPrivateKey);
	 * 
	 * weIdAuthentication.setWeIdPublicKeyId(weIdPublicKeyId);
	 * createCredentialPojoArgs.setWeIdAuthentication(weIdAuthentication);
	 * 
	 * createCredentialPojoArgs.setClaim(claimMap);
	 * 
	 * //创建CredentialPojo ResponseData<CredentialPojo> response =
	 * credentialPojoService.createCredential(createCredentialPojoArgs);
	 * 
	 * List<CredentialPojo> credentialList = new ArrayList<CredentialPojo>();
	 * credentialList.add(response.getResult());
	 * 
	 * //创建Challenge Challenge challenge = Challenge.create(weId,
	 * String.valueOf(System.currentTimeMillis()));
	 * 
	 * //创建PresentationPolicyE //String policyJson =
	 * "{\"extra\" : {\"extra1\" : \"\",\"extra2\" : \"\"},\"id\" : 123456,\"version\" : 1,\"orgId\" : \"webank\",\"weId\" : \"did:weid:0x0231765e19955fc65133ec8591d73e9136306cd0\",\"policy\" : {\"1017\" : {\"fieldsToBeDisclosed\" : {\"gender\" : 0,\"name\" : 1,\"age\" : 0}}}}"
	 * ; PresentationPolicyE presentationPolicyE =
	 * PresentationPolicyE.fromJson(policyJson);
	 * 
	 * //创建Presentation ResponseData<PresentationE> presentationERes =
	 * credentialPojoService.createPresentation(credentialList, presentationPolicyE,
	 * challenge, weIdAuthentication);
	 * 
	 * //验证Presentation ResponseData<Boolean> verifyRes =
	 * credentialPojoService.verify(weId, presentationPolicyE, challenge,
	 * presentationERes.getResult());
	 * 
	 * System.out.println(JSONObject.toJSONString(verifyRes)); }
	 * 
	 * private static void testCreatePresentation(String[] command) throws Exception
	 * { String weId = command[1]; String privateKey = command[2]; int cptId =
	 * Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String expirationDate =
	 * command[4]; String claimJsonStr = command[5]; String weIdPublicKeyId =
	 * command[6]; String policyJson = command[7];
	 * 
	 * //创建Presentation ResponseData<PresentationE> presentationE =
	 * WeIdCommon.createPresentation(command);
	 * 
	 * System.out.println(JSONObject.toJSONString(presentationE)); }
	 * 
	 * private static void testGetCredentialPojoHash(String[] command) throws
	 * Exception { String weId = command[1]; String privateKey = command[2]; int
	 * cptId = Integer.valueOf(command[3]); //yyyy-mm-dd hh24:mi:ss String
	 * expirationDate = command[4]; String claimJsonStr = command[5]; String
	 * weIdPublicKeyId = command[6];
	 * 
	 * ResponseData<CredentialPojo> responsecredentialPojo =
	 * WeIdCommon.createCredentialPojo(command); CredentialPojoService
	 * credentialPojoService = new CredentialPojoServiceImpl();
	 * 
	 * ResponseData<String> response = null; //ResponseData<String> response =
	 * credentialPojoService.getCredentialPojoHash(responsecredentialPojo.getResult(
	 * ));
	 * 
	 * System.out.println(JSONObject.toJSONString(response)); }
	 */

}
