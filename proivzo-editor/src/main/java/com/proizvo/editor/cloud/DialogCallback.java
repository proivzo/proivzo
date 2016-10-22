package com.proizvo.editor.cloud;

public interface DialogCallback<A, B, C> {

    void onDialogResult(int result, A key, B value, C mod);

}
