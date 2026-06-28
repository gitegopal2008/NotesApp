package com.notesapp.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.notesapp.data.local.entity.NoteEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteEntity> __insertionAdapterOfNoteEntity;

  private final EntityDeletionOrUpdateAdapter<NoteEntity> __updateAdapterOfNoteEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDeleteNote;

  private final SharedSQLiteStatement __preparedStmtOfHardDeleteNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTrash;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllNotes;

  public NoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteEntity = new EntityInsertionAdapter<NoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`id`,`title`,`content`,`folder_id`,`is_favorite`,`is_archived`,`color`,`created_at`,`updated_at`,`reminder_time`,`tags_json`,`is_encrypted`,`is_deleted`,`deleted_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NoteEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getContent());
        if (entity.getFolderId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getFolderId());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.isArchived() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getColor());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        if (entity.getReminderTime() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getReminderTime());
        }
        statement.bindString(11, entity.getTagsJson());
        final int _tmp_2 = entity.isEncrypted() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final int _tmp_3 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        if (entity.getDeletedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getDeletedAt());
        }
      }
    };
    this.__updateAdapterOfNoteEntity = new EntityDeletionOrUpdateAdapter<NoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`title` = ?,`content` = ?,`folder_id` = ?,`is_favorite` = ?,`is_archived` = ?,`color` = ?,`created_at` = ?,`updated_at` = ?,`reminder_time` = ?,`tags_json` = ?,`is_encrypted` = ?,`is_deleted` = ?,`deleted_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NoteEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getContent());
        if (entity.getFolderId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getFolderId());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.isArchived() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getColor());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        if (entity.getReminderTime() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getReminderTime());
        }
        statement.bindString(11, entity.getTagsJson());
        final int _tmp_2 = entity.isEncrypted() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final int _tmp_3 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        if (entity.getDeletedAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getDeletedAt());
        }
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfSoftDeleteNote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notes SET is_deleted = 1, deleted_at = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfHardDeleteNote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notes WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllTrash = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notes WHERE is_deleted = 1";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllNotes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notes";
        return _query;
      }
    };
  }

  @Override
  public Object insertNote(final NoteEntity note, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfNoteEntity.insertAndReturnId(note);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateNote(final NoteEntity note, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNoteEntity.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDeleteNote(final long id, final long deletedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDeleteNote.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deletedAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSoftDeleteNote.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object hardDeleteNote(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHardDeleteNote.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfHardDeleteNote.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllTrash(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTrash.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllTrash.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllNotes(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllNotes.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllNotes.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NoteEntity>> getAllNotes() {
    final String _sql = "SELECT * FROM notes WHERE is_deleted = 0 AND is_archived = 0 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<NoteEntity>> getNotesByFolder(final long folderId) {
    final String _sql = "SELECT * FROM notes WHERE is_deleted = 0 AND is_archived = 0 AND folder_id = ? ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, folderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<NoteEntity>> getFavoriteNotes() {
    final String _sql = "SELECT * FROM notes WHERE is_deleted = 0 AND is_favorite = 1 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<NoteEntity>> getArchivedNotes() {
    final String _sql = "SELECT * FROM notes WHERE is_deleted = 0 AND is_archived = 1 ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<NoteEntity>> getDeletedNotes() {
    final String _sql = "SELECT * FROM notes WHERE is_deleted = 1 ORDER BY deleted_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<NoteEntity> getNoteById(final long id) {
    final String _sql = "SELECT * FROM notes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<NoteEntity>() {
      @Override
      @Nullable
      public NoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final NoteEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _result = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Object getNoteByIdOnce(final long id, final Continuation<? super NoteEntity> $completion) {
    final String _sql = "SELECT * FROM notes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NoteEntity>() {
      @Override
      @Nullable
      public NoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final NoteEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _result = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NoteEntity>> searchNotesFts(final String query) {
    final String _sql = "SELECT notes.* FROM notes JOIN notes_fts ON notes.rowid = notes_fts.rowid WHERE notes_fts MATCH ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes",
        "notes_fts"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
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
  public Object getNoteCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM notes WHERE is_deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllNotesOnce(final Continuation<? super List<NoteEntity>> $completion) {
    final String _sql = "SELECT * FROM notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_id");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminder_time");
          final int _cursorIndexOfTagsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tags_json");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_encrypted");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final Long _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getLong(_cursorIndexOfFolderId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            final String _tmpTagsJson;
            _tmpTagsJson = _cursor.getString(_cursorIndexOfTagsJson);
            final boolean _tmpIsEncrypted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp_2 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_3 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpFolderId,_tmpIsFavorite,_tmpIsArchived,_tmpColor,_tmpCreatedAt,_tmpUpdatedAt,_tmpReminderTime,_tmpTagsJson,_tmpIsEncrypted,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
