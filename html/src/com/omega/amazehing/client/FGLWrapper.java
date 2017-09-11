package com.omega.amazehing.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;

public class FGLWrapper {

    private static Runnable onUnlockSuccesTask;
    private static Runnable onUnlockFailedTask;
    private static Runnable onReadyTask;

    public static native void showAd()
    /*-{
    $wnd.fgl.showAd();
    }-*/;

    private static native JavaScriptObject getFgl()
    /*-{
    return $wnd.fgl;
    }-*/;

    public static native void showMoreGames()
    /*-{
    $wnd.fgl.showMoreGames();
    }-*/;

    public static native boolean isCrossPromotionEnabled()
    /*-{
    return $wnd.fgl.crossPromotionEnabled;
    }-*/;

    public static native boolean isPremium()
    /*-{
    return $wnd.fgl.isPremium();
    }-*/;

    public static native boolean isUnlockEnabled()
    /*-{
    return $wnd.fgl.unlockEnabled;
    }-*/;

    public static void setOnUnlockSuccesTask(Runnable runnable) {
	FGLWrapper.onUnlockSuccesTask = runnable;
    }

    public static void setOnUnlockFailedTask(Runnable runnable) {
	FGLWrapper.onUnlockFailedTask = runnable;
    }

    public static native void initiateUnlockFunction()
    /*-{
        var unlockSucces = function onUnlockSucces() {
        	@com.omega.amazehing.client.FGLWrapper::onUnlockSucces()();
        };
        var unlockFailed = function onUnlockFailed() {
        	@com.omega.amazehing.client.FGLWrapper::onUnlockFailed()();
        }
        
        $wnd.fgl.inApp.initiateUnlockFunction(unlockSucces, unlockFailed);
    }-*/;

    public static void onUnlockSucces() {
	if (onUnlockSuccesTask == null) {
	    Window.alert("onUnlockSucces task is null.");

	    return;
	}
	onUnlockSuccesTask.run();
    }

    public static void onUnlockFailed() {
	if (onUnlockFailedTask == null) {
	    Window.alert("onUnlockFailed task is null.");

	    return;
	}
	onUnlockFailedTask.run();
    }

    public static native boolean isBrandingEnabled()
    /*-{
        return $wnd.fgl.brandingEnabled;
    }-*/;

    public static native String getBrandingLogo()
    /*-{
        return $wnd.fgl.getBrandingLogo();
    }-*/;

    public static native void handleBrandingClick()
    /*-{
     	return $wnd.fgl.handleBrandingClick();
    }-*/;

    public static void setOnReadyTask(Runnable runnable) {
	onReadyTask = runnable;
    };

    public static void onApiReady() {
	if (onReadyTask == null) {
	    Window.alert("onReady task is null.");

	    return;
	}
	onReadyTask.run();
    }

    public static native void onReady()
    /*-{
        var onReady = function onReady() {
            	@com.omega.amazehing.client.FGLWrapper::onApiReady()();
            };
        $wnd.fgl.onReady(onReady);
    }-*/;

    public static native void submitScore(int score, String leaderboard)
    /*-{
    	$wnd.fgl.submitScore(score, leaderboard);
    }-*/;

    public static void submitScore(int score) {
	submitScore(score, null);
    }

    public static native void displayScoreboard(String leaderboardId, int highlightScore)
    /*-{
    	$wnd.fgl.displayScoreboard(leaderboardId, highlightScore);
    }-*/;

    public static native void displayScoreboard(String leaderboardId)
    /*-{
    	$wnd.fgl.displayScoreboard(leaderboardId);
    }-*/;

    public static void displayScoreboard(int highlightScore) {
	displayScoreboard(null, highlightScore);
    }

    public static void displayScoreboard() {
	displayScoreboard(null, 0);
    }

    public static native void grantAchievement(int achievementId)
    /*-{
    	$wnd.fgl.grantAchievement(achievementId);
    }-*/;

    public static native void showAchievements()
    /*-{
    	$wnd.fgl.showAchievements();
    }-*/;

    public static native boolean hasAchievement(int achievementId)
    /*-{
    	return $wnd.fgl.hasAchievement(achievementId);
    }-*/;

}
