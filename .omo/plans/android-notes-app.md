# android-notes-app - Work Plan

## TL;DR (For humans)

**What you'll get:** A fully functional Android Notes app with 6 screens (note list, note editor, folders, search, settings, backup), organized into 16 Clean Architecture modules. You can create/edit/delete notes, organize them into nested folders, search across all notes with instant results, set reminders, lock the app with biometrics, and back up your data as JSON files.

**Why this approach:** Multi-module Clean Architecture with a strict dependency chain (UI → ViewModel → UseCase → Repository → Room DB) means each piece is independently testable and replaceable. Building domain first locks the app's contract, then data and UI follow without ambiguity. New features later (sync, widgets, rich text) drop in without changing existing code.

**What it will NOT do:** No cloud sync (planned for v2), no rich text formatting (plain text + markdown in v1), no OCR/PDF/voice/drawing/AI features, no widgets, no multi-platform. These are explicitly out of scope for this plan.

**Effort:** XL — ~40 source files across 16 modules, with full unit + UI test coverage
**Risk:** Medium — large surface area but each module is independently verifiable
**Decisions I made for you:** Used Hilt (DI), Room + FTS4 (database), Jetpack Navigation Compose (routing), Material 3 + Dynamic Color (theme), Kotlin Coroutines + Flow (async), Android Security Crypto (encryption), WorkManager (backup scheduling). All are industry-standard defaults. See the draft for the full decision ledger.

Your next move: **Approve this plan** to proceed, or **request changes** if any defaults need adjustment.

---

> TL;DR (machine): XL effort, Medium risk. 16 modules, ~40 source files, full Clean Architecture + MVVM Android Notes app with Room DB + FTS4, Jetpack Compose UI, Hilt DI, M3 theming, biometric lock, reminders, backup. Implemented in 5 waves with agent-executed QA.

## Scope
### Must have
- Fix all build configuration errors in existing scaffold
- Domain entities: Note, Folder, Tag, Reminder with full validation
- Room database with FTS4 for note content search
- 6 feature modules: Notes, Folders, Search, Settings, Backup, Reminders
- Security: biometric app lock + note content encryption
- Design system: M3 theming (light/dark/dynamic), reusable components
- Single-Activity architecture with Jetpack Navigation Compose
- Offline-first: Room as single source of truth
- Full unit test coverage: use cases, ViewModels, DAOs, repositories
- Compose UI tests for critical flows: note CRUD, folder navigation, search

### Must NOT have (guardrails, anti-slop, scope boundaries)
- No cloud sync, network layer, or remote data sources
- No rich text editor — plain text + Markdown formatting only
- No OCR, PDF import/export, voice notes, drawing canvas, or AI features
- No widgets, app shortcuts, or home screen integrations
- No Compose for Desktop/iOS — Android only
- No fabricated APIs or invented libraries — only use dependencies in libs.versions.toml
- No TODOs left in production code
- No copying code from other apps — all original implementation

## Verification strategy
- Test decision: **tests-after** (but every feature MUST have tests before marking todo complete)
  - Unit tests: JUnit5 + MockK + Turbine for ViewModels, UseCases
  - Room tests: @RunWith(AndroidJUnit4) on real in-memory DB
  - Compose UI tests: createComposeRule() for critical user flows
- Evidence: .omo/evidence/task-<N>-android-notes-app.md per todo

## Execution strategy
### Parallel execution waves
- **Wave 1 (Fix + Foundation):** Scaffold fix, Core module, Domain module — 3 todos, can parallelize Core + Domain
- **Wave 2 (Data + Design):** Data module (Room), Design System module — 2 todos, can parallelize
- **Wave 3 (App Shell + Navigation):** App module, Security module, Analytics module — 3 todos, Security+Analytics parallel
- **Wave 4 (Features):** Notes, Folders, Search, Settings, Backup, Reminders — 6 feature modules, can build in parallel after app shell is stable
- **Wave 5 (Testing + Polish):** Final unit tests, UI tests, verification — must come after all features

### Dependency matrix
| Todo | Depends on | Blocks | Can parallelize with |
| --- | --- | --- | --- |
| 1. Fix scaffold | — | 2-16 | — |
| 2. Domain layer | 1 | 4, 5, 6 | 3 |
| 3. Core layer | 1 | 4-17 | 2 |
| 4. Data layer | 2, 3 | 6-15 | 5 |
| 5. Design system | 1 | 6-15 | 4 |
| 6. App module | 3, 4, 5 | 8-15 | 7 |
| 7. Security module | 3 | 8-15 | 6 |
| 8. Analytics module | 3 | — | 6 |
| 9. Notes feature | 4, 5, 6 | 16, 17 | 10, 11, 12 |
| 10. Folders feature | 4, 5, 6 | 16, 17 | 9, 11, 12 |
| 11. Search feature | 4, 5, 6 | 16, 17 | 9, 10, 12 |
| 12. Settings feature | 4, 6 | 16, 17 | 9, 10, 11 |
| 13. Backup feature | 4, 6 | 16, 17 | 9-12 |
| 14. Reminders feature | 4, 6 | 16, 17 | 9-13 |
| 15. Feature integration | 9-14 | 16 | — |
| 16. Unit tests | 9-15 | 17 | — |
| 17. UI tests | 15 | — | — |

## Todos

<!-- APPEND TASK BATCHES BELOW THIS LINE WITH edit/apply_patch - never rewrite the headers above. -->

### WAVE 1: Foundation Fix + Core + Domain

- [x] 1. **Fix scaffold build configuration**
  **What to do:**
  1. Fix `settings.gradle.kts` line 9: change `dependencyResolution` → `dependencyResolutionManagement`
  2. Create `app/build.gradle.kts` — application plugin, compose enabled, Hilt + KSP, depends on :core :domain :data :designsystem :security :features:notes :features:folders :features:search :features:settings :features:backup :features:reminders
  3. Create `app/src/main/AndroidManifest.xml` — package com.notesapp, launcher activity, biometric permission, notification permission, backup agent
  4. Create `features/reminders/build.gradle.kts` — library plugin, compose, Hilt, depends on :domain :core :designsystem
  5. Fix version catalog: rename `-androidx-core-ktx` and `-androidx-activity-compose` — remove leading dashes (valid TOML but may cause issues)
  6. Create missing `app/src/main/res/values/strings.xml`, `themes.xml`
  7. Create `gradle/wrapper/gradle-wrapper.properties` with Gradle 8.5
  **Must NOT do:** Don't upgrade dependency versions unless they break compilation; don't change the module structure
  **Parallelization:** Wave 1 | Blocked by: — | Blocks: 2-17
  **References:**
  - NotesApp/settings.gradle.kts:9 (dependencyResolution typo)
  - NotesApp/gradle/libs.versions.toml (version catalog)
  - NotesApp/features/reminders/ (missing build.gradle.kts)
  - NotesApp/features/notes/note/build.gradle.kts (unregistered module)
  - app/build.gradle.kts pattern from features/notes/build.gradle.kts:1-56
  - AndroidManifest.xml standard structure (package=com.notesapp)
  **Acceptance criteria:** `./gradlew :app:dependencies` runs without error; `./gradlew assembleDebug` compiles (may have empty source warnings)
  **QA scenarios:**
  - Happy: `./gradlew :app:dependencies --configuration debugRuntimeClasspath` succeeds
  - Failure: `./gradlew assembleDebug` should succeed (empty source dirs are fine)
  - Evidence: .omo/evidence/task-1-scaffold-output.txt
  **Commit:** Y | `fix(build): Repair scaffold configuration — fix dependencyResolution typo, add app/build.gradle.kts, AndroidManifest, wrapper, reminders module`

