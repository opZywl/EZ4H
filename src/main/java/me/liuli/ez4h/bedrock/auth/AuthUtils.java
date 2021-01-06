package me.liuli.ez4h.bedrock.auth;

import com.alibaba.fastjson.JSONArray;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class AuthUtils {
    private static KeyPairGenerator KEY_PAIR_GEN;
    public static void load(){
        try {
            KEY_PAIR_GEN = KeyPairGenerator.getInstance("EC");
            KEY_PAIR_GEN.initialize(new ECGenParameterSpec("secp256r1"));//use P-256
        } catch (Exception e) {
            throw new AssertionError("Unable to initialize required encryption", e);
        }
    }
    public static KeyPair createKeyPair() {
        return KEY_PAIR_GEN.generateKeyPair();
    }
    public static JSONArray addChainToBeginning(String chain, JSONArray chainArray) {
        JSONArray newArray = new JSONArray();
        newArray.add(chain);
        newArray.addAll(chainArray);
        return newArray;
    }
    public static String signBytes(byte[] dataToSign,ECPrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA384withECDSA");
        signature.initSign(privateKey);
        signature.update(dataToSign);
        byte[] signatureBytes = JoseStuff.DERToJOSE(signature.sign(), JoseStuff.AlgorithmType.ECDSA384);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
    }
}
