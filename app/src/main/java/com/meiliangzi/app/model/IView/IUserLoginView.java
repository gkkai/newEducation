package com.meiliangzi.app.model.IView;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();


    void showFailedError();
}
