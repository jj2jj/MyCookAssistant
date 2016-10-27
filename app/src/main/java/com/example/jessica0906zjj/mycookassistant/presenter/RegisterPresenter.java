package com.example.jessica0906zjj.mycookassistant.presenter;

import com.example.jessica0906zjj.mycookassistant.base.RxPresenter;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.RegisterContract;
import com.example.jessica0906zjj.mycookassistant.view.RegisterView;

/**
 * Created by Jessica0906zjj on 2016-10-27.
 */

public class RegisterPresenter extends RxPresenter implements RegisterContract.Presenter{
    RegisterView mRegisterView;

  public RegisterPresenter(RegisterView view){
      mRegisterView = view;

      mRegisterView.attemptRegister();
      //
  }

}
