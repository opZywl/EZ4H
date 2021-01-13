package me.liuli.ez4h.bedrock.auth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.utils.FileUtils;
import me.liuli.ez4h.utils.OtherUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/*
 * This note refers to all auth/JWT related classes, I know there are JWT parsing dependencies(already built into the protocol
 * dependence) but I want to to be "up front", meaning I/we see everything that is going on, meaning we can see exactly how
 * the header, payload and signature are formed, rather then using some library that make take a couple minutes to understand(how
 * it works), I also do know, at some points it would be easier to use the JWT dependence but my stance above still applies,
 * maybe ill make my own simple class :shrug:
 */
//based off https://github.com/Sandertv/gophertunnel/tree/master/minecraft/auth
public class Xbox {
	private String accessToken;

	private String xboxUserAuthURL = "https://user.auth.xboxlive.com/user/authenticate";
	private String xboxAuthorizeURL = "https://xsts.auth.xboxlive.com/xsts/authorize";
	private String xboxDeviceAuthURL = "https://device.auth.xboxlive.com/device/authenticate";
	private String xboxTitleAuthURL = "https://title.auth.xboxlive.com/title/authenticate";
	private String minecraftAuthURL = "https://multiplayer.minecraft.net/authentication";

	public Xbox(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserToken(ECPublicKey publicKey, ECPrivateKey privateKey) throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RelyingParty", "http://auth.xboxlive.com");
		jsonObject.put("TokenType", "JWT");

		JSONObject properties = new JSONObject();
		jsonObject.put("Properties", properties);
		properties.put("AuthMethod", "RPS");
		properties.put("SiteName", "user.auth.xboxlive.com");
		properties.put("RpsTicket", "t=" + this.accessToken);

		JSONObject proofKey = new JSONObject();
		properties.put("ProofKey", proofKey);
		proofKey.put("crv", "P-256");
		proofKey.put("alg", "ES256");
		proofKey.put("use", "sig");
		proofKey.put("kty", "EC");
		proofKey.put("x", this.getProofKeyX(publicKey));
		proofKey.put("y", this.getProofKeyY(publicKey));

		URL url = new URL(this.xboxUserAuthURL);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("x-xbl-contract-version", "1");
		this.addSignatureHeader(connection, jsonObject, privateKey);

		this.writeJsonObjectToPost(connection, jsonObject);

		String responce = FileUtils.readIS(connection.getInputStream());
		JSONObject responceJsonObject = JSONObject.parseObject(responce);

		return responceJsonObject.getString("Token");
	}

	public String getDeviceToken(ECPublicKey publicKey, ECPrivateKey privateKey) throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RelyingParty", "http://auth.xboxlive.com");
		jsonObject.put("TokenType", "JWT");

		JSONObject properties = new JSONObject();
		jsonObject.put("Properties", properties);
		properties.put("AuthMethod", "ProofOfPossession");
		properties.put("DeviceType", "Nintendo");
		properties.put("Id", UUID.randomUUID().toString());
		properties.put("SerialNumber", UUID.randomUUID().toString());
		properties.put("Version", "0.0.0.0");

		JSONObject proofKey = new JSONObject();
		properties.put("ProofKey", proofKey);
		proofKey.put("crv", "P-256");
		proofKey.put("alg", "ES256");
		proofKey.put("use", "sig");
		proofKey.put("kty", "EC");
		proofKey.put("x", this.getProofKeyX(publicKey));
		proofKey.put("y", this.getProofKeyY(publicKey));

		URL url = new URL(this.xboxDeviceAuthURL);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("x-xbl-contract-version", "1");
		this.addSignatureHeader(connection, jsonObject, privateKey);

		this.writeJsonObjectToPost(connection, jsonObject);

		String responce = FileUtils.readIS(connection.getInputStream());
		JSONObject responceJsonObject = JSONObject.parseObject(responce);

