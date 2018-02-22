package com.github.wrdlbrnft.betterbarcodes.example.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.betterbarcodes.BarcodeFormat;
import com.github.wrdlbrnft.betterbarcodes.example.app.databinding.FragmentReaderBinding;
import com.github.wrdlbrnft.betterbarcodes.reader.BarcodeReader;
import com.github.wrdlbrnft.betterbarcodes.reader.permissions.PermissionHandler;
import com.github.wrdlbrnft.betterbarcodes.reader.permissions.PermissionRequest;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 04/12/2016
 */
public class ReaderFragment extends Fragment implements BarcodeReader.Callback {

    private PermissionRequest mPermissionRequest;

    public static ReaderFragment newInstance() {
        return new ReaderFragment();
    }

    private FragmentReaderBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentReaderBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setFragment(this);
        mBinding.barcodeReader.setCameraPermissionHandler(new PermissionHandler.Adapter() {
            @Override
            public void onNewPermissionRequest(PermissionRequest request) {
                mPermissionRequest = request;
                request.start(ReaderFragment.this);
            }
        });
        mBinding.barcodeReader.setCallback(this);
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
        final int currentFormat = mBinding.barcodeReader.getFormat();
        mBinding.barcodeReader.setFormat(enabled
                ? currentFormat | format
                : currentFormat & ~format
        );
    }

    private boolean isBarcodeFormatEnabled(@BarcodeFormat int format) {
        return (mBinding.barcodeReader.getFormat() & format) > 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.barcodeReader.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.barcodeReader.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResult(String text) {
        mBinding.barcodeReader.stopScanning();
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.fragment_reader_barcode_dialog_title)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, (dialogInterface, button) -> dialogInterface.dismiss())
                .setOnDismissListener(dialogInterface -> mBinding.barcodeReader.startScanning())
                .create();
        dialog.show();
    }
}