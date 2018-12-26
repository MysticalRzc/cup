package mystical.cup.utils.akskUtil;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lishaoyan on 2015/12/11.
 *
 * This class is to generate or verify the weshare token
 */
public class WeshareTokenHelper{
    private static final Logger logger = LoggerFactory.getLogger(WeshareTokenHelper.class);

    private String accessKey;
    private String secretKey;

    private static final String TOKEN_VERSION = "v2";

    private static final String UTF8_ENC = "UTF-8";

    private List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD");

    public WeshareTokenHelper() {

    }

    public WeshareTokenHelper(String ak, String sk) {
        accessKey = ak;
        secretKey = sk;
    }

    /**
     *  generate the token according the request or response contents
     * @param urlPath  the url of request
     * @param method request method, must be one of 'GET', 'POST', 'DELETE', 'HEAD', 'PUT'
     * @param queryParam  the query string of request
     * @param body  the post body for request, or response body
     * @param expireTime the token expired time
     * @return  the token
     */
    public String generateToken(String urlPath, String method, String queryParam, String body, int expireTime) {
        if (StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(secretKey)) {
            logger.error("invalid AK or SK");
            throw new RuntimeException("Invalid AK or SK");
        }

        if (StringUtils.isEmpty(urlPath)) {
            logger.error("urlPath can't be empty!");
            throw new RuntimeException("Empty url path");
        }

        if (!allowedMethods.contains(method)) {
            logger.error("{} isn't an allowed method", method);
            throw new RuntimeException("invalid request method");
        }

        String token = "";
        try {
            // |v2-{AK}-{ExpireTime}|{SK}|
            StringBuffer sbSign = new StringBuffer(String.format("|%s-%s-%d|%s|", TOKEN_VERSION,
                    accessKey, expireTime, secretKey));

            // {UrlPath}|
            sbSign.append(UriUtils.decode(urlPath, UTF8_ENC)).append("|");

            // {Method}|
            sbSign.append(method).append("|");

            // {QueryParam}|
            if (!StringUtils.isEmpty(queryParam)) {
                List<String> qsArray = new ArrayList<String>();
                for (String kv: queryParam.split("&")) {
                    String[] t = kv.split("=");
                    if (t.length > 1) {
                        qsArray.add(String.format("%s=%s", UriUtils.decode(t[0], UTF8_ENC), UriUtils.decode(t[1], UTF8_ENC)));
                    } else {
                        qsArray.add(String.format("%s=", UriUtils.decode(t[0], UTF8_ENC)));
                    }
                }

                Collections.sort(qsArray);
                boolean first = true;
                for (String s: qsArray) {
                    if (first) {
                        first = false;
                    } else {
                        sbSign.append("&");
                    }
                    sbSign.append(s);
                }
            }
            sbSign.append("|");

            // {body}|
            if (!StringUtils.isEmpty(body)) {
                sbSign.append(body);
            }
            sbSign.append("|");


            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(sbSign.toString().getBytes("UTF-8"));

            //  v2-{AK}-{ExpireTime}-{Signature}
            token = String.format("%s-%s-%s-%s", TOKEN_VERSION, accessKey, expireTime,
                    new String(Hex.encodeHex(md5.digest())));
            logger.debug("signature contents = {} , tokent = {}", sbSign.toString(),token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("failed to decode url or query path");
            throw new RuntimeException("Bad encoded url path or query string");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * verify the token
     * @param token  the token for verification
     * @param urlPath  the url of request
     * @param method request method, must be one of 'GET', 'POST', 'DELETE', 'HEAD', 'PUT'
     * @param queryParam  the query string of request
     * @param body  the post body for request, or response body
     */
    public boolean verifyToken(String token,String urlPath, String method, String queryParam, String body) {
        if (StringUtils.isEmpty(token)) {
            logger.warn("null token");
            return false;
        }

        try {
            String[] tokenParts = token.split("-");

            if (tokenParts.length != 4) {
                logger.warn("invalid token format");
                return false;
            }

            if (!TOKEN_VERSION.equals(tokenParts[0])) {
                logger.warn("invalid token protocol version");
                return false;
            }

            int expireTime = Integer.parseInt(tokenParts[2]);
            if (expireTime < System.currentTimeMillis() / 1000) {
                logger.warn("expired token");
                return false;
            }

            if (token.equals(generateToken(urlPath, method, queryParam, body, expireTime))) {
                return true;
            }
        } catch (Exception e) {
            logger.error("failed to parse token '{}'", token);
            e.printStackTrace();
        }

        return false;
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
