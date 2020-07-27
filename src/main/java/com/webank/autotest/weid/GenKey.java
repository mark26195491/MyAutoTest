package com.webank.autotest.weid;

import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;

import com.webank.weid.util.DataToolUtils;

public class GenKey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EncryptType.encryptType = 0;
		//国密设置成1
		// EncryptType.encryptType = 1; 
		Credentials credentials = GenCredential.create();
		//ַ
		String address = credentials.getAddress();
		//私钥 
		String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(10);
		BigInteger bigIntegerPriKey = credentials.getEcKeyPair().getPrivateKey();
		//公钥bigint
		String publicKey = credentials.getEcKeyPair().getPublicKey().toString(10);
		BigInteger bigIntegerPubkey = credentials.getEcKeyPair().getPublicKey();
		//公钥base64
        String pubkeyBase64Str= org.apache.commons.codec.binary.Base64.encodeBase64String(bigIntegerPubkey.toByteArray());
        String prikeyBase64Str= org.apache.commons.codec.binary.Base64.encodeBase64String(bigIntegerPriKey.toByteArray());
        
//        System.out.println("credential_hash:"+DataToolUtils.sha3(""+System.currentTimeMillis()));
//        System.out.println("publicKey(10):"+publicKey);
//        System.out.println("privateKey(10):"+privateKey);
//        System.out.println("pubkeyBase64Str:"+pubkeyBase64Str);
//        System.out.println("prikeyBase64Str:"+prikeyBase64Str);
//        //公钥规则：公钥必须是64字节，首位必须>16
//        //私钥规则：私钥必须是32字节，首位不能=0
//        System.out.println("pubkey(bytes):"+(int)(bigIntegerPubkey.toByteArray()[0]));
//        System.out.println("prikey(bytes):"+(int)(bigIntegerPriKey.toByteArray()[0]));
        
        System.out.println("sha3|publicKey|privateKey|pubkeyBase64Str|prikeyBase64Str|pubkeyfirstdigital|prikeyfirstdigital|");
        System.out.println(DataToolUtils.sha3(""+System.currentTimeMillis())+"|"+publicKey+"|"+privateKey+"|"+pubkeyBase64Str+"|"+prikeyBase64Str+"|"+(int)(bigIntegerPubkey.toByteArray()[0])+"|"+(int)(bigIntegerPriKey.toByteArray()[0])+"|");

	}

}
