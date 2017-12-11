package com.example.englandhoang.munimuni;

import android.test.ActivityInstrumentationTestCase2;

import com.example.englandhoang.munimuni.Main.MainActivity;
import com.robotium.solo.Solo;

/**
 * Created by EnglandHoang on 12/09/2017.
 */

public class AITC2 extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;


    public AITC2() {
        super(MainActivity.class);
    }

    public void test() throws Exception {


        solo = new Solo(getInstrumentation(), getActivity());
        solo.assertCurrentActivity("Sai activity", MainActivity.class);

        solo.clickOnText("Continue with Facebook");
        solo.waitForDialogToClose();
        solo.clickOnText("Bạn bè");
        solo.clickOnText("Giao lưu cộng đồng");
        solo.enterText(0, "Xin chào ahihi đồ ngốc");
        solo.clickOnImageButton(2);
        solo.goBack();


        /*solo.clickOnText("Chưa có tài khoản?");
        solo.enterText(0, "Nguyễn Đăng Hoàng Anh");
        solo.enterText(1, "NHA@gmail.com");
        solo.enterText(2, "123456");
        solo.clickOnButton(0);
        solo.waitForDialogToClose();*/






    }

}
