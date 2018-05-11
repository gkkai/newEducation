package com.meiliangzi.app.tools;

/**
 * Created by Administrator on 2017/8/23.
 */

public class PreferManager {

    public static final String USERID="userId";
    public static final String TOKENS="tokens";
    public static final String IMAGES="images";
    private static final String USERSTAR="userStar";
    private static final String USERPHONE="userPhone";
    private static final String USERNAME="userName";
    private static final String USERCOMPANY="userCompany";
    private static final String USER_CODE="user_code";
    private static final String USERDEPARTMENT="userDepartment";
    private static final String ISPARTYMEMBER="isPartymember";
    private static final String COMPANYID = "companyId";
    private static final String DEPARTMENTID = "departmentId";
    private static final String IS_REMEMBER_PWD = "isremmemberpwd";
    private static final String LOGIN_PWD = "loginpwd";
    private static final String TIME_START = "timestart";
    private static final String TIME_END = "timeend";
    private static final String COMPLETEINFO="completeinfo";
    private static final String PARTYBRANCHID ="partyBranchId";
    private static final String PARTYBRANCHSID = "partyBranchsId";
    private static final String PARTYBRANNAME ="partyBranName" ;

    private static final String IsAuthorization ="isAuthorization" ;
    public static void  saveIsAuthorization(int isAuthorization){
        PreferUtils.put(IsAuthorization,isAuthorization);
    }
    public static int getIsAuthorization(){

        return PreferUtils.getInt(IsAuthorization,1);
    }

    public static void  saveIsRememberPwd(String isremmemberpwd){
        PreferUtils.put(IS_REMEMBER_PWD,isremmemberpwd);
    }

    public static String getTimeStart(){
        return PreferUtils.getString(TIME_START,"0");
    }

    public static void  saveTimeStart(String timestart){
        PreferUtils.put(TIME_START,timestart);
    }

    public static String getIsRememberPwd(){
        return PreferUtils.getString(IS_REMEMBER_PWD,"");
    }

    public static void  saveLoginPwd(String loginpwd){
        PreferUtils.put(LOGIN_PWD,loginpwd);
    }

    public static String getLoginPwd(){
        return PreferUtils.getString(LOGIN_PWD,"");
    }

    public static void  saveUserId(String userId){
        PreferUtils.put(USERID,userId);
    }
    public static void  saveTokens(String token){
        PreferUtils.put(TOKENS,token);
    }

    public static void  partyBranchId(String partyBranchId){
        PreferUtils.put(PARTYBRANCHID,partyBranchId);
    }
    public static String  getpartyBranchId(String partyBranchId){
        //PreferUtils.put(PARTYBRANCHID,partyBranchId);
        return PreferUtils.getString(PARTYBRANCHID,"0");
    }
    public static void  partyBranchsId(String partyBranchsId){
        PreferUtils.put(PARTYBRANCHSID,partyBranchsId);

    }
    public static void  partyBranName(String partyBranId){
        PreferUtils.put(PARTYBRANNAME,partyBranId);

    }
    public static String  getpartyBranName(String partyBranId){
        //PreferUtils.put(PARTYBRANCHSID,partyBranchsId);
        return PreferUtils.getString(PARTYBRANNAME,"0");
    }
    public static String  getpartyBranchsId(String partyBranchsId){
        //PreferUtils.put(PARTYBRANCHSID,partyBranchsId);
        return PreferUtils.getString(PARTYBRANCHSID,"0");
    }

    public static String getUserId(){
        return PreferUtils.getString(USERID,"");
    }
    public static String getTokens(){
        return PreferUtils.getString(TOKENS,"");
    }

    public static void saveUserStar(String userStar) {
        PreferUtils.put(USERSTAR,userStar);
    }

    public static String getUserStar() {
        return PreferUtils.getString(USERSTAR,"");
    }


    public static void saveUserPhone(String userPhone) {
        PreferUtils.put(USERPHONE,userPhone);
    }

    public static String getUserPhone() {
        return PreferUtils.getString(USERPHONE,"");
    }

    public static String getUserName(){
        return PreferUtils.getString(USERNAME,"");
    }

    public static void saveUserName(String userName){
        PreferUtils.put(USERNAME,userName);
    }

    public static void saveCompany(String userCompany) {
        PreferUtils.put(USERCOMPANY,userCompany);
    }

    public static String getCompany(){
        return PreferUtils.getString(USERCOMPANY,"");
    }

    public static void saveWorkNum(String user_code) {
        PreferUtils.put(USER_CODE,user_code);
    }

    public static String getWorkNum(){
        return PreferUtils.getString(USER_CODE,"");
    }

    public static void saveDepartment(String userDepartment) {
        PreferUtils.put(USERDEPARTMENT,userDepartment);
    }

    public static String getDepartment(){
        return PreferUtils.getString(USERDEPARTMENT,"");
    }

    public static void saveIsPartment(boolean isPartymember) {
        PreferUtils.put(ISPARTYMEMBER,isPartymember);
    }

    public static boolean isPartment(){
        return PreferUtils.getBoolean(ISPARTYMEMBER,false);
    }

    public static void saveCompanyId(String companyId) {
        PreferUtils.put(COMPANYID,companyId);
    }

    public static String getCompanId(){
        return PreferUtils.getString(COMPANYID,"");
    }

    public static void savePartmentId(String departmentId) {
        PreferUtils.put(DEPARTMENTID,departmentId);
    }

    public static void saveIsCompleteInfo(boolean isCompleteInfo){
        PreferUtils.put(COMPLETEINFO,isCompleteInfo);
    }

    public static boolean getIsComplete(){
        return PreferUtils.getBoolean(COMPLETEINFO,false);
    }

    public static String getDepartmentId() {
        return PreferUtils.getString(DEPARTMENTID,"");
    }

}
