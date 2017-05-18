package com.dgrissom.imagescript.menus;

import com.dgrissom.imagescript.ImageScript;

import java.io.IOException;

public class ViewMenu {
    private ViewMenu() {}

    public static void onShowImageViewer() {
        try {
            ImageScript.getInstance().showImageViewer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
