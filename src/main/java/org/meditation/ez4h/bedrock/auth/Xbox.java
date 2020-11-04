package org.meditation.ez4h.bedrock.auth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.primitives.Longs;
import com.starkbank.ellipticcurve.Ecdsa;
import com.starkbank.ellipticcurve.PrivateKey;
import com.starkbank.ellipticcurve.PublicKey;
import com.starkbank.ellipticcurve.utils.File;
import org.meditation.ez4h.utils.FileUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class Xbox {

	private String accessToken;

	public String xblUserAuthURL = "https://user.auth.xboxlive.com/user/authenticate";
	public String xblAuthorizeURL = "https://xsts.auth.xboxlive.com/xsts/authorize";
	public String xblDeviceAuthURL = "https://device.auth.xboxlive.com/device/authenticate";
	public String xblTitleAuthURL = "https://title.auth.xboxlive.com/title/authenticate";
	public String minecraftAuthURL = "https://multiplayer.minecraft.net/authentication";

	public Xbox(String accessToken) {
		this.accessToken=accessToken;
	}

	public String userToken(PublicKey publicKey) {
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("RelyingParty", "http://auth.xboxlive.com");
			jsonObject.put("TokenType", "JWT");

			JSONObject properties=new JSONObject();
			properties.put("AuthMethod", "RPS");
			properties.put("SiteName", "user.auth.xboxlive.com");
			properties.put("RpsTicket", "t=" + this.accessToken);
			jsonObject.put("Properties", properties);

			URL url = new URL(this.xblUserAuthURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("x-xbl-contract-version", "1");
			connection.setDoOutput(true);

			DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.writeBytes(jsonObject.toJSONString());
			dataOutputStream.flush();

			String responce = FileUtils.readIS(connection.getInputStream());
			JSONObject responceJsonObject = JSONObject.parseObject(responce);
			return responceJsonObject.getString("Token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String deviceToken(PublicKey publicKey, PrivateKey privateKey) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RelyingParty", "http://auth.xboxlive.com");
		jsonObject.put("TokenType", "JWT");

		JSONObject properties = new JSONObject();
		properties.put("AuthMethod", "ProofOfPossession");
		properties.put("DeviceType", "Nintendo");
		properties.put("Id", UUID.randomUUID().toString());
		properties.put("SerialNumber", UUID.randomUUID().toString());
		properties.put("Version", "0.0.0.0");
		jsonObject.put("Properties", properties);

		JSONObject proofKey = new JSONObject();
		proofKey.put("crv", "P-256");
		proofKey.put("alg", "ES256");
		proofKey.put("use", "sig");
		proofKey.put("kty", "EC");
		proofKey.put("x", Base64.getUrlEncoder().withoutPadding().encodeToString(Xbox.bigIntegerToByteArray(publicKey.point.x)));
		proofKey.put("y", Base64.getUrlEncoder().withoutPadding().encodeToString(Xbox.bigIntegerToByteArray(publicKey.point.y)));
		properties.put("ProofKey", proofKey);
		try {

			URL url = new URL(this.xblDeviceAuthURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("x-xbl-contract-version", "1");
			this.sign(connection, jsonObject.toJSONString(), privateKey);
			connection.setDoOutput(true);

			DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.writeBytes(jsonObject.toJSONString());
			dataOutputStream.flush();

			String responce = FileUtils.readIS(connection.getInputStream());
			JSONObject responceJsonObject = JSONObject.parseObject(responce);

			return responceJsonObject.getString("Token");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String titleToken(PublicKey publicKey, PrivateKey privateKey, String deviceToken) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RelyingParty", "http://auth.xboxlive.com");
		jsonObject.put("TokenType", "JWT");

		JSONObject properties = new JSONObject();
		properties.put("AuthMethod", "RPS");
		properties.put("DeviceToken", deviceToken);
		properties.put("SiteName", "user.auth.xboxlive.com");
		properties.put("RpsTicket", "t=" + this.accessToken);
		jsonObject.put("Properties", properties);

		JSONObject proofKey = new JSONObject();
		proofKey.put("crv", "P-256");
		proofKey.put("alg", "ES256");
		proofKey.put("use", "sig");
		proofKey.put("kty", "EC");
		proofKey.put("x", Base64.getUrlEncoder().withoutPadding().encodeToString(Xbox.bigIntegerToByteArray(publicKey.point.x)));
		proofKey.put("y", Base64.getUrlEncoder().withoutPadding().encodeToString(Xbox.bigIntegerToByteArray(publicKey.point.y)));
		properties.put("ProofKey", proofKey);

		try {

			URL url = new URL(this.xblTitleAuthURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("x-xbl-contract-version", "1");
			this.sign(connection, jsonObject.toJSONString(), privateKey);
			connection.setDoOutput(true);

			DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.writeBytes(jsonObject.toJSONString());
			dataOutputStream.flush();

			String responce = FileUtils.readIS(connection.getInputStream());
			JSONObject responceJsonObject = JSONObject.parseObject(responce);

			return responceJsonObject.getString("Token");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public JSONObject xstsToken(String userToken, String deviceToken, String titleToken) {

		try {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("RelyingParty", "https://multiplayer.minecraft.net/");
			jsonObject.put("TokenType", "JWT");

			JSONObject properties = new JSONObject();
			jsonObject.put("properties", properties);

			JSONArray userTokens = new JSONArray();
			userTokens.add(userToken);

			properties.put("DeviceToken", deviceToken);
			properties.put("TitleToken", titleToken);
			properties.put("UserTokens", userTokens.toJSONString());
			properties.put("SandboxId", "RETAIL");

			URL url = new URL(this.xblAuthorizeURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("x-xbl-contract-version", "1");
			connection.setDoOutput(true);

			DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.writeBytes(jsonObject.toJSONString());
			dataOutputStream.flush();

			String responce = FileUtils.readIS(connection.getInputStream());
			return JSONObject.parseObject(responce);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String RequestMinecraftChain(JSONObject xsts, PublicKey publicKey) {
		try {
			String pubKeyData = Base64.getEncoder().encodeToString(publicKey.toDer().getBytes());

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("identityPublicKey", pubKeyData);

			URL url = new URL(this.minecraftAuthURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "XBL3.0 x=" + xsts.getJSONObject("DisplayClaims").getJSONArray("xui").getJSONObject(0).getString("uhs") + ";" + xsts.getString("Token"));
			connection.setRequestProperty("User-Agent", "MCPE/UWP");
			connection.setRequestProperty("Client-Version", /*Client.instance.bedrockClient.getSession().getPacketCodec().getMinecraftVersion()*/ "1.16.20" + "");
			connection.setDoOutput(true);

			DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
			dataOutputStream.writeBytes(jsonObject.toJSONString());
			dataOutputStream.flush();

			String responce = FileUtils.readIS(connection.getInputStream());
			return responce;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sign(HttpsURLConnection httpsURLConnection, String body, PrivateKey privateKey) throws Exception {
		long currentTime = this.windowsTimestamp();
		ByteArrayOutputStream hash = new ByteArrayOutputStream();

		hash.write(new byte[] { 0, 0, 0, 1, 0 });
		hash.write(Longs.toByteArray(currentTime));
		hash.write(new byte[] { 0 });

		hash.write("POST".getBytes());
		hash.write(new byte[] { 0 });
		String query = httpsURLConnection.getURL().getQuery();
		if (query == null) {
			query = "";
		}
		hash.write((httpsURLConnection.getURL().getPath() + query).getBytes());
		hash.write(new byte[] { 0 });
		String authorization = httpsURLConnection.getRequestProperty("Authorization");
		if (authorization == null) {
			authorization = "";
		}
		hash.write(authorization.getBytes());
		hash.write(new byte[] { 0 });
		hash.write(body.getBytes("UTF-8"));//TODO: i dont think utf-8 is needed
		hash.write(new byte[] { 0 });

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = digest.digest(hash.toByteArray());
		//Signature signature = Signature.getInstance("SHA512withECDSA");
		//signature.initSign(privateKey);
		//signature.update(hashBytes);
		//byte[] signatureBytes = signature.sign();
		com.starkbank.ellipticcurve.Signature signature = Ecdsa.signTunnelMc(hashBytes, privateKey);
		byte[] signatureBytes = null;
		ByteArrayOutputStream kek = new ByteArrayOutputStream();
		kek.write(bigIntegerToByteArray(signature.r));
		kek.write(bigIntegerToByteArray(signature.s));
		signatureBytes = kek.toByteArray();

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		buf.write(new byte[] { 0, 0, 0, 1 });
		buf.write(Longs.toByteArray(currentTime));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(buf.toByteArray());
		byteArrayOutputStream.write(signatureBytes);
		httpsURLConnection.addRequestProperty("Signature", Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
	}

	// windowsTimestamp returns a Windows specific timestamp. It has a certain offset from Unix time which must be
	// accounted for.
	public long windowsTimestamp() {
		return (Instant.now().getEpochSecond() + 11644473600L) * 10000000L;
	}

	//so sometimes getAffineX/Y toByteArray returns 33 or 31(really rare) bytes we are suppose to get 32 bytes, as said in these stackoverflows, they basically say if byte 0 is 0(33 bytes?) we can remove it
	//https://stackoverflow.com/questions/57379134/bouncy-castle-ecc-key-pair-generation-produces-different-sizes-for-the-coordinat
	//https://stackoverflow.com/questions/4407779/biginteger-to-byte
	public static byte[] bigIntegerToByteArray(BigInteger bigInteger) {
		byte[] array = bigInteger.toByteArray();
		if (array[0] == 0) {
			byte[] newArray = new byte[array.length - 1];
			System.arraycopy(array, 1, newArray, 0, newArray.length);
			return newArray;
		}
		return array;
	}

	//https://stackoverflow.com/questions/49974441/extracting-r-s-and-verifying-ecdsa-signature-remotely
	public static BigInteger extractR(byte[] signature) throws Exception {
		int startR = (signature[1] & 0x80) != 0 ? 3 : 2;
		int lengthR = signature[startR + 1];
		return new BigInteger(Arrays.copyOfRange(signature, startR + 2, startR + 2 + lengthR));
	}

	public static BigInteger extractS(byte[] signature) throws Exception {
		int startR = (signature[1] & 0x80) != 0 ? 3 : 2;
		int lengthR = signature[startR + 1];
		int startS = startR + 2 + lengthR;
		int lengthS = signature[startS + 1];
		return new BigInteger(Arrays.copyOfRange(signature, startS + 2, startS + 2 + lengthS));
	}

}
