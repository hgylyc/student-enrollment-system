package com.kaifa.project.studentenrollmentsysytem.service;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
@Service
public class VerificationService {
    public Map<String, String> verificationCodes = new HashMap<>();
    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }
    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }
    public void removeVerificationCode(String email, String code){
        verificationCodes.remove(email, code);
    }
}