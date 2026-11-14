package com.electromatic.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service public class SessionTokenService {
    private final byte[] secret;
    public SessionTokenService(@Value("${app.session-secret:}") String configured){if(configured!=null&&!configured.isBlank())secret=configured.getBytes(StandardCharsets.UTF_8);else{secret=new byte[32];new SecureRandom().nextBytes(secret);}}
    public String issue(Long sessionId,LocalDateTime expires){String payload=sessionId+"."+expires.toEpochSecond(ZoneOffset.UTC);return Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8))+"."+sign(payload);}
    public Long verify(String token){try{String[] p=token.split("\\.",2);String payload=new String(Base64.getUrlDecoder().decode(p[0]),StandardCharsets.UTF_8);String[] d=payload.split("\\.");if(d.length!=2||!MessageDigest.isEqual(sign(payload).getBytes(StandardCharsets.UTF_8),p[1].getBytes(StandardCharsets.UTF_8))||Long.parseLong(d[1])<=LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))return null;return Long.valueOf(d[0]);}catch(Exception e){return null;}}
    private String sign(String value){try{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(secret,"HmacSHA256"));return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));}catch(Exception e){throw new IllegalStateException(e);}}
}
