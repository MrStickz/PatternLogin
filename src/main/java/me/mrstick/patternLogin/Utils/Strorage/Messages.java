package me.mrstick.patternLogin.Utils.Strorage;

import me.mrstick.patternLogin.Utils.PluginManager;

public class Messages {

    public static String permission_error = PluginManager.GetMessageChanger().ColorGrade("permission-error");

    // Usages
    public static String pattern_relogin_usage = PluginManager.GetMessageChanger().ColorGrade("pattern-relogin");
    public static String pattern_reset_usage = PluginManager.GetMessageChanger().ColorGrade("pattern-reset");

    // Errors
    public static String wrong_pattern = PluginManager.GetMessageChanger().ColorGrade("wrong-pattern");
    public static String unknown_player = PluginManager.GetMessageChanger().ColorGrade("unknown-player");

    //

    public static String registered_successfully = PluginManager.GetMessageChanger().ColorGrade("registered-successfully");
    public static String login_successfully = PluginManager.GetMessageChanger().ColorGrade("loggedin-successfully");
    public static String loggedin_from_session = PluginManager.GetMessageChanger().ColorGrade("loggedin-from-session");

    public static String logged_out_by_admin = PluginManager.GetMessageChanger().ColorGrade("loggedout-by-admin");
    public static String logged_out_success = PluginManager.GetMessageChanger().ColorGrade("loggedout-successfully");
    public static String logged_out_by_admin_kick_msg = PluginManager.GetMessageChanger().ColorGrade("loggedout-by-admin-kick-msg");

    public static String pswd_reset_by_admin = PluginManager.GetMessageChanger().ColorGrade("pattern-reset-by-admin");
    public static String pswd_reset_success = PluginManager.GetMessageChanger().ColorGrade("pattern-reset-successfully");

    public static String max_tries_reached = PluginManager.GetMessageChanger().ColorGrade("max-tries-reached");
}
