package com.meiliangzi.app.tools;

/**
 * Created by kk on 2019/5/16.
 */

public class NewPreferManager {
    public static final String UserSex="userSex";
    public static final String OrganizationName="organizationName";
    public static final String Photo="photo";
    private static final String UserName="userName";
    private static final String BirthDate="birthDate";
    private static final String UserCode="userCode";
    private static final String PartyMasses="partyMasses";
    private static final String OrganizationId="organizationId";
    private static final String PositionName="positionName";
    private static final String PartyPositionName="partyPositionName";
    private static final String PositionId = "positionId";
    private static final String WorkNumber = "workNumber";
    private static final String Phone = "phone";
    private static final String Passwd = "passwd";
    private static final String PartyName = "partyName";
    private static final String NativePlace = "nativePlace";
    private static final String Id="CYXYid";
    private static final String UserTotalScore="userTotalScore";
    private static final String RuleLists="rulelists";
    private static final String IS_REMEMBER_PWD = "isremmemberpwd";
    private static final String LOGIN_PWD = "loginpwd";
    private static final String Token = "token";
    private static final String RefreshToken = "token_refresh";
    private static final String OrgId = "orgId";


    public static void  saveOrgId(String orgId){
        PreferUtils.put(Token,orgId);
    }

    public static String getOrgId(){
        return PreferUtils.getString(Token,"");
    }

    public static void  saverefreshToken(String token_refresh){
        PreferUtils.put(RefreshToken,token_refresh);
    }

    public static String getrefreshToken(){
        return PreferUtils.getString(RefreshToken,"");
    }
    public static void  saveToken(String token){
        PreferUtils.put(Token,token);
    }

    public static String getToken(){
        return PreferUtils.getString(Token,"");
    }




    public static void  saveLoginPwd(String loginpwd){
        PreferUtils.put(LOGIN_PWD,loginpwd);
    }

    public static String getLoginPwd(){
        return PreferUtils.getString(LOGIN_PWD,"");
    }

    public static void  saveIsRememberPwd(String isremmemberpwd){
        PreferUtils.put(IS_REMEMBER_PWD,isremmemberpwd);
    }
    public static String getIsRememberPwd(){
        return PreferUtils.getString(IS_REMEMBER_PWD,"");
    }

    public static void  saveRuleLists(String rulelists){
        PreferUtils.put(RuleLists,rulelists);
    }
    public static String getRuleLists(){
        return PreferUtils.getString(RuleLists,"");
    }






    public static void  saveUserTotalScore(String userTotalScore){
        PreferUtils.put(UserTotalScore,userTotalScore);
    }
    public static String getUserTotalScore(){
        return PreferUtils.getString(UserTotalScore,"");
    }






    public static void  saveId(String id){
        PreferUtils.put(Id,id);
    }
    public static String getId(){
        return PreferUtils.getString(Id,"");
    }


    public static void  saveNativePlace(String nativePlace){
        PreferUtils.put(NativePlace,nativePlace);
    }
    public static String getNativePlace(){
        return PreferUtils.getString(NativePlace,"");
    }




    public static void  savePartyName(String partyName){
        PreferUtils.put(PartyName,partyName);
    }
    public static String getPartyName(){
        return PreferUtils.getString(PartyName,"");
    }



    public static void  savePasswd(String passwd){
        PreferUtils.put(Passwd,passwd);
    }
    public static String getPasswd(){
        return PreferUtils.getString(Passwd,"");
    }




    public static void  savePhone(String phone){
        PreferUtils.put(Phone,phone);
    }
    public static String getPhone(){
        return PreferUtils.getString(Phone,"");
    }


    public static void  saveWorkNumber(String workNumber){
        PreferUtils.put(WorkNumber,workNumber);
    }
    public static String getWorkNumber(){
        return PreferUtils.getString(WorkNumber,"");
    }

    public static void  savePositionId(String positionId){
        PreferUtils.put(PositionId,positionId);
    }
    public static String getPositionId(){
        return PreferUtils.getString(PositionId,"");
    }


    public static void  savePartyPositionName(String partyPositionName){
        PreferUtils.put(PartyPositionName,partyPositionName);
    }
    public static String getPartyPositionName(){
        return PreferUtils.getString(PartyPositionName,"");
    }


    public static void  savePositionName(String positionName){
        PreferUtils.put(PositionName,positionName);
    }
    public static String getPositionName(){
        return PreferUtils.getString(PositionName,"");
    }

    public static void  saveOrganizationId(String organizationId){
        PreferUtils.put(OrganizationId,organizationId);
    }
    public static String getOrganizationId(){
        return PreferUtils.getString(OrganizationId,"");
    }





    public static void  savePartyMasses(String partyMasses){
        PreferUtils.put(PartyMasses,partyMasses);
    }
    public static String getPartyMasses(){
        return PreferUtils.getString(PartyMasses,"");
    }




    public static void  saveUserCode(String userCode){
        PreferUtils.put(UserCode,userCode);
    }
    public static String getUserCode(){
        return PreferUtils.getString(UserCode,"");
    }




    public static void  saveBirthDate(String birthDate){
        PreferUtils.put(BirthDate,birthDate);
    }
    public static String getBirthDate(){
        return PreferUtils.getString(BirthDate,"");
    }


    public static void  saveUserName(String userName){
        PreferUtils.put(UserName,userName);
    }
    public static String getUserName(){
        return PreferUtils.getString(UserName,"");
    }


    public static void  savePhoto(String photo){
        PreferUtils.put(Photo,photo);
    }
    public static String getPhoto(){
        return PreferUtils.getString(Photo,"");
    }





    public static void  saveUserSex(String userSex){
        PreferUtils.put(UserSex,userSex);
    }
    public static String getUserSex(){
        return PreferUtils.getString(UserSex,"");
    }
    public static void  saveOrganizationName(String organizationName){
        PreferUtils.put(OrganizationName,organizationName);
    }
    public static String getOrganizationName(){
        return PreferUtils.getString(OrganizationName,"");
    }
}
