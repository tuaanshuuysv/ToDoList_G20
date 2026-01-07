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
import com.todolist.app.data.local.entity.Subtask;
import java.lang.Class;
import java.lang.Exception;
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
public final class SubtaskDao_Impl implements SubtaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Subtask> __insertionAdapterOfSubtask;

  private final EntityDeletionOrUpdateAdapter<Subtask> __deletionAdapterOfSubtask;

  private final EntityDeletionOrUpdateAdapter<Subtask> __updateAdapterOfSubtask;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSubtasksByTaskId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSubtaskStatus;

  public SubtaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubtask = new EntityInsertionAdapter<Subtask>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `subtasks` (`subtask_id`,`task_id`,`title`,`is_completed`,`position`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Subtask entity) {
        statement.bindLong(1, entity.getSubtaskId());
        statement.bindLong(2, entity.getTaskId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getPosition());
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfSubtask = new EntityDeletionOrUpdateAdapter<Subtask>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `subtasks` WHERE `subtask_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Subtask entity) {
        statement.bindLong(1, entity.getSubtaskId());
      }
    };
    this.__updateAdapterOfSubtask = new EntityDeletionOrUpdateAdapter<Subtask>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `subtasks` SET `subtask_id` = ?,`task_id` = ?,`title` = ?,`is_completed` = ?,`position` = ?,`created_at` = ? WHERE `subtask_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Subtask entity) {
        statement.bindLong(1, entity.getSubtaskId());
        statement.bindLong(2, entity.getTaskId());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getPosition());
        final Long _tmp_1 = Converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        statement.bindLong(7, entity.getSubtaskId());
      }
    };
    this.__preparedStmtOfDeleteSubtasksByTaskId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM subtasks WHERE task_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSubtaskStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE subtasks SET is_completed = ? WHERE subtask_id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insertSubtask(final Subtask subtask) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfSubtask.insertAndReturnId(subtask);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteSubtask(final Subtask subtask) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSubtask.handle(subtask);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateSubtask(final Subtask subtask) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSubtask.handle(subtask);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteSubtasksByTaskId(final long taskId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSubtasksByTaskId.acquire();
    int _argIndex = 1;
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
      __preparedStmtOfDeleteSubtasksByTaskId.release(_stmt);
    }
  }

  @Override
  public void updateSubtaskStatus(final long subtaskId, final boolean isCompleted) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSubtaskStatus.acquire();
    int _argIndex = 1;
    final int _tmp = isCompleted ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, subtaskId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateSubtaskStatus.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Subtask>> getSubtasksByTaskId(final long taskId) {
    final String _sql = "SELECT * FROM subtasks WHERE task_id = ? ORDER BY position ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"subtasks"}, false, new Callable<List<Subtask>>() {
      @Override
      @Nullable
      public List<Subtask> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSubtaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "subtask_id");
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<Subtask> _result = new ArrayList<Subtask>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Subtask _item;
            _item = new Subtask();
            final long _tmpSubtaskId;
            _tmpSubtaskId = _cursor.getLong(_cursorIndexOfSubtaskId);
            _item.setSubtaskId(_tmpSubtaskId);
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
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _item.setCompleted(_tmpIsCompleted);
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            _item.setPosition(_tmpPosition);
            final Date _tmpCreatedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = Converters.fromTimestamp(_tmp_1);
            _item.setCreatedAt(_tmpCreatedAt);
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
