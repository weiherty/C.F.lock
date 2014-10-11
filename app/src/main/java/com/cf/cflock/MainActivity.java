package com.cf.cflock;


        import android.app.Activity;
        import android.app.admin.DevicePolicyManager;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;

        import java.util.Timer;
        import java.util.TimerTask;

public class MainActivity extends Activity {


    private DevicePolicyManager devicePolicyManager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        finish();
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        if (devicePolicyManager.isAdminActive(Dar.getCn(this))) {

           // finish();

            devicePolicyManager.lockNow();
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(0);//直接结束程序
        }else{
            startAddDeviceAdminAty();
        }
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

//或者下面这种方式
//android.os.Process.killProcess(android.os.Process.myPid());
    }
    private void startAddDeviceAdminAty(){
        Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                Dar.getCn(this));
        i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "C.F.lock 注册设备管理器");

        startActivityForResult(i, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==Activity.RESULT_OK) {
            devicePolicyManager.lockNow();
            android.os.Process.killProcess(android.os.Process.myPid());
        }else{
            startAddDeviceAdminAty();
        }

    }

}
