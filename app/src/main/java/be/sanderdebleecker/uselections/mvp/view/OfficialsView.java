package be.sanderdebleecker.uselections.mvp.view;

import java.util.List;

import be.sanderdebleecker.uselections.BaseActivity;
import be.sanderdebleecker.uselections.mvp.model.view.OfficialVM;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 22/05/2017
 */

public interface OfficialsView extends BaseView {
    void onOfficialsLoaded(List<OfficialVM> elections);
    BaseActivity getViewActivity();
    void onShowDialog(String message);
    void onHideDialog();
    void onShowToast(String message);
    void onClearItems();
}