- [x] 2. **Create domain layer — entities, repository interfaces, use cases**
  **What to do:** Create all files under `domain/src/main/java/com/notesapp/domain/`
  **Entities (model/):**
  - `Note.kt` — data class: id (Long, default 0), title (String), content (String), folderId (Long?, nullable = null for root), isFavorite (Boolean = false), isArchived (Boolean = false), color (Long = 0xFFFFFFFF), createdAt (Long = System.currentTimeMillis), updatedAt (Long = createdAt), reminderTime (Long?, nullable), tags (List<String> = emptyList()), isEncrypted (Boolean = false), deletedAt (Long? = null for soft delete). Companion with EMPTY placeholder.
  - `Folder.kt` — data class: id (Long = 0), name (String), parentId (Long? = null for root folders), color (Long = 0xFF8B5CF6), icon (String? = null), noteCount (Int = 0, transient/calculated), createdAt (Long), updatedAt (Long)
  - `Tag.kt` — data class: id (Long = 0), name (String), color (Long)
  - `Reminder.kt` — data class: id (Long = 0), noteId (Long), title (String), time (Long), isCompleted (Boolean = false), repeatInterval (String? = null — "NONE", "DAILY", "WEEKLY", "MONTHLY")
  - `NoteFilter.kt` — sealed class: All, ByFolder(folderId: Long), ByTag(tag: String), Favorites, Archived, Trash, Search(query: String)
  - `SortOrder.kt` — enum: UPDATED_DESC (default), UPDATED_ASC, CREATED_DESC, CREATED_ASC, TITLE_ASC, TITLE_DESC
  - `ThemeMode.kt` — enum: SYSTEM, LIGHT, DARK
  - `BackupInfo.kt` — data class: lastBackupTime (Long?), backupCount (Int), autoBackup (Boolean)
  **Repository interfaces (repository/):**
  - `NoteRepository.kt` — interface: fun getNotesStream(filter: NoteFilter, sort: SortOrder): Flow<List<Note>>; suspend fun getNoteById(id: Long): Note?; suspend fun insertNote(note: Note): Long; suspend fun updateNote(note: Note); suspend fun deleteNote(note: Note); suspend fun softDeleteNote(note: Note); suspend fun searchNotes(query: String): Flow<List<Note>>; suspend fun getNoteCount(): Int; fun getNoteByIdFlow(id: Long): Flow<Note?>
  - `FolderRepository.kt` — interface: fun getFoldersStream(): Flow<List<Folder>>; suspend fun getFolderById(id: Long): Folder?; suspend fun createFolder(name: String, parentId: Long?): Long; suspend fun updateFolder(folder: Folder); suspend fun deleteFolder(folder: Folder); fun getNoteCountForFolder(folderId: Long): Flow<Int>
  - `ReminderRepository.kt` — interface: fun getUpcomingRemindersStream(): Flow<List<Reminder>>; suspend fun setReminder(reminder: Reminder): Long; suspend fun cancelReminder(reminderId: Long); suspend fun completeReminder(reminderId: Long)
  - `UserPreferencesRepository.kt` — interface: val themeMode: Flow<ThemeMode>; val isDynamicColorEnabled: Flow<Boolean>; val isBiometricEnabled: Flow<Boolean>; val defaultSortOrder: Flow<SortOrder>; val backupInfo: Flow<BackupInfo>; suspend fun setThemeMode(mode: ThemeMode); suspend fun setDynamicColor(enabled: Boolean); suspend fun setBiometricEnabled(enabled: Boolean); suspend fun setDefaultSortOrder(order: SortOrder); suspend fun updateBackupInfo(info: BackupInfo)
  **Use cases (usecase/):**
  - `GetNotesUseCase.kt` — operator fun invoke(filter: NoteFilter, sort: SortOrder): Flow<List<Note>>
  - `SearchNotesUseCase.kt` — operator fun invoke(query: String): Flow<List<Note>> (debounced)
  - `GetNoteByIdUseCase.kt` — operator fun invoke(id: Long): Flow<Note?>
  - `SaveNoteUseCase.kt` — operator fun invoke(note: Note): Result<Long> (validates title not blank)
  - `DeleteNoteUseCase.kt` — operator fun invoke(note: Note) — soft delete
  - `GetFoldersUseCase.kt` — operator fun invoke(): Flow<List<Folder>>
  - `CreateFolderUseCase.kt` — operator fun invoke(name: String, parentId: Long?): Result<Long>
  - `UpdateFolderUseCase.kt` — operator fun invoke(folder: Folder): Result<Unit>
  - `DeleteFolderUseCase.kt` — operator fun invoke(folder: Folder): Result<Unit>
  - `GetUpcomingRemindersUseCase.kt` — operator fun invoke(): Flow<List<Reminder>>
  - `SetReminderUseCase.kt` — operator fun invoke(noteId: Long, time: Long, title: String): Result<Long>
  - `CancelReminderUseCase.kt` — operator fun invoke(reminderId: Long)
  - `ManageBackupUseCase.kt` — operator fun invoke(notes: List<Note>, folders: List<Folder>): BackupInfo
  **Must NOT do:** No Android imports in domain layer (pure Kotlin); no Room/Compose references; no implementation logic in use cases (delegate to repositories)
  **Parallelization:** Wave 1 | Blocked by: 1 | Blocks: 4, 9-15
  **References:**
  - domain/build.gradle.kts:26-36 (dependencies — :core, Hilt, Coroutines)
  - Clean Architecture domain layer conventions
  **Acceptance criteria:** All files compile with `./gradlew :domain:compileDebugKotlin`
  **QA scenarios:**
  - Happy: `./gradlew :domain:compileDebugKotlin` succeeds
  - Failure: Introduce a compile error (remove import), verify it fails
  - Evidence: .omo/evidence/task-2-domain-compile.txt
  **Commit:** Y | `feat(domain): Add entities, repository interfaces, and use cases for Notes app domain layer`

