package ohos.samples.camera;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorGroup;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.StackLayout;
import ohos.agp.components.AttrSet;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.service.DisplayAttributes;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.global.configuration.Configuration;

public class CaptureLayout extends StackLayout {

    private TypeButton mBtn_confirm; // 确认按钮
    private TypeButton mBtn_cancel; // 取消按钮

    private int mLayout_width;

    private int mButton_size;

    private ClickListener mLeftClickListener; // 左边按钮监听
    private ClickListener mRightClickListener; // 右边按钮监听

    public CaptureLayout(Context context) {
        this(context, null);
    }

    public CaptureLayout(Context context, AttrSet attrs) {
        this(context, attrs, null);
    }

    public CaptureLayout(Context context, AttrSet attrs, String defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayAttributes displayAttributes =
                DisplayManager.getInstance().getDefaultDisplay(getContext()).get().getAttributes();
        int screenWidth = displayAttributes.width;
        if (getResourceManager().getConfiguration().direction == Configuration.DIRECTION_VERTICAL) {
            mLayout_width = screenWidth;
        } else {
            mLayout_width = screenWidth / 2;
        }

        mButton_size = (int) (mLayout_width / 4.5f);
        initView();
        initEvent();
    }

    public void initEvent() {
        mBtn_cancel.setVisibility(HIDE);
        mBtn_confirm.setVisibility(HIDE);
    }

    public void startTypeBtnAnimator() {
        LogUtil.info(LogUtil.DEFAULT_TAG, "startTypeBtnAnimator");
        // 拍照录制结果后的动画
        mBtn_cancel.setVisibility(VISIBLE);
        mBtn_confirm.setVisibility(VISIBLE);
        mBtn_cancel.setClickable(false);
        mBtn_confirm.setClickable(false);

        AnimatorProperty propertyO = new AnimatorProperty();
        propertyO.moveFromX(mLayout_width / 2f);
        propertyO.moveToX(mButton_size / 2f);
        propertyO.setTarget(mBtn_cancel);

        AnimatorProperty propertyT = new AnimatorProperty();
        propertyT.moveFromX(mLayout_width / 2f);
        propertyT.moveToX(mLayout_width / 2f + mButton_size / 2f);
        propertyT.setTarget(mBtn_confirm);

        startAnimator(propertyO, propertyT);
    }

    private void startAnimator(AnimatorProperty propertyO, AnimatorProperty propertyT) {
        AnimatorGroup animatorGroup = new AnimatorGroup();
        animatorGroup.runParallel(propertyO, propertyT);
        animatorGroup.setStateChangedListener(
                new Animator.StateChangedListener() {
                    @Override
                    public void onStart(Animator animator) {
                        mBtn_cancel.setClickable(true);
                        mBtn_confirm.setClickable(true);
                    }

                    @Override
                    public void onStop(Animator animator) {
                        mBtn_cancel.setClickable(true);
                        mBtn_confirm.setClickable(true);
                    }

                    @Override
                    public void onCancel(Animator animator) {
                        mBtn_cancel.setClickable(true);
                        mBtn_confirm.setClickable(true);
                    }

                    @Override
                    public void onEnd(Animator animator) {
                        mBtn_cancel.setClickable(true);
                        mBtn_confirm.setClickable(true);
                    }

                    @Override
                    public void onPause(Animator animator) {
                        mBtn_cancel.setClickable(true);
                        mBtn_confirm.setClickable(true);
                    }

                    @Override
                    public void onResume(Animator animator) {
                    }
                });
        animatorGroup.setDuration(300);
        animatorGroup.start();
    }

    private void initView() {
        addBtnCancelEvent();
        addBtnConfirmEvent();
        this.addComponent(mBtn_cancel);
        this.addComponent(mBtn_confirm);
    }

    private void addBtnConfirmEvent() {
        // 确认按钮
        mBtn_confirm = new TypeButton(getContext(), TypeButton.TYPE_CONFIRM, mButton_size);
        LayoutConfig btnConfirmParam = new LayoutConfig(mButton_size, mButton_size);
        btnConfirmParam.alignment = LayoutAlignment.CENTER;
        mBtn_confirm.setLayoutConfig(btnConfirmParam);
        mBtn_confirm.setClickedListener(
                component -> {
                    if (mRightClickListener != null) {
                        mRightClickListener.onClick();
                    }
                });
    }

    private void addBtnCancelEvent() {
        // 取消按钮
        mBtn_cancel = new TypeButton(getContext(), TypeButton.TYPE_CANCEL, mButton_size);

        LayoutConfig btnCancelParam = new LayoutConfig(mButton_size, mButton_size);
        btnCancelParam.alignment = LayoutAlignment.CENTER;
        mBtn_cancel.setLayoutConfig(btnCancelParam);
        mBtn_cancel.setClickedListener(
                component -> {
                    if (mLeftClickListener != null) {
                        mLeftClickListener.onClick();
                    }
                });
    }

    public void resetCaptureLayout() {
        mBtn_cancel.setVisibility(HIDE);
        mBtn_confirm.setVisibility(HIDE);
    }

    public void setLeftClickListener(ClickListener leftClickListener) {
        this.mLeftClickListener = leftClickListener;
    }

    public void setRightClickListener(ClickListener rightClickListener) {
        this.mRightClickListener = rightClickListener;
    }

}
