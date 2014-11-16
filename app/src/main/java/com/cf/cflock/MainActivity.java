package com.cf.cflock;


        import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {


    private DevicePolicyManager devicePolicyManager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        finish();
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        if (devicePolicyManager.isAdminActive(Dar.getCn(this))) {


            devicePolicyManager.lockNow();

            android.os.Process.killProcess(android.os.Process.myPid());
        }else{
            startAddDeviceAdminAty();
        }
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

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