- [x] 3. **Create core layer — DI, base classes, extensions, error handling**
  **What to do:** Create all files under `core/src/main/java/com/notesapp/core/`
  **DI (di/):**
  - `AppModule.kt` — @Module @InstallIn(SingletonComponent::class) — provides Application context, Gson if needed. Empty providers that data layer fills.
  - `DatabaseModule.kt` — @Module @InstallIn(SingletonComponent::class) — provides Room database, DAOs (filled in data layer)
  - `RepositoryModule.kt` — @Module @InstallIn(SingletonComponent::class) — binds repository interfaces to implementations
  - `DispatchersModule.kt` — @Module providing @IoDispatcher, @MainDispatcher, @DefaultDispatcher CoroutineDispatcher qualifiers
  **Utils (util/):**
  - `DateTimeUtils.kt` — object: formatRelativeTime(timestamp: Long): String, formatDate(timestamp: Long): String, formatTime(timestamp: Long): String, isToday(timestamp: Long): Boolean, formatDateTime(timestamp: Long): String — using java.time (API 26+, minSdk 26 allows this)
  - `Constants.kt` — object: DATABASE_NAME = "notes_app_db", BACKUP_DIR = "NotesAppBackup", PREFS_NAME = "notes_app_prefs", NOTE_CONTENT_FTS_TABLE = "notes_fts", DEFAULT_PAGE_SIZE = 50
  - `ValidationUtils.kt` — object: isValidNoteTitle(title: String): Boolean (not blank, max 200 chars), isValidFolderName(name: String): Boolean (not blank, max 50 chars), isValidEmail(email: String): Boolean
  **Result wrapper (util/):** (can also be in domain)
  - `Resource.kt` — sealed class Resource<out T> { data class Success<T>(val data: T) : Resource<T>(); data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>(); data object Loading : Resource<Nothing>() }
  - In domain layer pack: `com.notesapp.domain.util.Resource.kt`
  **Extensions (extension/):**
  - `ContextExtensions.kt` — fun Context.showToast(message: String); val Context.screenSizeDp: Pair<Int, Int>
  - `DateExtensions.kt` — fun Long.toRelativeTime(): String; fun Long.toFormattedDate(): String; fun Long.toFormattedTime(): String
  - `FlowExtensions.kt` — fun <T> Flow<T>.withResource(): Flow<Resource<T>> (maps to Loading/Success/Error); fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T>
  - `StringExtensions.kt` — fun String.isValidEmail(): Boolean; fun String.truncate(maxLength: Int): String; fun String.toSearchableText(): String (normalize for FTS)
  **Logging (logging/):**
  - `Logger.kt` — interface Logger + `AndroidLogger` implementation using android.util.Log; fun d(tag: String, msg: String); fun e(tag: String, msg: String, throwable: Throwable? = null); fun i(tag: String, msg: String). Tagged by class.simpleName.
  - `LoggerModule.kt` — @Binds Logger to AndroidLogger
  **Must NOT do:** No feature-specific code in core; no Android dependencies except Context, Log; no business logic
  **Parallelization:** Wave 1 | Blocked by: 1 | Blocks: 4-17 | Can parallelize with: 2
  **References:**
  - core/build.gradle.kts:26-31 (dependencies)
  - Hilt @Module, @InstallIn patterns
  - Java.time API (minSdk 26)
  **Acceptance criteria:** `./gradlew :core:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: `./gradlew :core:compileDebugKotlin`
  - Evidence: .omo/evidence/task-3-core-compile.txt
  **Commit:** Y | `feat(core): Add DI modules, utility classes, extensions, logging, and Resource sealed class`

### WAVE 2: Data Layer + Design System

- [x] 4. **Create data layer — Room database, DAOs, repository implementations**
  **What to do:** Create files under `data/src/main/java/com/notesapp/data/`
  **Database (local/):**
  - `NotesDatabase.kt` — @Database(entities: [NoteEntity, FolderEntity, TagEntity, ReminderEntity], version: 1, exportSchema: true). Abstract class extending RoomDatabase. Has abstract DAOs: noteDao(), folderDao(), reminderDao(). Companion with MIGRATION_1_2 if needed. Create FTS4 entity `NoteFtsEntity` with @Fts4(contentEntity = NoteEntity::class) — columns: title, content.
  - `Converters.kt` — @TypeConverter for List<String> ↔ String (JSON array), Long? ↔ Long (nullable timestamps), Boolean ↔ Int. Extra: ColorLong ↔ Long (already Long, just alias).
  **Entities (local/entity/):**
  - `NoteEntity.kt` — @Entity(tableName = "notes") with same fields as domain Note. @PrimaryKey(autoGenerate = true) id: Long. ColumnInfo names snake_case. ForeignKey to folders table on folderId (SET_NULL on delete). Indices: (folderId), (isDeleted), (createdAt), (updatedAt), (reminderTime). @ColumnInfo(name = "is_deleted") for soft delete flag.
  - `FolderEntity.kt` — @Entity(tableName = "folders"). Fields: id (PK auto), name, parentId (nullable, self-referencing FK), color (default), icon, createdAt, updatedAt. Indices: (parentId), (name).
  - `TagEntity.kt` — @Entity(tableName = "tags"). Fields: id (PK auto), name (unique), color.
  - `ReminderEntity.kt` — @Entity(tableName = "reminders"). Fields: id (PK auto), noteId (FK to notes, CASCADE delete), title, time (Long), isCompleted (Boolean), repeatInterval (String).
  **DAO interfaces (local/dao/):**
  - `NoteDao.kt` — @Dao:
    - @Query getAllNotes(orderBy: String): Flow<List<NoteEntity>>
    - @Query getNotesByFolder(folderId: Long, orderBy: String): Flow<List<NoteEntity>>
    - @Query getFavoriteNotes(orderBy: String): Flow<List<NoteEntity>>
    - @Query getArchivedNotes(orderBy: String): Flow<List<NoteEntity>>
    - @Query getDeletedNotes(): Flow<List<NoteEntity>>
    - @Query getNoteById(id: Long): NoteEntity?
    - @Query getNoteByIdFlow(id: Long): Flow<NoteEntity?>
    - @Insert(onConflict = REPLACE) insertNote(note: NoteEntity): Long
    - @Update updateNote(note: NoteEntity)
    - @Query soft-delete and permanent delete methods
    - @Query searchNotesFts(query: String): Flow<List<NoteEntity>> — using FTS4 MATCH
    - @RawQuery for dynamic sort order
  - `FolderDao.kt` — @Dao: getAllFolders(): Flow<List<FolderEntity>>; getFolderById(id: Long): FolderEntity?; insertFolder(folder: FolderEntity): Long; updateFolder(folder: FolderEntity); deleteFolder(folder: FolderEntity); getNoteCount(folderId: Long): Flow<Int>
  - `ReminderDao.kt` — @Dao: getUpcomingReminders(currentTime: Long): Flow<List<ReminderEntity>>; getRemindersForNote(noteId: Long): Flow<List<ReminderEntity>>; insertReminder(reminder: ReminderEntity): Long; updateReminder(reminder: ReminderEntity); deleteReminder(reminderId: Long)
  **Mappers (local/):**
  - `NoteMapper.kt` — fun NoteEntity.toDomain(): Note; fun Note.toEntity(): NoteEntity
  - `FolderMapper.kt` — similarly
  - `ReminderMapper.kt` — similarly
  **Repository implementations (repository/):**
  - `NoteRepositoryImpl.kt` — @Singleton @Inject constructor(private val dao: NoteDao, private val dispatchers: @IoDispatcher CoroutineDispatcher). Implements NoteRepository. Maps entities ↔ domain, runs queries on IO dispatcher. For search, uses FTS4 MATCH.
  - `FolderRepositoryImpl.kt` — @Singleton, implements FolderRepository
  - `ReminderRepositoryImpl.kt` — @Singleton, implements ReminderRepository
  - `UserPreferencesRepositoryImpl.kt` — @Singleton, uses DataStore<Preferences>. Saves/loads theme mode, dynamic color, biometric enabled, sort order, backup info as Int/String keys.
  **DatabaseModule fill-in:**
  - Update core's DatabaseModule (or recreate in data layer with @Module including Provides for NotesDatabase, NoteDao, FolderDao, ReminderDao)
  - Should be in data layer: create `data/di/DatabaseModule.kt` providing database and DAOs
  **Must NOT do:** No UI or ViewModel code in data layer; no business logic in DAOs; don't use LiveData (use Flow); don't expose entities directly beyond ViewModels (map to domain)
  **Parallelization:** Wave 2 | Blocked by: 2, 3 | Blocks: 9-15 | Can parallelize with: 5
  **References:**
  - data/build.gradle.kts:26-49 (Room, DataStore, Hilt dependencies)
  - Room FTS4 documentation
  - https://developer.android.com/training/data-storage/room/accessing-data#query-observable
  **Acceptance criteria:** `./gradlew :data:compileDebugKotlin` succeeds; Room schema exported to app/schemas/
  **QA scenarios:**
  - Happy: compile succeeds
  - Unit test: create in-memory NotesDatabase, insert a note, query it back, verify equality
  - Evidence: .omo/evidence/task-4-data-compile.txt
  **Commit:** Y | `feat(data): Add Room database with FTS4, DAOs, repository implementations, DataStore preferences`

- [x] 5. **Create design system — Material 3 theme, reusable components**
  **What to do:** Create files under `designsystem/src/main/java/com/notesapp/designsystem/`
  **Theme (theme/):**
  - `Theme.kt` — @Composable NotesAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), dynamicColor: Boolean = true, content: @Composable () -> Unit). Uses dynamicColor on Android 12+ (Monet). Falls back to seed color (blue) for lower versions. Wraps MaterialTheme with custom colors and typography.
  - `Color.kt` — light/dark color schemes using static seed colors as fallback. Primary: Blue (#1976D2), Secondary: Teal (#00897B), Tertiary: Amber (#FFA000). Define surface variants, outline, error colors.
  - `Type.kt` — Typography using system fonts (Roboto on pre-Android 12, Google Sans on 12+). Define headline, title, body, label sizes. bodyLarge for note content, titleMedium for note titles, labelSmall for timestamps.
  - `Shape.kt` — RoundedCornerShape(12.dp) for cards, 8.dp for dialogs, 16.dp for bottom sheets, 24.dp for FAB.
  - `Dimens.kt` — object with spacing values (4, 8, 12, 16, 20, 24, 32, 48, 64), card elevation (2.dp), FAB size (56.dp), icon size (24.dp).
  **Components (component/):**
  - `NotesTopBar.kt` — @Composable NotesTopBar(title: String, onBackClick: (() -> Unit)? = null, actions: @Composable RowScope.() -> Unit = {}). Material 3 top app bar.
  - `NotesSearchBar.kt` — @Composable NotesSearchBar(query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit, active: Boolean, onActiveChange: (Boolean) -> Unit). Docked search bar with DismissSearchBar.
  - `NoteCard.kt` — @Composable NoteCard(note: Note, onClick: () -> Unit, onLongClick: (() -> Unit)? = null). ElevatedCard with title (max 2 lines), content preview (max 3 lines), timestamp, folder indicator chip, reminder icon if set, favorite star.
  - `FolderCard.kt` — @Composable FolderCard(folder: Folder, noteCount: Int, onClick: () -> Unit, onLongClick: (() -> Unit)? = null). Card with folder icon, name, count, color indicator.
  - `EmptyState.kt` — @Composable EmptyState(icon: ImageVector, title: String, subtitle: String? = null, action: (@Composable () -> Unit)? = null). Centered column for empty lists.
  - `LoadingIndicator.kt` — @Composable LoadingIndicator(modifier: Modifier). Centered CircularProgressIndicator.
  - `ErrorView.kt` — @Composable ErrorView(message: String, onRetry: () -> Unit). Error icon + message + retry button.
  - `ConfirmDialog.kt` — @Composable ConfirmDialog(title: String, message: String, confirmText: String = "Delete", onConfirm: () -> Unit, onDismiss: () -> Unit). AlertDialog.
  - `NoteColorPicker.kt` — @Composable NoteColorPicker(selectedColor: Long, onColorSelected: (Long) -> Unit). Row of color circles.
  - `SortDropdown.kt` — @Composable SortDropdown(selected: SortOrder, onSelected: (SortOrder) -> Unit). Dropdown menu for sort options.
  - `NoteGridView.kt` — @Composable NoteGridView(notes: List<Note>, onNoteClick: (Long) -> Unit). LazyVerticalGrid(columns = 2) for tablet.
  - `NoteListView.kt` — @Composable NoteListView(notes: List<Note>, onNoteClick: (Long) -> Unit). LazyColumn for phone.
  **Animation (animation/):**
  - Default compose animations: fadeIn + slideInVertically for list items, shared element transitions for note detail (future)
  **Must NOT do:** No feature-specific UI here; no business logic in design system; no ViewModel references; keep components stateless (receive data as parameters)
  **Parallelization:** Wave 2 | Blocked by: 1 | Blocks: 9-15 | Can parallelize with: 4
  **References:**
  - designsystem/build.gradle.kts:1-40 (Compose BOM, M3, icons)
  - Material 3 documentation
  **Acceptance criteria:** `./gradlew :designsystem:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile success
  - Evidence: .omo/evidence/task-5-designsystem-compile.txt
  **Commit:** Y | `feat(designsystem): Add Material 3 theme, reusable components (NoteCard, FolderCard, EmptyState, etc.)`

