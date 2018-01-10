package com.meiliangzi.app.tools;

import android.app.Dialog;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RuleCheckUtils {
    private static final String MATCH_QQ_REGEX = "^\\d{5,10}$";
    private static final String MATCH_NUMBER_REGEX = "\\d+";
    private static final String SMS_SIX_CODER = "\\d{6}";
    private static final String MATCH_PHONE_REGEX = "((13)|(14)|(15)|(17)|(18))\\d{9}$";
    private static final String MATCH_EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final String MATCH_REG_REGEX = "^([a-z]|[A-Z]|[0-9]|[\u2E80-\u9FFF]){3,}|@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?|[wap.]{4}|[www.]{4}|[blog.]{5}|[bbs.]{4}|[.com]{4}|[.cn]{3}|[.net]{4}|[.org]{4}|[http://]{7}|[ftp://]{6}$";
    //	private static final String MATCH_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
    private static final String MATCH_ID_CARD_ONE = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";//15位数身份证验证正则表达式：
    private static final String MATCH_ID_CARD_TWO = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";//18位数身份证验证正则表达式：
    private static final String MATCH_ID_IP = "\\b(([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\b";//ip的正则匹配
    private static final String MATCH_ID_WW_REGEX = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";//域名的正则匹配

    private static final String MATCH_PWD = "^(.*[a-z0-9A-Z].*){6,20}$";

    private RuleCheckUtils() {
    }

    public static void checkIdCardRegex(String idCard) {
        if (idCard.length() < 15) {
            throw new IllegalArgumentException("请输入正确的身份证号码");

        } else if (idCard.length() == 15) {
            if (!Pattern.matches(MATCH_ID_CARD_ONE, idCard)) {
                throw new IllegalArgumentException("请输入正确的身份证号码");
            }
        } else if (idCard.length() > 15 && idCard.length() < 18) {
            throw new IllegalArgumentException("请输入正确的身份证号码");

        } else if (idCard.length() == 18) {
            if (!Pattern.matches(MATCH_ID_CARD_TWO, idCard)) {
                throw new IllegalArgumentException("请输入正确的身份证号码");
            }
        }

    }

    public static void checkPhoneRegex(String phonNum) {
        if (!Pattern.matches(MATCH_PHONE_REGEX, phonNum)) {
            throw new IllegalArgumentException("请输入正确的手机号码");
        }
    }
    public static void checkIPRegex(String ip,String msg) {
        if (!Pattern.matches(MATCH_ID_IP, ip)) {
            throw new IllegalArgumentException("请输入正确的"+msg+"IP");
        }
    }

    public static void checkIPNameRegex(String ip,String msg) {
        if (!Pattern.matches(MATCH_ID_WW_REGEX, ip)) {
            throw new IllegalArgumentException("请输入正确的"+msg+"域名");
        }
    }

    public static void checkRegRegex(String reg) {
        if (!Pattern.matches(MATCH_REG_REGEX, reg)) {
            throw new IllegalArgumentException("不支持表情输入");
        }
    }

    public static boolean checkPhone(String phonNum) {
        if (Pattern.matches(MATCH_PHONE_REGEX, phonNum)) {
            return true;
        }
        return false;
    }
    public static boolean checkPhones(String phonNum) {
        if (TextUtils.isEmpty(phonNum)) {
            throw new IllegalArgumentException("请输入手机号码");
        }
        if (!Pattern.matches(MATCH_PHONE_REGEX, phonNum)) {
            throw new IllegalArgumentException("请输入正确的手机号");
        }
        return false;
    }

    public static boolean checkEmail(String email) {
        if (Pattern.matches(MATCH_EMAIL_REGEX, email)) {
            return true;
        }
        return false;
    }

    public static void checkEmailRegex(String email) {
        if (!Pattern.matches(MATCH_EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("请输入正确的邮箱地址");
        }
    }

    public static boolean checkPhoneOrEmail(String loginName) {
        if (Pattern.matches(MATCH_PHONE_REGEX, loginName) || Pattern.matches(MATCH_EMAIL_REGEX, loginName)) {
            return false;
        }
        if (!Pattern.matches(MATCH_NUMBER_REGEX, loginName)) {
            return true;
        } else {
            return true;
        }
    }

    public static void checkPhoneOrEmailRegex(String loginName) {
        if (Pattern.matches(MATCH_PHONE_REGEX, loginName) || Pattern.matches(MATCH_EMAIL_REGEX, loginName)) {
            return;
        }
        if (!Pattern.matches(MATCH_NUMBER_REGEX, loginName)) {
            checkEmailRegex(loginName);
        } else {
            checkPhoneRegex(loginName);
        }
    }

    public static void checkEmpty(String content, String errMsg) {
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException(errMsg);
        }
    }

    public static void checkSMSCaptchaLength(String code) {
        if (code.length() <6) {
            throw new IllegalArgumentException("短信验证码长度为4");
        }
    }


    public static void checkPwdLength(String pass) {
        if (!Pattern.matches(MATCH_PWD, pass)) {
            throw new IllegalArgumentException("请输入6~20位数字和字母组合密码");
        }
    }

    public static void checkIsEqual(String pwd, String newpwdAgain) {
        if (!TextUtils.equals(newpwdAgain, pwd)) {
            throw new IllegalArgumentException("两次密码不一致哦");
        }
    }

    public static void checkIsEqualCode(String code, String text) {
        if (!TextUtils.equals(code, text)) {
            throw new IllegalArgumentException("验证码输入有误");
        }
    }

    public static boolean patternPhoneRegex(String phonNum) {
        return Pattern.matches(MATCH_PHONE_REGEX, phonNum);
    }

    public static String patternCode(String patternContent) {
        if (TextUtils.isEmpty(patternContent)) {
            return null;
        }
        Pattern p = Pattern.compile(SMS_SIX_CODER);
        Matcher matcher = p.matcher(patternContent);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new IllegalStateException("没有匹配4位验证码");
    }

    public static void checkNumUnValidAndMinusNum(EditText et) {
        String numString = et.getText().toString();
        if (numString.matches("\\d+")) {
            Integer numInt = Integer.parseInt(numString);
            if (numInt > 1) {
                numInt--;
                et.setText(numInt.toString());
            } else {
                throw new IllegalArgumentException("数目");
            }
        } else {
            throw new IllegalArgumentException("请输入有效数");
        }
    }

    public static void checkNumUnValidAndAddNum(EditText et) {
        String numString = et.getText().toString();
        if (numString.matches("\\d+")) {
            Integer numInt = Integer.parseInt(numString);
            if (numInt < 999) {
                numInt++;
                et.setText(numInt.toString());
            } else {
                throw new IllegalArgumentException("购买数目99");
            }
        } else {
            throw new IllegalArgumentException("请输入有效数");
        }
    }

    public static void checkNumUnValidAndSetOne(EditText buyEt, EditText popEt, Dialog dialog) {
        String numString = popEt.getText().toString();
        if (numString.matches("\\d+")) {
            Integer numInt = Integer.parseInt(numString);
            if (numInt < 1) {
                popEt.setText("1");
            } else {
                buyEt.setText(popEt.getText().toString());
                dialog.dismiss();
            }
        } else {
            throw new IllegalArgumentException("请输入有效数");
        }
    }

    public static void checkNumUnValidAndSetOne(EditText popEt, Dialog dialog) {
        String numString = popEt.getText().toString();
        if (numString.matches("\\d+")) {
            Integer numInt = Integer.parseInt(numString);
            if (numInt < 1) {
                popEt.setText("1");
            } else {
                dialog.dismiss();
            }
        } else {
            throw new IllegalArgumentException("请输入有效数");
        }
    }
}
