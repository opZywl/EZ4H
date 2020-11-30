package org.meditation.ez4h.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.util.Base64URL;

import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.util.Base64;

public class OtherUtils {

    public static boolean isNull(Object object){
        return object == null;
    }
    public static String base64Encode(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }
    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input.getBytes()));
    }
    public static String fixBase64(String base64){
        if(base64.substring(base64.length()-2).equals("==")){
            return base64.substring(0,base64.length()-2);
        }
        return base64;
    }
    public static String JWSSigner(ECPrivateKey key,String signingInput){
        try {
            ECDSASigner jwsSigner=new ECDSASigner(key, Curve.P_384);
            return sign(jwsSigner,new JWSAlgorithm("ES384"),key,signingInput.getBytes()).toString();
        }catch (Throwable t){
            t.printStackTrace();
        }
        return "";
    }
    public static Base64URL sign(ECDSASigner signer,JWSAlgorithm alg,ECPrivateKey privateKey, byte[] signingInput) throws JOSEException {
        if (signer.supportedJWSAlgorithms().contains(alg)) {
            byte[] jcaSignature;
            try {
                Signature dsa = ECDSA.getSignerAndVerifier(alg, signer.getJCAContext().getProvider());
                dsa.initSign(privateKey, signer.getJCAContext().getSecureRandom());
                dsa.update(signingInput);
                jcaSignature = dsa.sign();
            } catch (SignatureException | InvalidKeyException var7) {
                throw new JOSEException(var7.getMessage(), var7);
            }
            int rsByteArrayLength = ECDSA.getSignatureByteArrayLength(alg);
            byte[] jwsSignature = ECDSA.transcodeSignatureToConcat(jcaSignature, rsByteArrayLength);
            return Base64URL.encode(jwsSignature);
        }else{
            return null;
        }
    }
}