### WAVE 3: App Shell + Security + Analytics

- [x] 6. **Create app module — Application class, Hilt, Navigation, MainActivity**
  **What to do:** Create files under `app/src/main/java/com/notesapp/app/`
  **Application class:**
  - `NotesApplication.kt` — @HiltAndroidApp class NotesApplication : Application(). Override onCreate for initialization (WorkManager, analytics init if any).
  - Add to AndroidManifest.xml: android:name=".app.NotesApplication"
  **Navigation (navigation/):**
  - `Screen.kt` — sealed class Screen(val route: String, val title: String):
    - Notes(route = "notes", title = "Notes")
    - NoteDetail(route = "note/{noteId}", title = "Note") — noteId: Long nav argument
    - Folders(route = "folders", title = "Folders")
    - Search(route = "search", title = "Search")
    - Settings(route = "settings", title = "Settings")
    - Backup(route = "backup", title = "Backup")
  - `NavGraph.kt` — @Composable fun NotesNavGraph(navController: NavHostController). NavHost with:
    - composable(Screen.Notes) → NotesScreen(navController)
    - composable(Screen.NoteDetail + "/{noteId}") → NoteDetailScreen(noteId, navController)
    - composable(Screen.Folders) → FoldersScreen(navController)
    - composable(Screen.Search) → SearchScreen(navController)
    - composable(Screen.Settings) → SettingsScreen(navController)
    - composable(Screen.Backup) → BackupScreen(navController)
  - `NavigationActions.kt` — class NavActions(private val navController: NavHostController): fun navigateToNoteDetail(noteId: Long); fun navigateToFolders(); fun navigateToSearch(); fun navigateToSettings(); fun navigateToBackup(); fun goBack(); fun popUpToTop()
  **MainActivity:**
  - `MainActivity.kt` — @AndroidEntryPoint class MainActivity : ComponentActivity(). override fun onCreate: setContent { NotesAppTheme { val navController = rememberNavController(); Scaffold + NavGraph } }
  **Scaffold composable:**
  - `NotesScaffold.kt` — @Composable fun NotesScaffold(navController: NavHostController, currentRoute: String?). Has BottomNavigationBar with: Notes, Folders, Search, Settings. Shows/hides based on route. For note detail route, content is full-screen without bottom nav.
  - Bottom bar: NavigationBar with NavigationBarItem for each screen. Uses Material 3 icons: Notes→lightbulb, Folders→folder, Search→search, Settings→settings. Highlights current route.
  **Must NOT do:** No business logic in app module; no direct DB access; app module wires things together only
  **Parallelization:** Wave 3 | Blocked by: 4, 5 | Blocks: 9-15 | Can parallelize with: 7
  **References:**
  - Jetpack Navigation Compose patterns
  - app/build.gradle.kts (needs activity-compose, navigation-compose, hilt-navigation-compose)
  **Acceptance criteria:** `./gradlew :app:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile success
  - Evidence: .omo/evidence/task-6-app-compile.txt
  **Commit:** Y | `feat(app): Add Application class, Hilt setup, navigation graph, MainActivity with bottom nav`

- [x] 7. **Create security module — biometric lock, encryption**
  **What to do:** Create files under `security/src/main/java/com/notesapp/security/`
  **Encryption:**
  - `EncryptionManager.kt` — @Singleton @Inject constructor. Uses EncryptedFile from Android Security Crypto. Methods: encryptString(plaintext: String): String, decryptString(ciphertext: String): String. Uses MasterKey with AES256_GCM. For file-level: encryptNoteContent(content: String): String, decryptNoteContent(content: String): String.
  **Biometric:**
  - `BiometricAuthManager.kt` — @Singleton @Inject constructor. Uses BiometricPrompt. Methods: isBiometricAvailable(): Boolean, authenticate(activity: FragmentActivity, onSuccess: () -> Unit, onError: (String) -> Unit). Checks for hardware, enrolled biometrics.
  - `AppLockManager.kt` — @Singleton @Inject constructor (preferencesRepo: UserPreferencesRepository). Methods: isAppLockEnabled(): Flow<Boolean>; fun lockApp(); fun unlockApp(); val isAppLocked: Flow<Boolean>. Uses DataStore from core.
  **Must NOT do:** Don't roll custom crypto — use Android Security Crypto (EncryptedFile/EncryptedSharedPreferences); don't store master key in insecure places
  **Parallelization:** Wave 3 | Blocked by: 3 | Blocks: — | Can parallelize with: 6
  **References:**
  - security/build.gradle.kts:26-35 (biometric, security-crypto deps)
  - Android Security Crypto: https://developer.android.com/privacy-and-security/security-crypto
  **Acceptance criteria:** `./gradlew :security:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile success
  - Evidence: .omo/evidence/task-7-security-compile.txt
  **Commit:** Y | `feat(security): Add biometric authentication manager and encryption manager`

