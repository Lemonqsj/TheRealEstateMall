package com.lemon.the_real_estate_mall.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.lemon.the_real_estate_mall.utils.ClassUtil;


public abstract class BaseActivity <VM extends AndroidViewModel, SV extends ViewDataBinding>extends AppCompatActivity {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        initViewModel();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // Translucent navigation bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
    }

    public void initData() {

    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {

    }
    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
////        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
////        initViewModel();
//    }
//    @Override
//    public void setContentView(Activity activity ,int layoutResID) {
//        bindingView = DataBindingUtil.setContentView(activity, layoutResID);
//        initViewModel();
//    }



    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = ViewModelProviders.of(this).get(viewModelClass);
            //页面数据初始化方法
            initData();
        }
    }

    /**
     * 不需要根据系统字体的大小来改变字体大小
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

//    private CustomDialog mDialogProgress = null;
//    public TextView tvTip = null;
//    private TextView tvMsgInfo;
//    private ImageView mUploadProgress;
//    public Dialog showDialogHorizontal(Context context, String title) {
//        if (!this.isFinishing()) {
//            if (mDialogProgress == null) {
//                View view = View.inflate(this, R.layout.dialog_waiting_horizontal, null);
//                tvTip = view.findViewById(R.id.tvTip);
//                tvMsgInfo = view.findViewById(R.id.tv_msg_info);
//                mUploadProgress = view.findViewById(R.id.upload_progress);
//                try {
//                    ImageUtil.loadCircle(this, R.drawable.upload_gif, mUploadProgress, R.color.colorPrimary, false);
//                    mDialogProgress = new CustomDialog(context, 700, 250, view, R.style.MyDialog);
//                } catch (Exception e) {
//                    e.fillInStackTrace();
//                }
//            }
//
//            if (mDialogProgress != null) {
//                tvMsgInfo.setText(title);
//                mDialogProgress.setCancelable(false);
//                mDialogProgress.setCanceledOnTouchOutside(false);
//                mDialogProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                mDialogProgress.show();
//            }
//
//        }
//        return mDialogProgress;
//
//    }
//
//    /**
//     * 隐藏等待提示框
//     */
//    public void hideWaitingDialog() {
//        if (mDialogProgress != null) {
//            mDialogProgress.dismiss();
//            mDialogProgress = null;
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBrightness(EventBrightness command){
//        int cmd = command.getCmd();
//        switch (cmd){
//            case Constant.BRIGHTNESS_INT: // 开启亮度
////                boolean b = AndroidUtils.isAutoBrightness(this);
//                AndroidUtils.autoBrightness(this,true);
//                AndroidUtils.saveBrightness(this,100);
//                break;
//            case Constant.BRIGHTNESS_CLOSE_INT: // 关闭亮度
//                AndroidUtils.autoBrightness(this,false); // 取消自动调节亮度
//                AndroidUtils.saveBrightness(this,0);// 取消省电
//                break;
//            case Constant.ELECTRIC: // 电量
//                int electricQuantity = AndroidUtils.getCapacity(this);
//                SpeechVoice.getInstance(this).startSpeak("剩余电量百分之" + NumberFormatUtil.formatInteger(electricQuantity),true);
//                break;
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Eventphone(EventCommand command){
//        int cmd = command.getEventCode();
//        switch (cmd){
//            case Constant.PHONE_CALL_CMD:
//                // 判断是否有sim卡
//                if (!NetWorkUtil.checkSimState(this)){
//                    BluetoothService.getInstance(this).sendMessage("挂断电话");
//                    ToastMsg.toastShow(this,"请检查是否安装SIM卡");
//                    return;
//                }
//                String phoneNumber = command.getMsg();
//                LogPrint.println("拨打电话:=======phoneNumber====" + phoneNumber);
//                PhoneUtils.getInstance(this).callPhone(phoneNumber);
//                if (PhoneUtils.getInstance(this).isOuting()&&
//                        LiveUtils.getInstance(this).getEasyPusher()!=null) {
//                    EventBus.getDefault().post(new EventCommand(Constant.LIVE_OFF_CMD));
//                }
//                break;
//            case Constant.HAND_UP_CMD:
//                LogPrint.println("挂断电话:===========");
//                PhoneUtils.getInstance(this).hangUpPhone();
//                break;
//            case Constant.ANSWER_CALLS_CMD:
//                LogPrint.println("接听电话:===========");
//                PhoneUtils.getInstance(this).answerRingingCall();
//                break;
//        }
//    }



}
