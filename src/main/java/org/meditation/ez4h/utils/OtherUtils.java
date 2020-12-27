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
}