- [x] 8. **Create analytics module — event tracking abstraction**
  **What to do:** Create files under `analytics/src/main/java/com/notesapp/analytics/`
  **Trackable (model/):**
  - `AnalyticsEvent.kt` — data class AnalyticsEvent(name: String, params: Map<String, String> = emptyMap())
  - `AnalyticsScreen.kt` — sealed class: NoteList, NoteDetail(noteId), FoldersList, Search, Settings, Backup
  **Tracker:**
  - `AnalyticsTracker.kt` — interface: fun logEvent(event: AnalyticsEvent); fun logScreenView(screen: AnalyticsScreen); fun setUserId(id: String); fun setUserProperty(key: String, value: String)
  - `NoOpAnalyticsTracker.kt` — @Singleton implementation that logs nothing (default for v1, can be swapped for Firebase later)
  **Must NOT do:** No Firebase dependency in v1; no personal data in event params
  **Parallelization:** Wave 3 | Blocked by: 3 | Blocks: — | Can parallelize with: 6
  **References:**
  - analytics/build.gradle.kts:26-31 (core, Hilt, Coroutines)
  **Acceptance criteria:** `./gradlew :analytics:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile success
  - Evidence: .omo/evidence/task-8-analytics-compile.txt
  **Commit:** Y | `feat(analytics): Add analytics event abstraction with NoOp implementation`

### WAVE 4: Feature Modules

- [x] 9. **Create Notes feature — list and detail/edit screens**
  **What to do:** Create files under `features/notes/src/main/java/com/notesapp/features/notes/`
  **Note list (list/):**
  - `NotesViewModel.kt` — @HiltViewModel. Injected: GetNotesUseCase, DeleteNoteUseCase, GetFoldersUseCase, SearchNotesUseCase, UserPreferencesRepository. StateFlow<NotesUiState> with: notes: List<Note>, isLoading, error, currentFilter: NoteFilter, currentSort: SortOrder, viewMode: ListViewMode (LIST/GRID), searchQuery. Functions: loadNotes(), onSearchQueryChanged(query), onFilterSelected(filter), onSortChanged(order), toggleViewMode(), onDeleteNote(note), onNoteClick(id).
  - `NotesUiState.kt` — data class NotesUiState(notes, isLoading, error, currentFilter, sortOrder, viewMode, searchQuery, selectedFolder)
  - `NotesScreen.kt` — @Composable. Observes ViewModel state. Displays:
    - NotesTopBar with search icon → toggles search bar
    - NotesSearchBar when active
    - SortDropdown + ViewMode toggle in top bar
    - Folder filter chips (horizontal scroll)
    - Content: NoteListView or NoteGridView based on viewMode
    - FAB → navigates to new note
    - EmptyState when notes is empty
    - LoadingIndicator during load
    - ErrorView on error
    - Pull-to-refresh via pullRefresh modifier + indicator
  - `NotesContract.kt` — sealed interface NotesEvent (load, search, filter, sort, toggleView, delete, clickNote)
  **Note detail/edit (detail/):**
  - `NoteDetailViewModel.kt` — @HiltViewModel. Injected: GetNoteByIdUseCase, SaveNoteUseCase, DeleteNoteUseCase, SetReminderUseCase. UiState: note: Note?, isLoading, isSaving, isNewNote. Functions: loadNote(id), saveNote(title, content, folderId, color), deleteNote(), setReminder(time), toggleFavorite(), archiveNote().
  - `NoteDetailUiState.kt` — data class NoteDetailUiState(note, isLoading, isSaving, isNewNote, hasUnsavedChanges)
  - `NoteDetailScreen.kt` — @Composable. Observes ViewModel. Displays:
    - Editor: OutlinedTextField for title (large, bold), OutlinedTextField for content (multiline, monospace for markdown, fill max height)
    - TopBar with: back, save, undo/redo (stretch), more menu (delete, archive, favorite, set reminder)
    - Bottom bar: folder selector, color picker, reminder button
    - Auto-save with 2-second debounce
    - Confirmation dialog on back if unsaved changes
    - Delete confirmation dialog
    - Reminder date/time picker dialog
  **Must NOT do:** No direct DB calls in ViewModel (use use cases); no business logic in composables
  **Parallelization:** Wave 4 | Blocked by: 4, 5, 6 | Blocks: | Can parallelize with: 10, 11, 12, 13, 14
  **References:**
  - features/notes/build.gradle.kts:1-56 (deps)
  - Domain use cases (GetNotesUseCase, SaveNoteUseCase, etc.)
  - Design system components
  **Acceptance criteria:** `./gradlew :features:notes:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile success, then verify navigation from notes list to detail and back
  - Mock test: ViewModel test — given repository returns notes, assert UI state has notes
  - Evidence: .omo/evidence/task-9-notes-compile.txt
  **Commit:** Y | `feat(notes): Add Notes feature — list screen with search/filter/sort and detail/edit screen with auto-save`

