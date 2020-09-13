package com.geekbrains.gwt.client.utils;

import com.google.gwt.user.client.ui.TabLayoutPanel;

import java.util.List;

public class TabsUtil {
    public static void tabsRefresh (TabLayoutPanel tabPanel, Integer selectedTab, List<Integer> opened) {
        for (int i = 0; i < tabPanel.getWidgetCount(); i++) {
            if (opened.contains(i)) {
                tabPanel.getTabWidget(i).setVisible(true);
                tabPanel.getWidget(i).removeStyleName("hidden");
            } else {
                tabPanel.getTabWidget(i).setVisible(false);
                tabPanel.getWidget(i).addStyleName("hidden");
            }
        }

        if (selectedTab != null)
            tabPanel.selectTab(selectedTab);
    }
}
