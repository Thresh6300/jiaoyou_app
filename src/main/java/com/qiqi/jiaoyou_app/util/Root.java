package com.qiqi.jiaoyou_app.util;

import java.util.List;

public class Root
{
    private List<UserDataItem> UserDataItem;

    private int StandardSequence;

    private int CustomSequence;

    private int FriendNum;

    private int CompleteFlag;

    private int NextStartIndex;

    private String ActionStatus;

    private int ErrorCode;

    private String ErrorInfo;

    private String ErrorDisplay;

    //添加无参的构造器
    public Root(){
    }
    public void setUserDataItem(List<UserDataItem> UserDataItem){
        this.UserDataItem = UserDataItem;
    }
    public List<UserDataItem> getUserDataItem(){
        return this.UserDataItem;
    }
    public void setStandardSequence(int StandardSequence){
        this.StandardSequence = StandardSequence;
    }
    public int getStandardSequence(){
        return this.StandardSequence;
    }
    public void setCustomSequence(int CustomSequence){
        this.CustomSequence = CustomSequence;
    }
    public int getCustomSequence(){
        return this.CustomSequence;
    }
    public void setFriendNum(int FriendNum){
        this.FriendNum = FriendNum;
    }
    public int getFriendNum(){
        return this.FriendNum;
    }
    public void setCompleteFlag(int CompleteFlag){
        this.CompleteFlag = CompleteFlag;
    }
    public int getCompleteFlag(){
        return this.CompleteFlag;
    }
    public void setNextStartIndex(int NextStartIndex){
        this.NextStartIndex = NextStartIndex;
    }
    public int getNextStartIndex(){
        return this.NextStartIndex;
    }
    public void setActionStatus(String ActionStatus){
        this.ActionStatus = ActionStatus;
    }
    public String getActionStatus(){
        return this.ActionStatus;
    }
    public void setErrorCode(int ErrorCode){
        this.ErrorCode = ErrorCode;
    }
    public int getErrorCode(){
        return this.ErrorCode;
    }
    public void setErrorInfo(String ErrorInfo){
        this.ErrorInfo = ErrorInfo;
    }
    public String getErrorInfo(){
        return this.ErrorInfo;
    }
    public void setErrorDisplay(String ErrorDisplay){
        this.ErrorDisplay = ErrorDisplay;
    }
    public String getErrorDisplay(){
        return this.ErrorDisplay;
    }

    @Override
    public String toString() {
        return "Root{" +
                "UserDataItem=" + UserDataItem +
                ", StandardSequence=" + StandardSequence +
                ", CustomSequence=" + CustomSequence +
                ", FriendNum=" + FriendNum +
                ", CompleteFlag=" + CompleteFlag +
                ", NextStartIndex=" + NextStartIndex +
                ", ActionStatus='" + ActionStatus + '\'' +
                ", ErrorCode=" + ErrorCode +
                ", ErrorInfo='" + ErrorInfo + '\'' +
                ", ErrorDisplay='" + ErrorDisplay + '\'' +
                '}';
    }
}