- [x] 10. **Create Folders feature — folder management and navigation**
  **What to do:** Create files under `features/folders/src/main/java/com/notesapp/features/folders/`
  **FoldersViewModel:**
  - Injected: GetFoldersUseCase, CreateFolderUseCase, UpdateFolderUseCase, DeleteFolderUseCase, GetNotesUseCase
  - StateFlow<FoldersUiState>: folders: List<Folder>, selectedFolder: Folder?, notesInFolder: List<Note>, isLoading, error, showCreateDialog
  - Functions: loadFolders(), selectFolder(folder), createFolder(name, parentId), renameFolder(folder, newName), deleteFolder(folder), getNotesForFolder(folderId)
  **FoldersScreen:**
  - Composable tree: LazyColumn of folder items with indentation for nested folders
  - Each item: Card with folder icon, name, note count, color strip on left
  - Long press → context menu: Rename, Delete
  - FAB → create folder dialog
  - Expandable/collapsible for nested folders
  - Click → filter notes list by this folder (navigate or update filter)
  - Empty state: "Create your first folder"
  - Confirm dialog for delete (warns about notes inside)
  **Must NOT do:** No circular folder references; no moving notes between modules (use repository)
  **Parallelization:** Wave 4 | Blocked by: 4, 5, 6 | Can parallelize with: 9, 11, 12, 13, 14
  **References:**
  - features/folders/build.gradle.kts:1-39
  - Domain use cases for folders
  **Acceptance criteria:** `./gradlew :features:folders:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile, mock test folder CRUD
  - Evidence: .omo/evidence/task-10-folders-compile.txt
  **Commit:** Y | `feat(folders): Add Folders feature — CRUD, nested folders, folder content view`

- [x] 11. **Create Search feature — global full-text search**
  **What to do:** Create files under `features/search/src/main/java/com/notesapp/features/search/`
  **SearchViewModel:**
  - Injected: SearchNotesUseCase, GetFoldersUseCase
  - StateFlow<SearchUiState>: query, results: List<Note>, isSearching, recentSearches: List<String>, selectedFolderFilter: Long?, sortBy
  - Functions: onQueryChanged(query: String) — debounced 300ms, search(), clearSearch(), selectRecentSearch(query), filterByFolder(folderId), clearFolderFilter()
  **SearchScreen:**
  - Composable: Initial state shows recent searches as chips, empty state
  - When typing: LazyColumn of NoteCards matching query
  - Results grouped by relevance (FTS match score if available, or updatedAt)
  - Filter chips at top: folder filter, sort order
  - Highlight matched text in title and content snippets
  - Click note → navigate to NoteDetail
  - Pull-to-refresh for re-search
  **Must NOT do:** No remote search (local only); no regex search (basic FTS)
  **Parallelization:** Wave 4 | Blocked by: 4, 5, 6 | Can parallelize with: 9, 10, 12, 13, 14
  **References:**
  - features/search/build.gradle.kts:1-40
  - SearchNotesUseCase from domain
  - Room FTS4 MATCH syntax
  **Acceptance criteria:** `./gradlew :features:search:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile, ViewModel test with debounced search
  - Evidence: .omo/evidence/task-11-search-compile.txt
  **Commit:** Y | `feat(search): Add global FTS search feature with debounce, filters, recent searches`