		return responceJsonObject.getString("Token");
	}

	public String getTitleToken(ECPublicKey publicKey, ECPrivateKey privateKey, String deviceToken) throws Exception {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RelyingParty", "http://auth.xboxlive.com");
		jsonObject.put("TokenType", "JWT");

		JSONObject properties = new JSONObject();
		jsonObject.put("Properties", properties);
		properties.put("AuthMethod", "RPS");
		properties.put("DeviceToken", deviceToken);
		properties.put("SiteName", "user.auth.xboxlive.com");
		properties.put("RpsTicket", "t=" + this.accessToken);

		JSONObject proofKey = new JSONObject();
		properties.put("ProofKey", proofKey);
		proofKey.put("crv", "P-256");
		proofKey.put("alg", "ES256");
		proofKey.put("use", "sig");
		proofKey.put("kty", "EC");
		proofKey.put("x", this.getProofKeyX(publicKey));
		proofKey.put("y", this.getProofKeyY(publicKey));

		URL url = new URL(this.xboxTitleAuthURL);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("x-xbl-contract-version", "1");
		this.addSignatureHeader(connection, jsonObject, privateKey);

		this.writeJsonObjectToPost(connection, jsonObject);

		String responce = FileUtils.readIS(connection.getInputStream());
		JSONObject responceJsonObject = JSONObject.parseObject(responce);

		return responceJsonObject.getString("Token");
	}

	public String getXstsToken(String userToken, String deviceToken, String titleToken, ECPublicKey publicKey, ECPrivateKey privateKey) throws Exception {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("RelyingParty", "https://multiplayer.minecraft.net/");
		jsonObject.put("TokenType", "JWT");

		JSONObject properties = new JSONObject();
		jsonObject.put("Properties", properties);

		JSONArray userTokens = new JSONArray();
		userTokens.add(userToken);

		properties.put("DeviceToken", deviceToken);
		properties.put("TitleToken", titleToken);
		properties.put("UserTokens", userTokens);
		properties.put("SandboxId", "RETAIL");

		JSONObject proofKey = new JSONObject();
		properties.put("ProofKey", proofKey);
		proofKey.put("crv", "P-256");
		proofKey.put("alg", "ES256");
		proofKey.put("use", "sig");
		proofKey.put("kty", "EC");
		proofKey.put("x", this.getProofKeyX(publicKey));
		proofKey.put("y", this.getProofKeyY(publicKey));

		URL url = new URL(this.xboxAuthorizeURL);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("x-xbl-contract-version", "1");
		this.addSignatureHeader(connection, jsonObject, privateKey);

		this.writeJsonObjectToPost(connection, jsonObject);

		return FileUtils.readIS(connection.getInputStream());
	}

	public String requestMinecraftChain(String xsts, ECPublicKey publicKey) throws Exception {
		JSONObject xstsObject = JSONObject.parseObject(xsts);

		String pubKeyData = Base64.getEncoder().encodeToString(publicKey.getEncoded());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("identityPublicKey", pubKeyData);

		URL url = new URL(this.minecraftAuthURL);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Authorization", "XBL3.0 x=" + xstsObject.getJSONObject("DisplayClaims").getJSONArray("xui").getJSONObject(0).getString("uhs") + ";" + xstsObject.getString("Token"));
		connection.setRequestProperty("User-Agent", "MCPE/UWP");
		connection.setRequestProperty("Client-Version", EZ4H.getCommonManager().getBedrockCodec().getMinecraftVersion());

		this.writeJsonObjectToPost(connection, jsonObject);

		return FileUtils.readIS(connection.getInputStream());
	}

	private void writeJsonObjectToPost(HttpsURLConnection connection, JSONObject jsonObject) throws Exception {
		connection.setDoOutput(true);

		DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
		dataOutputStream.writeBytes(jsonObject.toJSONString());
		dataOutputStream.flush();
	}

	private String getProofKeyX(ECPublicKey ecPublicKey) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(Xbox.bigIntegerToByteArray(ecPublicKey.getW().getAffineX()));
	}

	private String getProofKeyY(ECPublicKey ecPublicKey) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(Xbox.bigIntegerToByteArray(ecPublicKey.getW().getAffineY()));
	}

	private void addSignatureHeader(HttpsURLConnection httpsURLConnection, JSONObject postData, ECPrivateKey privateKey) throws Exception {
		long currentTime = this.windowsTimestamp();
		ByteArrayOutputStream bytesToSign = new ByteArrayOutputStream();

		bytesToSign.write(new byte[] { 0, 0, 0, 1, 0 });
		bytesToSign.write(OtherUtils.toByteArray(currentTime));
		bytesToSign.write(new byte[] { 0 });

		bytesToSign.write("POST".getBytes());
		bytesToSign.write(new byte[] { 0 });
		String query = httpsURLConnection.getURL().getQuery();
		if (query == null) {
			query = "";
		}
		bytesToSign.write((httpsURLConnection.getURL().getPath() + query).getBytes());
		bytesToSign.write(new byte[] { 0 });
		String authorization = httpsURLConnection.getRequestProperty("Authorization");
		if (authorization == null) {
			authorization = "";
		}
		bytesToSign.write(authorization.getBytes());
		bytesToSign.write(new byte[] { 0 });
		bytesToSign.write(postData.toJSONString().getBytes());
		bytesToSign.write(new byte[] { 0 });

		Signature signature = Signature.getInstance("SHA256withECDSA");
		signature.initSign(privateKey);
		signature.update(bytesToSign.toByteArray());
		byte[] signatureBytes = JoseStuff.DERToJOSE(signature.sign(), JoseStuff.AlgorithmType.ECDSA256);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(new byte[] { 0, 0, 0, 1 });
		byteArrayOutputStream.write(OtherUtils.toByteArray(currentTime));
		byteArrayOutputStream.write(signatureBytes);
		httpsURLConnection.addRequestProperty("Signature", Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
	}

	// windowsTimestamp returns a Windows specific timestamp. It has a certain offset from Unix time which must be accounted for.
	private long windowsTimestamp() {
		return (Instant.now().getEpochSecond() + 11644473600L) * 10000000L;
	}

	//so sometimes getAffineX/Y toByteArray returns 33 or 31(really rare) bytes we are suppose to get 32 bytes, as said in these stackoverflows, they basically say if byte 0 is 0(33 bytes?) we can remove it
	//https://stackoverflow.com/questions/57379134/bouncy-castle-ecc-key-pair-generation-produces-different-sizes-for-the-coordinat
	//https://stackoverflow.com/questions/4407779/biginteger-to-byte
	private static byte[] bigIntegerToByteArray(BigInteger bigInteger) {
		byte[] array = bigInteger.toByteArray();
		if (array[0] == 0) {
			byte[] newArray = new byte[array.length - 1];
			System.arraycopy(array, 1, newArray, 0, newArray.length);
			return newArray;
		}
		return array;
	}

}
