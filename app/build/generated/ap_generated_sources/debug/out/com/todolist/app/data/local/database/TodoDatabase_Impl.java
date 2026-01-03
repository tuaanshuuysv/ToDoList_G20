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
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '09cf1a5c39731d5f0c72cc7e255f1846')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `tasks`");
        db.execSQL("DROP TABLE IF EXISTS `projects`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `subtasks`");
        db.execSQL("DROP TABLE IF EXISTS `milestones`");
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
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "09cf1a5c39731d5f0c72cc7e255f1846", "2cdddc503b1f187a3e511f268928595b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tasks","projects","categories","subtasks","milestones");
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
}