- [x] 12. **Create Settings feature — theme, preferences, about**
  **What to do:** Create files under `features/settings/src/main/java/com/notesapp/features/settings/`
  **SettingsViewModel:**
  - Injected: UserPreferencesRepository, BiometricAuthManager (from security module only if available — optional dependency)
  - StateFlow<SettingsUiState>: themeMode, isDynamicColor, isBiometricEnabled, appVersion, backupInfo
  - Functions: setThemeMode(mode), toggleDynamicColor(), toggleBiometricLock()
  **SettingsScreen:**
  - Composable: LazyColumn of preference groups:
    - Appearance: ThemeModeSelector (System/Light/Dark radio), DynamicColor toggle
    - Security: Biometric lock toggle (if available)
    - Data: Backup → navigate to Backup screen
    - About: App version, privacy policy link, open source licenses
  - Each preference item: Label + current value + click handler
  - Theme changes apply immediately via recomposition
  **Must NOT do:** No syncing with server in settings; no telemetry opt-in/out (not implemented yet)
  **Parallelization:** Wave 4 | Blocked by: 4, 6 | Can parallelize with: 9, 10, 11, 13, 14
  **References:**
  - features/settings/build.gradle.kts:1-41
  - UserPreferencesRepository interface
  **Acceptance criteria:** `./gradlew :features:settings:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile, ViewModel test for theme toggle
  - Evidence: .omo/evidence/task-12-settings-compile.txt
  **Commit:** Y | `feat(settings): Add Settings feature — theme, dynamic color, biometric toggle, about section`

- [x] 13. **Create Backup feature — export/import JSON, WorkManager scheduling**
  **What to do:** Create files under `features/backup/src/main/java/com/notesapp/features/backup/`
  **BackupManager (manager/):**
  - `BackupManager.kt` — @Singleton class. Injected: NoteRepository, FolderRepository. Methods:
    - exportToJson(context: Context, notes: List<Note>, folders: List<Folder>): Result<Uri> — creates JSON file in Downloads, returns content URI
    - importFromJson(context: Context, uri: Uri): Result<Int> — reads JSON, inserts into DB, returns count
    - scheduleAutoBackup(context: Context, intervalHours: Int) — uses WorkManager PeriodicWorkRequest
    - cancelAutoBackup(context: Context) — cancels WorkManager
  - JSON format: { "version": 1, "exportedAt": timestamp, "notes": [...], "folders": [...] }
  **WorkManager:**
  - `BackupWorker.kt` — @HiltWorker class. doWork(): Result. Creates JSON backup, handles errors, returns success/failure.
  **BackupViewModel:**
  - Injected: UserPreferencesRepository, BackupManager
  - StateFlow: lastBackupTime, isExporting, isImporting, autoBackupEnabled, autoBackupInterval
  - Functions: exportNotes(), importNotes(uri), toggleAutoBackup(), setAutoBackupInterval(hours)
  **BackupScreen:**
  - Composable: Cards for each action:
    - Export notes: Shows last backup time, export button → creates file → shows snackbar with file path
    - Import notes: Import button → opens file picker for .json → shows progress
    - Auto-backup: Toggle switch, interval selector dropdown (daily/weekly/monthly)
  - Shows loading indicator during export/import
  - Error snackbar on failure
  **Must NOT do:** No cloud backup (Drive/Cloud); no backup encryption in v1; don't overwrite existing data without confirmation
  **Parallelization:** Wave 4 | Blocked by: 4, 6 | Can parallelize with: 9, 10, 11, 12, 14
  **References:**
  - features/backup/build.gradle.kts:1-41 (WorkManager dep)
  - Hilt-WorkManager integration
  - Android FileProvider for sharing files (optional, can use MediaStore)
  **Acceptance criteria:** `./gradlew :features:backup:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile, ViewModel test
  - Evidence: .omo/evidence/task-13-backup-compile.txt
  **Commit:** Y | `feat(backup): Add Backup feature — JSON export/import, WorkManager scheduling`

- [x] 14. **Create Reminders feature — set, edit, cancel reminders on notes**
  **What to do:** Create files under `features/reminders/src/main/java/com/notesapp/features/reminders/`
  **ReminderManager (manager/):**
  - `ReminderManager.kt` — @Singleton. Injected: ReminderRepository. Methods:
    - scheduleReminder(context: Context, reminder: Reminder) — uses AlarmManager.setExactAndAllowWhileIdle() + PendingIntent to BroadcastReceiver
    - cancelReminder(context: Context, reminderId: Long)
  - `ReminderReceiver.kt` — BroadcastReceiver. onReceive: show notification with notification channel "notes_reminders". Notification: title from note, content preview, tap opens note detail via PendingIntent to MainActivity.
  - `ReminderNotificationHelper.kt` — creates notification channel, builds notifications with NotificationCompat
  **RemindersViewModel:**
  - Injected: GetUpcomingRemindersUseCase, SetReminderUseCase, CancelReminderUseCase
  - StateFlow: reminders: List<Reminder>, isLoading
  - Functions: loadReminders(), cancelReminder(id), completeReminder(id)
  **ReminderListScreen (optional standalone, or integrated into NotesScreen):**
  - Composable: LazyColumn of reminder cards with note title, reminder time (relative format), complete checkbox, cancel button
  - Shows today's reminders first, then upcoming
  - EmptyState: "No reminders set"
  **Must NOT do:** Don't use JobScheduler (AlarmManager is appropriate for user-facing reminders); no background sync
  **Parallelization:** Wave 4 | Blocked by: 4, 6 | Can parallelize with: 9, 10, 11, 12, 13
  **References:**
  - features/reminders/build.gradle.kts (create if not exists)
  - AlarmManager + PendingIntent + BroadcastReceiver pattern
  - Notification channels (API 26+, minSdk 26)
  **Acceptance criteria:** `./gradlew :features:reminders:compileDebugKotlin` succeeds
  **QA scenarios:**
  - Happy: compile
  - Evidence: .omo/evidence/task-14-reminders-compile.txt
  **Commit:** Y | `feat(reminders): Add Reminders feature — AlarmManager scheduling, notifications, CRUD`

### WAVE 5: Integration, Testing, Polish

