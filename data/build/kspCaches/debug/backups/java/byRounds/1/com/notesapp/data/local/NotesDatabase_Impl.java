package com.notesapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.notesapp.data.local.dao.FolderDao;
import com.notesapp.data.local.dao.FolderDao_Impl;
import com.notesapp.data.local.dao.NoteDao;
import com.notesapp.data.local.dao.NoteDao_Impl;
import com.notesapp.data.local.dao.ReminderDao;
import com.notesapp.data.local.dao.ReminderDao_Impl;
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
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NotesDatabase_Impl extends NotesDatabase {
  private volatile NoteDao _noteDao;

  private volatile FolderDao _folderDao;

  private volatile ReminderDao _reminderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `folder_id` INTEGER, `is_favorite` INTEGER NOT NULL, `is_archived` INTEGER NOT NULL, `color` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `reminder_time` INTEGER, `tags_json` TEXT NOT NULL, `is_encrypted` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `deleted_at` INTEGER, FOREIGN KEY(`folder_id`) REFERENCES `folders`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_folder_id` ON `notes` (`folder_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_is_deleted` ON `notes` (`is_deleted`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_created_at` ON `notes` (`created_at`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_updated_at` ON `notes` (`updated_at`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_reminder_time` ON `notes` (`reminder_time`)");
        db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `notes_fts` USING FTS4(`title` TEXT NOT NULL, `content` TEXT NOT NULL, content=`notes`)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_UPDATE BEFORE UPDATE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_DELETE BEFORE DELETE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_UPDATE AFTER UPDATE ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_INSERT AFTER INSERT ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
        db.execSQL("CREATE TABLE IF NOT EXISTS `folders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `parent_id` INTEGER, `color` INTEGER NOT NULL, `icon` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`parent_id`) REFERENCES `folders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_folders_parent_id` ON `folders` (`parent_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_folders_name` ON `folders` (`name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_id` INTEGER NOT NULL, `title` TEXT NOT NULL, `time` INTEGER NOT NULL, `is_completed` INTEGER NOT NULL, `repeat_interval` TEXT, FOREIGN KEY(`note_id`) REFERENCES `notes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_note_id` ON `reminders` (`note_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_time` ON `reminders` (`time`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '348134294025e65d0ab7e1565803ff4e')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `notes_fts`");
        db.execSQL("DROP TABLE IF EXISTS `folders`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
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
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_UPDATE BEFORE UPDATE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_DELETE BEFORE DELETE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_UPDATE AFTER UPDATE ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_INSERT AFTER INSERT ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(14);
        _columnsNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("folder_id", new TableInfo.Column("folder_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("is_favorite", new TableInfo.Column("is_favorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("is_archived", new TableInfo.Column("is_archived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("reminder_time", new TableInfo.Column("reminder_time", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("tags_json", new TableInfo.Column("tags_json", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("is_encrypted", new TableInfo.Column("is_encrypted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("deleted_at", new TableInfo.Column("deleted_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNotes.add(new TableInfo.ForeignKey("folders", "SET NULL", "NO ACTION", Arrays.asList("folder_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(5);
        _indicesNotes.add(new TableInfo.Index("index_notes_folder_id", false, Arrays.asList("folder_id"), Arrays.asList("ASC")));
        _indicesNotes.add(new TableInfo.Index("index_notes_is_deleted", false, Arrays.asList("is_deleted"), Arrays.asList("ASC")));
        _indicesNotes.add(new TableInfo.Index("index_notes_created_at", false, Arrays.asList("created_at"), Arrays.asList("ASC")));
        _indicesNotes.add(new TableInfo.Index("index_notes_updated_at", false, Arrays.asList("updated_at"), Arrays.asList("ASC")));
        _indicesNotes.add(new TableInfo.Index("index_notes_reminder_time", false, Arrays.asList("reminder_time"), Arrays.asList("ASC")));
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.notesapp.data.local.entity.NoteEntity).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashSet<String> _columnsNotesFts = new HashSet<String>(2);
        _columnsNotesFts.add("title");
        _columnsNotesFts.add("content");
        final FtsTableInfo _infoNotesFts = new FtsTableInfo("notes_fts", _columnsNotesFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `notes_fts` USING FTS4(`title` TEXT NOT NULL, `content` TEXT NOT NULL, content=`notes`)");
        final FtsTableInfo _existingNotesFts = FtsTableInfo.read(db, "notes_fts");
        if (!_infoNotesFts.equals(_existingNotesFts)) {
          return new RoomOpenHelper.ValidationResult(false, "notes_fts(com.notesapp.data.local.entity.NoteFtsEntity).\n"
                  + " Expected:\n" + _infoNotesFts + "\n"
                  + " Found:\n" + _existingNotesFts);
        }
        final HashMap<String, TableInfo.Column> _columnsFolders = new HashMap<String, TableInfo.Column>(7);
        _columnsFolders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("parent_id", new TableInfo.Column("parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("icon", new TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFolders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysFolders.add(new TableInfo.ForeignKey("folders", "CASCADE", "NO ACTION", Arrays.asList("parent_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesFolders = new HashSet<TableInfo.Index>(2);
        _indicesFolders.add(new TableInfo.Index("index_folders_parent_id", false, Arrays.asList("parent_id"), Arrays.asList("ASC")));
        _indicesFolders.add(new TableInfo.Index("index_folders_name", false, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoFolders = new TableInfo("folders", _columnsFolders, _foreignKeysFolders, _indicesFolders);
        final TableInfo _existingFolders = TableInfo.read(db, "folders");
        if (!_infoFolders.equals(_existingFolders)) {
          return new RoomOpenHelper.ValidationResult(false, "folders(com.notesapp.data.local.entity.FolderEntity).\n"
                  + " Expected:\n" + _infoFolders + "\n"
                  + " Found:\n" + _existingFolders);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(6);
        _columnsReminders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("time", new TableInfo.Column("time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("is_completed", new TableInfo.Column("is_completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("repeat_interval", new TableInfo.Column("repeat_interval", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysReminders.add(new TableInfo.ForeignKey("notes", "CASCADE", "NO ACTION", Arrays.asList("note_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(2);
        _indicesReminders.add(new TableInfo.Index("index_reminders_note_id", false, Arrays.asList("note_id"), Arrays.asList("ASC")));
        _indicesReminders.add(new TableInfo.Index("index_reminders_time", false, Arrays.asList("time"), Arrays.asList("ASC")));
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(com.notesapp.data.local.entity.ReminderEntity).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "348134294025e65d0ab7e1565803ff4e", "673816ab7106711cd3e42fff738ea5d7");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("notes_fts", "notes");
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "notes","notes_fts","folders","reminders");
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
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `notes_fts`");
      _db.execSQL("DELETE FROM `folders`");
      _db.execSQL("DELETE FROM `reminders`");
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
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FolderDao.class, FolderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
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
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public FolderDao folderDao() {
    if (_folderDao != null) {
      return _folderDao;
    } else {
      synchronized(this) {
        if(_folderDao == null) {
          _folderDao = new FolderDao_Impl(this);
        }
        return _folderDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }
}
