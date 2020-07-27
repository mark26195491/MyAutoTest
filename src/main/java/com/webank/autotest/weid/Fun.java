package com.webank.autotest.weid;

import com.webank.autotest.fisco.FiscoService;
import com.webank.util.FileUtils;
import com.alibaba.fastjson.JSONObject;
import com.webank.weid.constant.AmopMsgType;
import com.webank.weid.protocol.base.Version;

import java.util.Map;
import com.webank.weid.util.DateUtils;

/**
 * Created by v_wbyangwang on 2019/8/16.
 */
public class Fun {
    public static void main(String[] args) throws Exception {
        String a = "aa||cc";
        String b = "aaaa";
        String[] strArr = a.split("\\|");
        String[] bArr = b.split(";");
        String test = "{aaa{bbb}ccc}";

        if(strArr[1].isEmpty()) {
            System.out.println("22222222222222");
        }
        System.out.println(strArr.length);
        System.out.printf("%s %s %s\n", strArr[0], "c", "dsafdasdf");
        System.out.println("999999999999" + bArr[0]);
        System.out.println("000000" + AmopMsgType.valueOf("GET_ENCRYPT_KEY").getValue());

        //WeIdService weIdService = new WeIdServiceImpl();
        //WeIdService.class.getMethod("createWeId",null).;
        String json = "{\"name\" : \"zhang san\" , \"gender\" : \"F\" , \"age\" : 18}";
        //Map<String, Object> map = JSONObject.parseObject(json).getInnerMap();
        Map<String, Object> map = null;
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        Version version = new Version();
        System.out.println(version.getVersion() + "++++++++++++++");
        System.out.println(String.format("\"name\" : \"%s\" , gender : %s", "wangyang", "F"));
        FiscoService fiscoService = new FiscoService();
        String rst = fiscoService.createKey();
        String publicKey = fiscoService.createPublicKeyByPrivateKey("31794045104062819111748095643385337234421920321327503971703139087457017218299");
        System.out.println(rst);
        System.out.println("publicKey:" + publicKey);
        System.out.println("********" + FileUtils.readFile("ecdsa_key"));
        Long timeStamp = DateUtils.converDateToTimeStamp("2019-08-26 15:57:00");
        System.out.println(String.valueOf(timeStamp));
        StringBuilder stringBuilder2=new StringBuilder(test);
        System.out.println("indexof:" + stringBuilder2.insert(stringBuilder2.lastIndexOf("}"),"\"aaa\":\"bbb\""));
        String[] arr = {"aaa", "did:weid:1:0xd2c693baaa6db272488df470dc17445f756133a9", "81307008019549103579134423063891509459482356474297222850489909732778077697940", "1000", "2019-10-01 00:00:00", "{\"name\":\"wangyang\",\"gender\":\"M\",\"age\":30}"};
        //WeIdCommon.createCredential(arr);
        String aaa = "{  \"claim\": {    \"age\": 30,    \"gender\": \"M\",    \"name\": \"wangyang\"  },  \"context\": \"https://github.com/WeBankFinTech/WeIdentity/blob/master/context/v1\",  \"cptId\": 1000,  \"expirationDate\": 1569859200000,  \"id\": \"4c3ae1c5-3f6f-48fc-b887-5e728f0d9aaf\",  \"issuanceDate\": 1567506546091,  \"issuer\": \"did:weid:1:0xd2c693baaa6db272488df470dc17445f756133a9\",  \"proof\": {    \"created\": \"1567506546091\",    \"creator\": \"did:weid:1:0xd2c693baaa6db272488df470dc17445f756133a9\",    \"signature\": \"G5lXv2dVxa7qKYv1wlSA5QsEM8MWNjjhZLjvdKWWR6PWGvkLc2uxtx17KZizMIdVQ+YmCrAIDxLFaQYcJvxBD/0=\",    \"type\": \"EcdsaSignature\"  }}";

    }
}