- [x] 15. **Feature integration — wire all modules together**
  **What to do:** Ensure all features are connected through the app module:
  1. Register all feature routes in NavGraph
  2. Add all feature bottom nav items in NotesScaffold
  3. Ensure Hilt sees all @HiltViewModel from feature modules (they're auto-discovered)
  4. Wire BackupScreen from Settings → navigateToBackup
  5. Wire Folder filter from NotesScreen → filter by selected folder
  6. Wire Search → NoteDetail navigation
  7. Wire Reminder notification tap → open NoteDetail
  8. Verify app/build.gradle.kts has all feature module dependencies
  9. Test that `./gradlew assembleDebug` succeeds end-to-end
  **Must NOT do:** No adding new features; this is integration only
  **Parallelization:** Wave 5 | Blocked by: 9, 10, 11, 12, 13, 14 | Blocks: 16, 17
  **Acceptance criteria:** `./gradlew assembleDebug` succeeds
  **QA scenarios:**
  - Happy: full debug build succeeds
  - Failure: `./gradlew assembleDebug` with --stacktrace for any errors
  - Evidence: .omo/evidence/task-15-integration.txt
  **Commit:** Y | `feat(integration): Wire all feature modules through navigation and app shell`

- [ ] 16. **Write unit tests for domain, data, and feature modules**
  **What to do:**
  **Domain tests (domain/src/test/):**
  - `SaveNoteUseCaseTest.kt` — Test: save valid note returns Success, save blank title returns Error
  - `GetNotesUseCaseTest.kt` — Test: returns notes from repository, maps through Flow
  - `DeleteNoteUseCaseTest.kt` — Test: calls repository.softDeleteNote
  - `CreateFolderUseCaseTest.kt` — Test: valid name creates, blank name fails
  - Mock repository with MockK
  - Use Turbine for Flow testing
  - Use kotlinx-coroutines-test for TestDispatcher
  **Data tests (data/src/test/):**
  - `NoteDaoTest.kt` — @RunWith(AndroidJUnit4) with in-memory Room. Insert note, query, verify fields. Search with FTS, verify results. Soft-delete, verify excluded from normal queries.
  - `FolderDaoTest.kt` — Insert folder, query, update, delete
  - `ReminderDaoTest.kt` — Insert reminder, query upcoming, update
  - `NoteRepositoryImplTest.kt` — Mock DAO, test mapping and repository methods
  - `UserPreferencesRepositoryImplTest.kt` — Mock DataStore, test preferences read/write
  **Feature tests (features/*/src/test/):**
  - `NotesViewModelTest.kt` — Mock GetNotesUseCase, test state updates; test search query debounce; test filter changes
  - `NoteDetailViewModelTest.kt` — Mock GetNoteByIdUseCase + SaveNoteUseCase; test load, save, auto-save debounce
  - `FoldersViewModelTest.kt` — Mock GetFoldersUseCase + CreateFolderUseCase; test CRUD state
  - `SearchViewModelTest.kt` — Mock SearchNotesUseCase; test debounced search
  - `SettingsViewModelTest.kt` — Mock UserPreferencesRepository; test theme toggle
  - `BackupViewModelTest.kt` — Mock BackupManager; test export/import flow
  - `RemindersViewModelTest.kt` — Mock ReminderRepository; test load/cancel
  **Must NOT do:** Don't mock what you don't own (e.g., Room) — use in-memory DB; no Android instrumentation tests yet (unit level); no UI tests in this todo
  **Parallelization:** Wave 5 | Blocked by: 15 | Blocks: 17
  **References:**
  - build.gradle.kts from each module (junit, mockk, turbine, coroutines-test)
  - Official Android testing docs
  **Acceptance criteria:** `./gradlew testDebugUnitTest` passes all tests
  **QA scenarios:**
  - Happy: `./gradlew testDebugUnitTest` — all green
  - Failure: check test reports at app/build/reports/tests/testDebugUnitTest/index.html
  - Evidence: .omo/evidence/task-16-unit-tests.txt
  **Commit:** Y | `test: Add unit tests for domain (use cases), data (DAOs, repositories), and all feature ViewModels`

- [ ] 17. **Write Compose UI tests for critical user flows**
  **What to do:** Create UI tests in `app/src/androidTest/java/com/notesapp/app/`
  **Setup:**
  - `TestNotesApplication.kt` — @HiltAndroidTest application for testing
  - `HiltTestActivity.kt` — simple host activity for Compose tests
  - Test rule: createComposeRule() with hilt
  **Test cases:**
  - `NotesListFlowTest.kt` — Launch app, verify notes screen loads, FAB is displayed, click FAB navigates to note detail
  - `NoteCreateAndEditTest.kt` — Create new note with title and content, verify it appears in list, click to open, edit content, verify update
  - `NoteDeleteTest.kt` — Create note, delete via menu, verify removed from list
  - `FolderManagementTest.kt` — Navigate to folders, create folder, verify visible, rename, delete
  - `SearchTest.kt` — Create notes with known content, navigate to search, type query, verify filtered results
  - `SettingsTest.kt` — Navigate to settings, toggle theme, verify theme changes (check surface color)
  - `NavigationTest.kt` — Test full navigation: Notes → Detail → Back → Folders → Settings → Back
  - `EmptyStateTest.kt` — With empty DB, verify empty state composable displayed with correct message
  **Pattern:**
  - Use `semantics` matchers: onNodeWithText, onNodeWithContentDescription, onNodeWithTag
  - Use `performClick`, `performTextInput`, `performScrollTo`
  - For ViewModel injection in tests: @HiltViewModel + inject into test
  **Must NOT do:** Don't test Compose internals; test user-visible behavior only; no flaky timeline-dependent tests (use TestDispatcher)
  **Parallelization:** Wave 5 | Blocked by: 15, 16 | Blocks: —
  **References:**
  - Compose UI testing framework
  - Hilt testing: @HiltAndroidTest
  **Acceptance criteria:** `./gradlew :app:connectedDebugAndroidTest` passes on emulator/device
  **QA scenarios:**
  - Happy: UI tests pass on emulator (API 26+)
  - Evidence: .omo/evidence/task-17-ui-tests.txt
  **Commit:** Y | `test: Add Compose UI tests for notes CRUD, folder management, search, settings, and navigation`

## Final verification wave
> Runs in parallel after ALL todos. ALL must APPROVE. Surface results and wait for the user's explicit okay before declaring complete.
- [ ] F1. **Plan compliance audit** — Verify all todos completed, no scope creep, no features outside Must-Have list
- [ ] F2. **Code quality review** — Run lint (`./gradlew lint`), verify no errors/warnings in production code
- [ ] F3. **Real manual QA** — Install APK on device/emulator, test critical flows: create note, edit, search, organize in folder, set reminder, export backup
- [ ] F4. **Scope fidelity** — Ensure no Must-Not-Have features implemented (no sync, no rich text, no AI)

## Commit strategy
- Each todo gets its own atomic commit with conventional commit format: `<type>(<scope>): <summary>`
- Types: fix (for scaffold), feat (for new features), test (for test-only), refactor (future)
- Branches: work on `main` directly (single-developer project)
- Messages are descriptive but concise, referencing the todo number
- No commits after final verification (squash if needed)

## Success criteria
1. `./gradlew assembleDebug` compiles without errors
2. `./gradlew testDebugUnitTest` passes all unit tests
3. `./gradlew :app:connectedDebugAndroidTest` passes all UI tests (on emulator API 26+)
4. App launches on device/emulator
5. User can: create note → edit content → search → organize in folder → set reminder → export backup → lock with biometric
6. Code follows Clean Architecture layers with no improper dependencies (e.g., ViewModel → DB directly)
7. No TODOs in production code
8. All resources have proper strings (no hardcoded UI text)
