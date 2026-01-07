package com.todolist.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.todolist.app.data.local.database.Converters;
import com.todolist.app.data.local.entity.Task;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCompletionStatus;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tasks` (`task_id`,`title`,`description`,`project_id`,`category_id`,`priority`,`status`,`due_date`,`reminder_time`,`estimated_duration`,`actual_duration`,`is_completed`,`completed_at`,`is_recurring`,`recurrence_rule`,`parent_recurring_task_id`,`position`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.getTaskId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getProjectId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getProjectId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCategoryId());
        }
        if (entity.getPriority() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPriority());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getStatus());
        }
        final Long _tmp = Converters.dateToTimestamp(entity.getDueDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getReminderTime());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_1);
        }
        if (entity.getEstimatedDuration() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getEstimatedDuration());
        }
        if (entity.getActualDuration() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getActualDuration());
        }
        final int _tmp_2 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final Long _tmp_3 = Converters.dateToTimestamp(entity.getCompletedAt());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_3);
        }
        final int _tmp_4 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(14, _tmp_4);
        if (entity.getRecurrenceRule() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getRecurrenceRule());
        }
        if (entity.getParentRecurringTaskId() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getParentRecurringTaskId());
        }
        statement.bindLong(17, entity.getPosition());
        final Long _tmp_5 = Converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, _tmp_5);
        }
        final Long _tmp_6 = Converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, _tmp_6);
        }
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `task_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.getTaskId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `task_id` = ?,`title` = ?,`description` = ?,`project_id` = ?,`category_id` = ?,`priority` = ?,`status` = ?,`due_date` = ?,`reminder_time` = ?,`estimated_duration` = ?,`actual_duration` = ?,`is_completed` = ?,`completed_at` = ?,`is_recurring` = ?,`recurrence_rule` = ?,`parent_recurring_task_id` = ?,`position` = ?,`created_at` = ?,`updated_at` = ? WHERE `task_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.getTaskId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getProjectId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getProjectId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCategoryId());
        }
        if (entity.getPriority() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPriority());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getStatus());
        }
        final Long _tmp = Converters.dateToTimestamp(entity.getDueDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getReminderTime());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_1);
        }
        if (entity.getEstimatedDuration() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getEstimatedDuration());
        }
        if (entity.getActualDuration() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getActualDuration());
        }
        final int _tmp_2 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final Long _tmp_3 = Converters.dateToTimestamp(entity.getCompletedAt());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_3);
        }
        final int _tmp_4 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(14, _tmp_4);
        if (entity.getRecurrenceRule() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getRecurrenceRule());
        }
        if (entity.getParentRecurringTaskId() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getParentRecurringTaskId());
        }
        statement.bindLong(17, entity.getPosition());
        final Long _tmp_5 = Converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(18);
        } else {
          statement.bindLong(18, _tmp_5);
        }
        final Long _tmp_6 = Converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, _tmp_6);
        }
        statement.bindLong(20, entity.getTaskId());
      }
    };
    this.__preparedStmtOfUpdateCompletionStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tasks SET is_completed = ?, completed_at = ? WHERE task_id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insertTask(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTask.insertAndReturnId(task);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTask(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTask(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCompletionStatus(final long taskId, final boolean completed,
      final Date completedAt) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCompletionStatus.acquire();
    int _argIndex = 1;
    final int _tmp = completed ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    final Long _tmp_1 = Converters.dateToTimestamp(completedAt);
    if (_tmp_1 == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, _tmp_1);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, taskId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateCompletionStatus.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM tasks ORDER BY position ASC, priority DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<List<Task>>() {
      @Override
      @Nullable
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "project_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfEstimatedDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "estimated_duration");
          final int _cursorIndexOfActualDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "actual_duration");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "is_recurring");
          final int _cursorIndexOfRecurrenceRule = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_rule");
          final int _cursorIndexOfParentRecurringTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parent_recurring_task_id");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            _item = new Task();
            final long _tmpTaskId;
            _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
            _item.setTaskId(_tmpTaskId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Long _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getLong(_cursorIndexOfProjectId);
            }
            _item.setProjectId(_tmpProjectId);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            _item.setCategoryId(_tmpCategoryId);
            final String _tmpPriority;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmpPriority = null;
            } else {
              _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            }
            _item.setPriority(_tmpPriority);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final Date _tmpDueDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDueDate);
            }
            _tmpDueDate = Converters.fromTimestamp(_tmp);
            _item.setDueDate(_tmpDueDate);
            final Date _tmpReminderTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = Converters.fromTimestamp(_tmp_1);
            _item.setReminderTime(_tmpReminderTime);
            final Integer _tmpEstimatedDuration;
            if (_cursor.isNull(_cursorIndexOfEstimatedDuration)) {
              _tmpEstimatedDuration = null;
            } else {
              _tmpEstimatedDuration = _cursor.getInt(_cursorIndexOfEstimatedDuration);
            }
            _item.setEstimatedDuration(_tmpEstimatedDuration);
            final Integer _tmpActualDuration;
            if (_cursor.isNull(_cursorIndexOfActualDuration)) {
              _tmpActualDuration = null;
            } else {
              _tmpActualDuration = _cursor.getInt(_cursorIndexOfActualDuration);
            }
            _item.setActualDuration(_tmpActualDuration);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            _item.setCompleted(_tmpIsCompleted);
            final Date _tmpCompletedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = Converters.fromTimestamp(_tmp_3);
            _item.setCompletedAt(_tmpCompletedAt);
            final boolean _tmpIsRecurring;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_4 != 0;
            _item.setRecurring(_tmpIsRecurring);
            final String _tmpRecurrenceRule;
            if (_cursor.isNull(_cursorIndexOfRecurrenceRule)) {
              _tmpRecurrenceRule = null;
            } else {
              _tmpRecurrenceRule = _cursor.getString(_cursorIndexOfRecurrenceRule);
            }
            _item.setRecurrenceRule(_tmpRecurrenceRule);
            final Long _tmpParentRecurringTaskId;
            if (_cursor.isNull(_cursorIndexOfParentRecurringTaskId)) {
              _tmpParentRecurringTaskId = null;
            } else {
              _tmpParentRecurringTaskId = _cursor.getLong(_cursorIndexOfParentRecurringTaskId);
            }
            _item.setParentRecurringTaskId(_tmpParentRecurringTaskId);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item.setPosition(_tmpPosition);
            final Date _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = Converters.fromTimestamp(_tmp_5);
            _item.setCreatedAt(_tmpCreatedAt);
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = Converters.fromTimestamp(_tmp_6);
            _item.setUpdatedAt(_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Task> getTaskById(final long taskId) {
    final String _sql = "SELECT * FROM tasks WHERE task_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<Task>() {
      @Override
      @Nullable
      public Task call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "project_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfEstimatedDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "estimated_duration");
          final int _cursorIndexOfActualDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "actual_duration");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "is_recurring");
          final int _cursorIndexOfRecurrenceRule = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_rule");
          final int _cursorIndexOfParentRecurringTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parent_recurring_task_id");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final Task _result;
          if (_cursor.moveToFirst()) {
            _result = new Task();
            final long _tmpTaskId;
            _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
            _result.setTaskId(_tmpTaskId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _result.setTitle(_tmpTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _result.setDescription(_tmpDescription);
            final Long _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getLong(_cursorIndexOfProjectId);
            }
            _result.setProjectId(_tmpProjectId);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            _result.setCategoryId(_tmpCategoryId);
            final String _tmpPriority;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmpPriority = null;
            } else {
              _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            }
            _result.setPriority(_tmpPriority);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _result.setStatus(_tmpStatus);
            final Date _tmpDueDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDueDate);
            }
            _tmpDueDate = Converters.fromTimestamp(_tmp);
            _result.setDueDate(_tmpDueDate);
            final Date _tmpReminderTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = Converters.fromTimestamp(_tmp_1);
            _result.setReminderTime(_tmpReminderTime);
            final Integer _tmpEstimatedDuration;
            if (_cursor.isNull(_cursorIndexOfEstimatedDuration)) {
              _tmpEstimatedDuration = null;
            } else {
              _tmpEstimatedDuration = _cursor.getInt(_cursorIndexOfEstimatedDuration);
            }
            _result.setEstimatedDuration(_tmpEstimatedDuration);
            final Integer _tmpActualDuration;
            if (_cursor.isNull(_cursorIndexOfActualDuration)) {
              _tmpActualDuration = null;
            } else {
              _tmpActualDuration = _cursor.getInt(_cursorIndexOfActualDuration);
            }
            _result.setActualDuration(_tmpActualDuration);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            _result.setCompleted(_tmpIsCompleted);
            final Date _tmpCompletedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = Converters.fromTimestamp(_tmp_3);
            _result.setCompletedAt(_tmpCompletedAt);
            final boolean _tmpIsRecurring;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_4 != 0;
            _result.setRecurring(_tmpIsRecurring);
            final String _tmpRecurrenceRule;
            if (_cursor.isNull(_cursorIndexOfRecurrenceRule)) {
              _tmpRecurrenceRule = null;
            } else {
              _tmpRecurrenceRule = _cursor.getString(_cursorIndexOfRecurrenceRule);
            }
            _result.setRecurrenceRule(_tmpRecurrenceRule);
            final Long _tmpParentRecurringTaskId;
            if (_cursor.isNull(_cursorIndexOfParentRecurringTaskId)) {
              _tmpParentRecurringTaskId = null;
            } else {
              _tmpParentRecurringTaskId = _cursor.getLong(_cursorIndexOfParentRecurringTaskId);
            }
            _result.setParentRecurringTaskId(_tmpParentRecurringTaskId);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _result.setPosition(_tmpPosition);
            final Date _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = Converters.fromTimestamp(_tmp_5);
            _result.setCreatedAt(_tmpCreatedAt);
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = Converters.fromTimestamp(_tmp_6);
            _result.setUpdatedAt(_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Task>> searchTasks(final String searchQuery) {
    final String _sql = "SELECT * FROM tasks WHERE title LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (searchQuery == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchQuery);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<List<Task>>() {
      @Override
      @Nullable
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "project_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfEstimatedDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "estimated_duration");
          final int _cursorIndexOfActualDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "actual_duration");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "is_recurring");
          final int _cursorIndexOfRecurrenceRule = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_rule");
          final int _cursorIndexOfParentRecurringTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parent_recurring_task_id");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            _item = new Task();
            final long _tmpTaskId;
            _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
            _item.setTaskId(_tmpTaskId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Long _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getLong(_cursorIndexOfProjectId);
            }
            _item.setProjectId(_tmpProjectId);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            _item.setCategoryId(_tmpCategoryId);
            final String _tmpPriority;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmpPriority = null;
            } else {
              _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            }
            _item.setPriority(_tmpPriority);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final Date _tmpDueDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDueDate);
            }
            _tmpDueDate = Converters.fromTimestamp(_tmp);
            _item.setDueDate(_tmpDueDate);
            final Date _tmpReminderTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = Converters.fromTimestamp(_tmp_1);
            _item.setReminderTime(_tmpReminderTime);
            final Integer _tmpEstimatedDuration;
            if (_cursor.isNull(_cursorIndexOfEstimatedDuration)) {
              _tmpEstimatedDuration = null;
            } else {
              _tmpEstimatedDuration = _cursor.getInt(_cursorIndexOfEstimatedDuration);
            }
            _item.setEstimatedDuration(_tmpEstimatedDuration);
            final Integer _tmpActualDuration;
            if (_cursor.isNull(_cursorIndexOfActualDuration)) {
              _tmpActualDuration = null;
            } else {
              _tmpActualDuration = _cursor.getInt(_cursorIndexOfActualDuration);
            }
            _item.setActualDuration(_tmpActualDuration);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            _item.setCompleted(_tmpIsCompleted);
            final Date _tmpCompletedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = Converters.fromTimestamp(_tmp_3);
            _item.setCompletedAt(_tmpCompletedAt);
            final boolean _tmpIsRecurring;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_4 != 0;
            _item.setRecurring(_tmpIsRecurring);
            final String _tmpRecurrenceRule;
            if (_cursor.isNull(_cursorIndexOfRecurrenceRule)) {
              _tmpRecurrenceRule = null;
            } else {
              _tmpRecurrenceRule = _cursor.getString(_cursorIndexOfRecurrenceRule);
            }
            _item.setRecurrenceRule(_tmpRecurrenceRule);
            final Long _tmpParentRecurringTaskId;
            if (_cursor.isNull(_cursorIndexOfParentRecurringTaskId)) {
              _tmpParentRecurringTaskId = null;
            } else {
              _tmpParentRecurringTaskId = _cursor.getLong(_cursorIndexOfParentRecurringTaskId);
            }
            _item.setParentRecurringTaskId(_tmpParentRecurringTaskId);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item.setPosition(_tmpPosition);
            final Date _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = Converters.fromTimestamp(_tmp_5);
            _item.setCreatedAt(_tmpCreatedAt);
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = Converters.fromTimestamp(_tmp_6);
            _item.setUpdatedAt(_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Task>> getTasksByProject(final long projectId) {
    final String _sql = "SELECT * FROM tasks WHERE project_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, projectId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tasks"}, false, new Callable<List<Task>>() {
      @Override
      @Nullable
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "project_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfEstimatedDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "estimated_duration");
          final int _cursorIndexOfActualDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "actual_duration");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completed_at");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "is_recurring");
          final int _cursorIndexOfRecurrenceRule = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_rule");
          final int _cursorIndexOfParentRecurringTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parent_recurring_task_id");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            _item = new Task();
            final long _tmpTaskId;
            _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
            _item.setTaskId(_tmpTaskId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final Long _tmpProjectId;
            if (_cursor.isNull(_cursorIndexOfProjectId)) {
              _tmpProjectId = null;
            } else {
              _tmpProjectId = _cursor.getLong(_cursorIndexOfProjectId);
            }
            _item.setProjectId(_tmpProjectId);
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            _item.setCategoryId(_tmpCategoryId);
            final String _tmpPriority;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmpPriority = null;
            } else {
              _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            }
            _item.setPriority(_tmpPriority);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final Date _tmpDueDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDueDate);
            }
            _tmpDueDate = Converters.fromTimestamp(_tmp);
            _item.setDueDate(_tmpDueDate);
            final Date _tmpReminderTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _tmpReminderTime = Converters.fromTimestamp(_tmp_1);
            _item.setReminderTime(_tmpReminderTime);
            final Integer _tmpEstimatedDuration;
            if (_cursor.isNull(_cursorIndexOfEstimatedDuration)) {
              _tmpEstimatedDuration = null;
            } else {
              _tmpEstimatedDuration = _cursor.getInt(_cursorIndexOfEstimatedDuration);
            }
            _item.setEstimatedDuration(_tmpEstimatedDuration);
            final Integer _tmpActualDuration;
            if (_cursor.isNull(_cursorIndexOfActualDuration)) {
              _tmpActualDuration = null;
            } else {
              _tmpActualDuration = _cursor.getInt(_cursorIndexOfActualDuration);
            }
            _item.setActualDuration(_tmpActualDuration);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            _item.setCompleted(_tmpIsCompleted);
            final Date _tmpCompletedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _tmpCompletedAt = Converters.fromTimestamp(_tmp_3);
            _item.setCompletedAt(_tmpCompletedAt);
            final boolean _tmpIsRecurring;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_4 != 0;
            _item.setRecurring(_tmpIsRecurring);
            final String _tmpRecurrenceRule;
            if (_cursor.isNull(_cursorIndexOfRecurrenceRule)) {
              _tmpRecurrenceRule = null;
            } else {
              _tmpRecurrenceRule = _cursor.getString(_cursorIndexOfRecurrenceRule);
            }
            _item.setRecurrenceRule(_tmpRecurrenceRule);
            final Long _tmpParentRecurringTaskId;
            if (_cursor.isNull(_cursorIndexOfParentRecurringTaskId)) {
              _tmpParentRecurringTaskId = null;
            } else {
              _tmpParentRecurringTaskId = _cursor.getLong(_cursorIndexOfParentRecurringTaskId);
            }
            _item.setParentRecurringTaskId(_tmpParentRecurringTaskId);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item.setPosition(_tmpPosition);
            final Date _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = Converters.fromTimestamp(_tmp_5);
            _item.setCreatedAt(_tmpCreatedAt);
            final Date _tmpUpdatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = Converters.fromTimestamp(_tmp_6);
            _item.setUpdatedAt(_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
