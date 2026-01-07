package com.todolist.app.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.todolist.app.data.local.dao.ActivityLogDao;
import com.todolist.app.data.local.dao.ActivityLogDao_Impl;
import com.todolist.app.data.local.dao.CategoryDao;
import com.todolist.app.data.local.dao.CategoryDao_Impl;
import com.todolist.app.data.local.dao.ProjectDao;
import com.todolist.app.data.local.dao.ProjectDao_Impl;
import com.todolist.app.data.local.dao.SubtaskDao;
import com.todolist.app.data.local.dao.SubtaskDao_Impl;
import com.todolist.app.data.local.dao.TaskDao;
import com.todolist.app.data.local.dao.TaskDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TodoDatabase_Impl extends TodoDatabase {
  private volatile TaskDao _taskDao;

  private volatile SubtaskDao _subtaskDao;

  private volatile ProjectDao _projectDao;

  private volatile CategoryDao _categoryDao;

  private volatile ActivityLogDao _activityLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`task_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `project_id` INTEGER, `category_id` INTEGER, `priority` TEXT, `status` TEXT, `due_date` INTEGER, `reminder_time` INTEGER, `estimated_duration` INTEGER, `actual_duration` INTEGER, `is_completed` INTEGER NOT NULL, `completed_at` INTEGER, `is_recurring` INTEGER NOT NULL, `recurrence_rule` TEXT, `parent_recurring_task_id` INTEGER, `position` INTEGER NOT NULL, `created_at` INTEGER, `updated_at` INTEGER, FOREIGN KEY(`project_id`) REFERENCES `projects`(`project_id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`category_id`) REFERENCES `categories`(`category_id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_project_id` ON `tasks` (`project_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_category_id` ON `tasks` (`category_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_due_date` ON `tasks` (`due_date`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_is_completed` ON `tasks` (`is_completed`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_priority` ON `tasks` (`priority`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `projects` (`project_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `description` TEXT, `color` TEXT, `icon` TEXT, `deadline` INTEGER, `default_category_id` INTEGER, `view_type` TEXT, `is_archived` INTEGER NOT NULL, `created_at` INTEGER, `updated_at` INTEGER, FOREIGN KEY(`default_category_id`) REFERENCES `categories`(`category_id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_projects_is_archived` ON `projects` (`is_archived`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_projects_deadline` ON `projects` (`deadline`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_projects_default_category_id` ON `projects` (`default_category_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`category_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `color` TEXT, `icon` TEXT, `created_at` INTEGER)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_categories_name` ON `categories` (`name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `subtasks` (`subtask_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `task_id` INTEGER NOT NULL, `title` TEXT, `is_completed` INTEGER NOT NULL, `position` INTEGER NOT NULL, `created_at` INTEGER, FOREIGN KEY(`task_id`) REFERENCES `tasks`(`task_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_subtasks_task_id` ON `subtasks` (`task_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_subtasks_task_id_position` ON `subtasks` (`task_id`, `position`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `milestones` (`milestone_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `project_id` INTEGER NOT NULL, `name` TEXT, `description` TEXT, `due_date` INTEGER, `is_completed` INTEGER NOT NULL, `created_at` INTEGER, FOREIGN KEY(`project_id`) REFERENCES `projects`(`project_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_milestones_project_id` ON `milestones` (`project_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_milestones_due_date` ON `milestones` (`due_date`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `activity_logs` (`log_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `entity_type` TEXT, `entity_id` INTEGER NOT NULL, `action` TEXT, `details` TEXT, `created_at` INTEGER)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_activity_logs_entity_type_entity_id` ON `activity_logs` (`entity_type`, `entity_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_activity_logs_created_at` ON `activity_logs` (`created_at`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tags` (`tag_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `color` TEXT, `created_at` INTEGER)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_tags_name` ON `tags` (`name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `task_tags` (`task_id` INTEGER NOT NULL, `tag_id` INTEGER NOT NULL, PRIMARY KEY(`task_id`, `tag_id`), FOREIGN KEY(`task_id`) REFERENCES `tasks`(`task_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`tag_id`) REFERENCES `tags`(`tag_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_task_tags_task_id` ON `task_tags` (`task_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_task_tags_tag_id` ON `task_tags` (`tag_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '41f2e069bb084bbda60cc2b41d90d296')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `tasks`");
        db.execSQL("DROP TABLE IF EXISTS `projects`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `subtasks`");
        db.execSQL("DROP TABLE IF EXISTS `milestones`");
        db.execSQL("DROP TABLE IF EXISTS `activity_logs`");
        db.execSQL("DROP TABLE IF EXISTS `tags`");
        db.execSQL("DROP TABLE IF EXISTS `task_tags`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTasks = new HashMap<String, TableInfo.Column>(19);
        _columnsTasks.put("task_id", new TableInfo.Column("task_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("project_id", new TableInfo.Column("project_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("category_id", new TableInfo.Column("category_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("priority", new TableInfo.Column("priority", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("due_date", new TableInfo.Column("due_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("reminder_time", new TableInfo.Column("reminder_time", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("estimated_duration", new TableInfo.Column("estimated_duration", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("actual_duration", new TableInfo.Column("actual_duration", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("is_completed", new TableInfo.Column("is_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("completed_at", new TableInfo.Column("completed_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("is_recurring", new TableInfo.Column("is_recurring", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("recurrence_rule", new TableInfo.Column("recurrence_rule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("parent_recurring_task_id", new TableInfo.Column("parent_recurring_task_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("position", new TableInfo.Column("position", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTasks = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysTasks.add(new TableInfo.ForeignKey("projects", "SET NULL", "NO ACTION", Arrays.asList("project_id"), Arrays.asList("project_id")));
        _foreignKeysTasks.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("category_id")));
        final HashSet<TableInfo.Index> _indicesTasks = new HashSet<TableInfo.Index>(5);
        _indicesTasks.add(new TableInfo.Index("index_tasks_project_id", false, Arrays.asList("project_id"), Arrays.asList("ASC")));
        _indicesTasks.add(new TableInfo.Index("index_tasks_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        _indicesTasks.add(new TableInfo.Index("index_tasks_due_date", false, Arrays.asList("due_date"), Arrays.asList("ASC")));
        _indicesTasks.add(new TableInfo.Index("index_tasks_is_completed", false, Arrays.asList("is_completed"), Arrays.asList("ASC")));
        _indicesTasks.add(new TableInfo.Index("index_tasks_priority", false, Arrays.asList("priority"), Arrays.asList("ASC")));
        final TableInfo _infoTasks = new TableInfo("tasks", _columnsTasks, _foreignKeysTasks, _indicesTasks);
        final TableInfo _existingTasks = TableInfo.read(db, "tasks");
        if (!_infoTasks.equals(_existingTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "tasks(com.todolist.app.data.local.entity.Task).\n"
                  + " Expected:\n" + _infoTasks + "\n"
                  + " Found:\n" + _existingTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsProjects = new HashMap<String, TableInfo.Column>(11);
        _columnsProjects.put("project_id", new TableInfo.Column("project_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("icon", new TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("deadline", new TableInfo.Column("deadline", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("default_category_id", new TableInfo.Column("default_category_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("view_type", new TableInfo.Column("view_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("is_archived", new TableInfo.Column("is_archived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProjects = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysProjects.add(new TableInfo.ForeignKey("categories", "SET NULL", "NO ACTION", Arrays.asList("default_category_id"), Arrays.asList("category_id")));
        final HashSet<TableInfo.Index> _indicesProjects = new HashSet<TableInfo.Index>(3);
        _indicesProjects.add(new TableInfo.Index("index_projects_is_archived", false, Arrays.asList("is_archived"), Arrays.asList("ASC")));
        _indicesProjects.add(new TableInfo.Index("index_projects_deadline", false, Arrays.asList("deadline"), Arrays.asList("ASC")));
        _indicesProjects.add(new TableInfo.Index("index_projects_default_category_id", false, Arrays.asList("default_category_id"), Arrays.asList("ASC")));
        final TableInfo _infoProjects = new TableInfo("projects", _columnsProjects, _foreignKeysProjects, _indicesProjects);
        final TableInfo _existingProjects = TableInfo.read(db, "projects");
        if (!_infoProjects.equals(_existingProjects)) {
          return new RoomOpenHelper.ValidationResult(false, "projects(com.todolist.app.data.local.entity.Project).\n"
                  + " Expected:\n" + _infoProjects + "\n"
                  + " Found:\n" + _existingProjects);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(5);
        _columnsCategories.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("icon", new TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(1);
        _indicesCategories.add(new TableInfo.Index("index_categories_name", true, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.todolist.app.data.local.entity.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsSubtasks = new HashMap<String, TableInfo.Column>(6);
        _columnsSubtasks.put("subtask_id", new TableInfo.Column("subtask_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtasks.put("task_id", new TableInfo.Column("task_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtasks.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtasks.put("is_completed", new TableInfo.Column("is_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtasks.put("position", new TableInfo.Column("position", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtasks.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubtasks = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSubtasks.add(new TableInfo.ForeignKey("tasks", "CASCADE", "NO ACTION", Arrays.asList("task_id"), Arrays.asList("task_id")));
        final HashSet<TableInfo.Index> _indicesSubtasks = new HashSet<TableInfo.Index>(2);
        _indicesSubtasks.add(new TableInfo.Index("index_subtasks_task_id", false, Arrays.asList("task_id"), Arrays.asList("ASC")));
        _indicesSubtasks.add(new TableInfo.Index("index_subtasks_task_id_position", false, Arrays.asList("task_id", "position"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoSubtasks = new TableInfo("subtasks", _columnsSubtasks, _foreignKeysSubtasks, _indicesSubtasks);
        final TableInfo _existingSubtasks = TableInfo.read(db, "subtasks");
        if (!_infoSubtasks.equals(_existingSubtasks)) {
          return new RoomOpenHelper.ValidationResult(false, "subtasks(com.todolist.app.data.local.entity.Subtask).\n"
                  + " Expected:\n" + _infoSubtasks + "\n"
                  + " Found:\n" + _existingSubtasks);
        }
        final HashMap<String, TableInfo.Column> _columnsMilestones = new HashMap<String, TableInfo.Column>(7);
        _columnsMilestones.put("milestone_id", new TableInfo.Column("milestone_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("project_id", new TableInfo.Column("project_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("due_date", new TableInfo.Column("due_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("is_completed", new TableInfo.Column("is_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMilestones = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMilestones.add(new TableInfo.ForeignKey("projects", "CASCADE", "NO ACTION", Arrays.asList("project_id"), Arrays.asList("project_id")));
        final HashSet<TableInfo.Index> _indicesMilestones = new HashSet<TableInfo.Index>(2);
        _indicesMilestones.add(new TableInfo.Index("index_milestones_project_id", false, Arrays.asList("project_id"), Arrays.asList("ASC")));
        _indicesMilestones.add(new TableInfo.Index("index_milestones_due_date", false, Arrays.asList("due_date"), Arrays.asList("ASC")));
        final TableInfo _infoMilestones = new TableInfo("milestones", _columnsMilestones, _foreignKeysMilestones, _indicesMilestones);
        final TableInfo _existingMilestones = TableInfo.read(db, "milestones");
        if (!_infoMilestones.equals(_existingMilestones)) {
          return new RoomOpenHelper.ValidationResult(false, "milestones(com.todolist.app.data.local.entity.Milestone).\n"
                  + " Expected:\n" + _infoMilestones + "\n"
                  + " Found:\n" + _existingMilestones);
        }
        final HashMap<String, TableInfo.Column> _columnsActivityLogs = new HashMap<String, TableInfo.Column>(6);
        _columnsActivityLogs.put("log_id", new TableInfo.Column("log_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("entity_type", new TableInfo.Column("entity_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("entity_id", new TableInfo.Column("entity_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("action", new TableInfo.Column("action", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("details", new TableInfo.Column("details", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysActivityLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesActivityLogs = new HashSet<TableInfo.Index>(2);
        _indicesActivityLogs.add(new TableInfo.Index("index_activity_logs_entity_type_entity_id", false, Arrays.asList("entity_type", "entity_id"), Arrays.asList("ASC", "ASC")));
        _indicesActivityLogs.add(new TableInfo.Index("index_activity_logs_created_at", false, Arrays.asList("created_at"), Arrays.asList("ASC")));
        final TableInfo _infoActivityLogs = new TableInfo("activity_logs", _columnsActivityLogs, _foreignKeysActivityLogs, _indicesActivityLogs);
        final TableInfo _existingActivityLogs = TableInfo.read(db, "activity_logs");
        if (!_infoActivityLogs.equals(_existingActivityLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "activity_logs(com.todolist.app.data.local.entity.ActivityLog).\n"
                  + " Expected:\n" + _infoActivityLogs + "\n"
                  + " Found:\n" + _existingActivityLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsTags = new HashMap<String, TableInfo.Column>(4);
        _columnsTags.put("tag_id", new TableInfo.Column("tag_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("created_at", new TableInfo.Column("created_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTags = new HashSet<TableInfo.Index>(1);
        _indicesTags.add(new TableInfo.Index("index_tags_name", true, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoTags = new TableInfo("tags", _columnsTags, _foreignKeysTags, _indicesTags);
        final TableInfo _existingTags = TableInfo.read(db, "tags");
        if (!_infoTags.equals(_existingTags)) {
          return new RoomOpenHelper.ValidationResult(false, "tags(com.todolist.app.data.local.entity.Tag).\n"
                  + " Expected:\n" + _infoTags + "\n"
                  + " Found:\n" + _existingTags);
        }
        final HashMap<String, TableInfo.Column> _columnsTaskTags = new HashMap<String, TableInfo.Column>(2);
        _columnsTaskTags.put("task_id", new TableInfo.Column("task_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskTags.put("tag_id", new TableInfo.Column("tag_id", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTaskTags = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysTaskTags.add(new TableInfo.ForeignKey("tasks", "CASCADE", "NO ACTION", Arrays.asList("task_id"), Arrays.asList("task_id")));
        _foreignKeysTaskTags.add(new TableInfo.ForeignKey("tags", "CASCADE", "NO ACTION", Arrays.asList("tag_id"), Arrays.asList("tag_id")));
        final HashSet<TableInfo.Index> _indicesTaskTags = new HashSet<TableInfo.Index>(2);
        _indicesTaskTags.add(new TableInfo.Index("index_task_tags_task_id", false, Arrays.asList("task_id"), Arrays.asList("ASC")));
        _indicesTaskTags.add(new TableInfo.Index("index_task_tags_tag_id", false, Arrays.asList("tag_id"), Arrays.asList("ASC")));
        final TableInfo _infoTaskTags = new TableInfo("task_tags", _columnsTaskTags, _foreignKeysTaskTags, _indicesTaskTags);
        final TableInfo _existingTaskTags = TableInfo.read(db, "task_tags");
        if (!_infoTaskTags.equals(_existingTaskTags)) {
          return new RoomOpenHelper.ValidationResult(false, "task_tags(com.todolist.app.data.local.entity.TaskTag).\n"
                  + " Expected:\n" + _infoTaskTags + "\n"
                  + " Found:\n" + _existingTaskTags);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "41f2e069bb084bbda60cc2b41d90d296", "a8b8bff4eb62b9d7fc40f994c6c649b7");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tasks","projects","categories","subtasks","milestones","activity_logs","tags","task_tags");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `tasks`");
      _db.execSQL("DELETE FROM `projects`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `subtasks`");
      _db.execSQL("DELETE FROM `milestones`");
      _db.execSQL("DELETE FROM `activity_logs`");
      _db.execSQL("DELETE FROM `tags`");
      _db.execSQL("DELETE FROM `task_tags`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TaskDao.class, TaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SubtaskDao.class, SubtaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProjectDao.class, ProjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ActivityLogDao.class, ActivityLogDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }

  @Override
  public SubtaskDao subtaskDao() {
    if (_subtaskDao != null) {
      return _subtaskDao;
    } else {
      synchronized(this) {
        if(_subtaskDao == null) {
          _subtaskDao = new SubtaskDao_Impl(this);
        }
        return _subtaskDao;
      }
    }
  }

  @Override
  public ProjectDao projectDao() {
    if (_projectDao != null) {
      return _projectDao;
    } else {
      synchronized(this) {
        if(_projectDao == null) {
          _projectDao = new ProjectDao_Impl(this);
        }
        return _projectDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public ActivityLogDao activityLogDao() {
    if (_activityLogDao != null) {
      return _activityLogDao;
    } else {
      synchronized(this) {
        if(_activityLogDao == null) {
          _activityLogDao = new ActivityLogDao_Impl(this);
        }
        return _activityLogDao;
      }
    }
  }
}
