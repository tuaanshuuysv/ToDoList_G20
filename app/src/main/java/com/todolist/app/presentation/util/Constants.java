package com.todolist.app.presentation.util;

/**
 * Application-wide constants
 * ⚠️ IMPORTANT: Do NOT modify this file after branching!
 * All team members share this file.
 */
public class Constants {

    // ===========================================
    // DATABASE
    // ===========================================
    public static final String DATABASE_NAME = "todo_database";
    public static final int DATABASE_VERSION = 1;

    // ===========================================
    // SHARED PREFERENCES
    // ===========================================
    public static final String PREF_NAME = "TodoPreferences";
    public static final String PREF_THEME = "theme";
    public static final String PREF_LANGUAGE = "language";
    public static final String PREF_NOTIFICATIONS_ENABLED = "notifications_enabled";
    public static final String PREF_DEFAULT_PRIORITY = "default_priority";
    public static final String PREF_DEFAULT_REMINDER = "default_reminder";
    public static final String PREF_LAST_BACKUP = "last_backup";

    // ===========================================
    // PRIORITY LEVELS
    // ===========================================
    public static final String PRIORITY_HIGH = "HIGH";
    public static final String PRIORITY_MEDIUM = "MEDIUM";
    public static final String PRIORITY_LOW = "LOW";

    // ===========================================
    // TASK STATUS
    // ===========================================
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_COMPLETED = "COMPLETED";

    // ===========================================
    // PROJECT VIEW TYPES
    // ===========================================
    public static final String VIEW_TYPE_LIST = "LIST";
    public static final String VIEW_TYPE_KANBAN = "KANBAN";
    public static final String VIEW_TYPE_TIMELINE = "TIMELINE";
    public static final String VIEW_TYPE_CALENDAR = "CALENDAR";

    // ===========================================
    // NOTIFICATION
    // ===========================================
    public static final String CHANNEL_ID = "todo_reminders";
    public static final String CHANNEL_NAME = "Task Reminders";
    public static final int NOTIFICATION_ID_REMINDER = 1001;
    public static final int NOTIFICATION_ID_OVERDUE = 1002;
    public static final int NOTIFICATION_ID_DAILY = 1003;

    // ===========================================
    // INTENT EXTRAS
    // ===========================================
    public static final String EXTRA_TASK_ID = "task_id";
    public static final String EXTRA_PROJECT_ID = "project_id";
    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_IS_EDIT_MODE = "is_edit_mode";
    public static final String EXTRA_ACTION = "action";

    // ===========================================
    // ACTIONS (for notifications & intents)
    // ===========================================
    public static final String ACTION_COMPLETE_TASK = "com.todolist.app.COMPLETE_TASK";
    public static final String ACTION_SNOOZE_TASK = "com.todolist.app.SNOOZE_TASK";
    public static final String ACTION_ADD_TASK = "com.todolist.app.ADD_TASK";
    public static final String ACTION_OPEN_TASK = "com.todolist.app.OPEN_TASK";

    // ===========================================
    // REQUEST CODES
    // ===========================================
    public static final int REQUEST_ADD_TASK = 100;
    public static final int REQUEST_EDIT_TASK = 101;
    public static final int REQUEST_PICK_DATE = 200;
    public static final int REQUEST_PICK_TIME = 201;
    public static final int REQUEST_STORAGE_PERMISSION = 300;

    // ===========================================
    // LIMITS
    // ===========================================
    public static final int MAX_TITLE_LENGTH = 200;
    public static final int MAX_DESCRIPTION_LENGTH = 2000;
    public static final int MAX_PROJECT_NAME_LENGTH = 100;
    public static final int MAX_CATEGORY_NAME_LENGTH = 100;

    // ===========================================
    // DEFAULTS
    // ===========================================
    public static final int DEFAULT_REMINDER_MINUTES = 60;  // 1 hour before
    public static final String DEFAULT_PRIORITY = PRIORITY_MEDIUM;
    public static final String DEFAULT_PROJECT_COLOR = "#2196F3";
    public static final String DEFAULT_CATEGORY_COLOR = "#4CAF50";
    public static final String DEFAULT_THEME = "SYSTEM";
    public static final String DEFAULT_LANGUAGE = "vi";

    // ===========================================
    // BACKUP
    // ===========================================
    public static final String BACKUP_FILE_PREFIX = "TodoList_Backup_";
    public static final String BACKUP_FILE_EXTENSION = ".json";
    public static final int BACKUP_VERSION = 1;

    // ===========================================
    // RECURRENCE PATTERNS
    // ===========================================
    public static final String RECURRENCE_DAILY = "FREQ=DAILY";
    public static final String RECURRENCE_WEEKLY = "FREQ=WEEKLY";
    public static final String RECURRENCE_MONTHLY = "FREQ=MONTHLY";
    public static final String RECURRENCE_YEARLY = "FREQ=YEARLY";

    // ===========================================
    // DATE FORMATS
    // ===========================================
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";

    // ===========================================
    // NAVIGATION
    // ===========================================
    public static final String NAV_HOME = "nav_home";
    public static final String NAV_PROJECTS = "nav_projects";
    public static final String NAV_CATEGORIES = "nav_categories";
    public static final String NAV_STATISTICS = "nav_statistics";
    public static final String NAV_SETTINGS = "nav_settings";

    private Constants() {
        // Private constructor to prevent instantiation
    }
}