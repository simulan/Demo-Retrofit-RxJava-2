package be.sanderdebleecker.uselections.mvp.presenter.expansions;

/**
 * @author simulan
 * @version 1.0.0
 * @since 21/05/2017
 */

public interface PermissionPresenter {
    void requestPermissions();
    void onPermissionsResult (int requestCode, String[] permissions, int[] grantResults);
}
