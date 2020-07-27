package com.webank.autotest.fisco;

import com.webank.autotest.weid.TestAmopService;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * Created by v_wbyangwang on 2019/8/23.
 */
public class FiscoService {

    private static final Logger logger = LoggerFactory.getLogger(FiscoService.class);

    public String createAcct() {
        //创建普通外部账户
        EncryptType.encryptType = 0;
        //创建国密外部账户，向国密区块链节点发送交易需要使用国密外部账户
        // EncryptType.encryptType = 1;
        Credentials credentials = GenCredential.create();
        //账户地址
        String address = credentials.getAddress();
        //账户私钥
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        //账户公钥
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);


        return String.format("{\"accountAddress\":\"%s\",\"privateKey\":\"%s\",\"publicKey\":\"%s\"}", address, privateKey, publicKey);
    }

    public String createKey() {
        ECKeyPair keyPair = null;

        try {
            keyPair = Keys.createEcKeyPair();
        } catch (Exception e) {
            logger.error("Create weId failed.", e);
            return "ERROR";
        }

        String publicKey = String.valueOf(keyPair.getPublicKey());
        String privateKey = String.valueOf(keyPair.getPrivateKey());

        return String.format("{\"privateKey\":\"%s\",\"publicKey\":\"%s\"}", privateKey, publicKey);
    }

    public String createPublicKeyByPrivateKey(String privateKey) {
        String publicKey = "";

        BigInteger bigInteger = new BigInteger(privateKey);
        publicKey = String.valueOf(ECKeyPair.create(bigInteger).getPublicKey());

        return publicKey;
    }
}
