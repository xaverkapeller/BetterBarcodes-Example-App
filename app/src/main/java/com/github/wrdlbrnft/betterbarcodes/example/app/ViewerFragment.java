package com.github.wrdlbrnft.betterbarcodes.example.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.betterbarcodes.BarcodeFormat;
import com.github.wrdlbrnft.betterbarcodes.example.app.databinding.FragmentViewerBinding;
import com.github.wrdlbrnft.betterbarcodes.views.writer.layoutmanagers.HorizontalRotatingLayoutManager;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 04/12/2016
 */
public class ViewerFragment extends Fragment {

    public static ViewerFragment newInstance() {
        return new ViewerFragment();
    }

    private FragmentViewerBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentViewerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setFragment(this);
        mBinding.barcodeView.setLayoutManager(new CustomBarcodeLayoutManager());
    }

    public void setBarcodeText(String text) {
        mBinding.barcodeView.setText(text);
    }

    public String getBarcodeText() {
        return mBinding.barcodeView.getText();
    }

    public void setQrCodeEnabled(boolean enabled) {
        setBarcodeFormat(enabled, BarcodeFormat.QR_CODE);
    }

    public boolean getQrCodeEnabled() {
        return isBarcodeFormatEnabled(BarcodeFormat.QR_CODE);
    }

    public void setCode128Enabled(boolean enabled) {
        setBarcodeFormat(enabled, BarcodeFormat.CODE_128);
    }

    public boolean getCode128Enabled() {
        return isBarcodeFormatEnabled(BarcodeFormat.CODE_128);
    }

    public void setAztecEnabled(boolean enabled) {
        setBarcodeFormat(enabled, BarcodeFormat.AZTEC);
    }

    public boolean getAztecEnabled() {
        return isBarcodeFormatEnabled(BarcodeFormat.AZTEC);
    }

    private void setBarcodeFormat(boolean enabled, @BarcodeFormat int format) {
        if (enabled) {
            mBinding.barcodeView.setFormat(mBinding.barcodeView.getFormat() | format);
        } else {
            final int newFormat = format == BarcodeFormat.NONE
                    ? BarcodeFormat.NONE
                    : mBinding.barcodeView.getFormat() & ~format;
            mBinding.barcodeView.setFormat(newFormat);
        }
    }

    private boolean isBarcodeFormatEnabled(@BarcodeFormat int format) {
        return (mBinding.barcodeView.getFormat() & format) > 0;
    }

    private class CustomBarcodeLayoutManager extends HorizontalRotatingLayoutManager {

        @Override
        public void onSwitchToSelectMode(View barcodes, View descriptions) {
            super.onSwitchToSelectMode(barcodes, descriptions);
            if (mBinding.barcodeInstructions.getVisibility() != View.VISIBLE) {
                mBinding.barcodeInstructions.setVisibility(View.VISIBLE);
                mBinding.barcodeInstructions.setTranslationY(-mBinding.barcodeInstructions.getHeight());
            }
            mBinding.barcodeInstructions.animate().translationY(0.0f);
        }

        @Override
        public void onSwitchToDisplayMode(View barcodes, View descriptions) {
            super.onSwitchToDisplayMode(barcodes, descriptions);
            mBinding.barcodeInstructions.animate().translationY(-mBinding.barcodeInstructions.getHeight());
        }
    }
}