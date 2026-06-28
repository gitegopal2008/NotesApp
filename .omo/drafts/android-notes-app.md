---
slug: android-notes-app
status: awaiting-approval
intent: unclear
pending-action: write .omo/plans/android-notes-app.md
approach: Phased multi-wave implementation — fix scaffold issues first, build core+domain+data layers, then feature modules, then advanced features, then polish/testing
---

# Draft: android-notes-app

## Components (topology ledger)
| id | outcome | status | evidence path |
|----|---------|--------|---------------|
| scaffold-fix | Fix build config, version catalog, missing files | active | NotesApp/settings.gradle.kts, gradle/libs.versions.toml |
| domain-layer | Entities, repository interfaces, use cases | active | NotesApp/domain/ |
| data-layer | Room DB, DataStore, repository impls | active | NotesApp/data/ |
| core-layer | DI, base classes, extensions, common utils | active | NotesApp/core/ |
| designsystem | M3 theme, typography, common components | active | NotesApp/designsystem/ |
| notes-feature | Note list, note detail/edit, FTS search | active | NotesApp/features/notes/ |
| folders-feature | Folder CRUD, nested folders, folder navigation | active | NotesApp/features/folders/ |
| search-feature | Global search, filters, results | active | NotesApp/features/search/ |
| settings-feature | Theme, backup, account, about | active | NotesApp/features/settings/ |
| backup-feature | Export/import notes as JSON/CSV, WorkManager | active | NotesApp/features/backup/ |
| reminders-feature | Alarm-based reminders for notes | active | NotesApp/features/reminders/ |
| security-module | Biometric lock, encryption-at-rest | active | NotesApp/security/ |
| sync-module | Cloud sync infrastructure (interface + pending) | deferred | NotesApp/sync/ |
| analytics-module | Event tracking abstraction | active | NotesApp/analytics/ |
| app-module | Navigation graph, Application class, DI setup | active | NotesApp/app/ |
| testing | Unit tests, Compose UI tests, Room tests | active | NotesApp/testing/ |

## Open assumptions (announced defaults)
| assumption | adopted default | rationale | reversible? |
|-----------|-----------------|-----------|-------------|
| Min SDK | 26 (Android 8.0) | Covers 95%+ of active devices, allows modern APIs | Yes — can raise later |
| Compose BOM | 2024.02.00 (as in project) | Already configured; update can be done separately | Yes |
| Navigation | Jetpack Navigation Compose | Official recommended, type-safe, deep link support | Low — rewriting navigation is expensive |
| DI framework | Hilt | Official Android DI, ViewModel injection, multi-module support | Low — switching DI is costly |
| Database | Room with FTS4 | Full-text search, compile-time SQL verification, Flow support | Low — switching DB is data-migration heavy |
| Async | Kotlin Coroutines + Flow | Official recommendation, structured concurrency, Lifecycle-aware | Low — pervasive across architecture |
| Testing | JUnit5 + MockK + Turbine | Industry standard for Kotlin, coroutine testing | Yes |
| Theme | Material 3 + Dynamic Color | Modern Android design language, includes dark/light/contrast | Yes |
| Encryption | Android Security Crypto + Biometric | EncryptedFile + master key, BiometricPrompt for auth | Low — involves user data |
| Cloud sync | Deferred to later phase | Not in v1; architecture must support adding it later | Yes — architectural support only |
| Backup | Room DB export + JSON, WorkManager schedule | Local backup to device/cloud Drive | Yes |

## Findings (cited - path:lines)
- Project scaffold exists with 16 modules defined — NotesApp/settings.gradle.kts:17-31
- **Zero Kotlin source files exist** — all directories are empty trees — NotesApp/core/src/main/java/com/ (empty), NotesApp/domain/src/main/java/ (empty), etc.
- Version catalog uses Kotlin 1.9.22, AGP 8.2.2, Compose BOM 2024.02.00 — somewhat outdated — NotesApp/gradle/libs.versions.toml
- `settings.gradle.kts` uses `dependencyResolution` (wrong API name — should be `dependencyResolutionManagement`) — NotesApp/settings.gradle.kts:9
- `:app` module has NO `build.gradle.kts` — only empty src/ directory — glob returned no files in app/
- `:features:notes:note` has its own build.gradle.kts but is NOT listed in settings.gradle.kts — NotesApp/features/notes/note/build.gradle.kts
- `:features:reminders` IS listed in settings.gradle.kts but has no build.gradle.kts — NotesApp/features/reminders/ (no build.gradle.kts found in glob)
- `features/notes/build.gradle.kts` uses both `compose = true` AND `kotlin.compose` plugin — redundant — dual configuration
- Version catalog names: libs.work.manager referenced in backup/build.gradle.kts but TOML defines `work-manager` (dash, not dot) — likely broken reference
- Compose compiler uses old `composeOptions { kotlinCompilerExtensionVersion }` pattern — not yet migrated to Kotlin 2.0 compose compiler plugin (but `kotlin.compose` plugin IS declared in root build.gradle.kts)
- `testing/` module listed in folder structure but no build.gradle.kts found — NotesApp/testing/ not in glob results

## Decisions (with rationale)
1. **Fix scaffold first** — Build config must compile before any code can be tested
2. **Domain-layer first** — Entities, repository interfaces, and use cases are dependency-free from Android framework and define the app's contract
3. **Data-layer second** — Room databases, DAOs, FTS tables, repository implementations depend on Domain interfaces
4. **Core-layer in parallel with domain** — DI modules, base classes, extensions, logging utilities
5. **App module last among foundation** — Navigation graph, Application class, Hilt entry point, Theme wiring
6. **Feature modules after foundation** — Each feature is independent and can be built/tested separately
7. **Advanced features (security, sync, analytics) deferred or parallel** — Security is separate module; sync is deferred; analytics is thin wrapper
8. **Use FTS4 for note content search** — Room supports it natively, no external dependency needed
9. **Single-Activity architecture** — One Activity, Compose handles all navigation
10. **Repository pattern with offline-first** — Repositories serve local data first, network sync is additive layer

## Scope IN
**v1 (this plan):**
- Fix all build configuration issues
- Domain layer: Note, Folder, Tag, Reminder entities; repository interfaces; use cases (CRUD, search, folders, reminders)
- Data layer: Room DB with FTS4, DAOs, converters; DataStore for preferences; repository implementations
- Core layer: Hilt DI modules, base classes, DateTime utils, Resource/Result sealed class, logging
- Design system: M3 theme (light/dark/dynamic), typography, reusable Compose components (TopBar, FAB, BottomSheet, Dialogs)
- App module: Application class, Hilt entry point, Navigation graph, MainActivity
- Notes feature: Note list screen (with search bar, folder filter, grid/list toggle), Note detail/edit screen (title, content, rich text toolbar, tags, reminders), FTS search within notes
- Folders feature: Folder list screen, create/rename/delete folders, nested folders (tree structure), folder selection dialog
- Search feature: Global search across notes (title + content), filter by folder/tags, recent searches
- Settings feature: Theme selection (system/light/dark/dynamic), toggle for dynamic color, about section
- Backup feature: Export all notes as JSON, import from JSON, schedule backups with WorkManager
- Reminders feature: Set/edit/remove reminders on notes, notification via AlarmManager + BroadcastReceiver
- Security module: App lock with biometric, encrypt note content, secure preferences
- Testing: Unit tests for domain (use cases, entities), data (DAOs, repositories), ViewModels, UI tests for critical flows

**Scope OUT (Must NOT have)**
- Cloud sync (architecture prepares for it with repository interfaces, but no network code)
- Rich text editor (uses plain text with Markdown-style formatting in v1)
- OCR, PDF import/export, voice notes, drawing — deferred to future
- AI features, collaboration, version history — deferred to future
- Widgets — not in v1
- No Compose for Desktop or other platforms — Android only
- No copying code from existing apps — all implementation original
- No fabricating APIs or inventing libraries — only use declared dependencies
- No TODOs in production code

## Open questions
*(None — all resolved through research and best-practice defaults. Intent is UNCLEAR, so defaults are adopted and surfaced for user veto.)*

## Approval gate
status: awaiting-approval
<!-- When exploration is exhausted and unknowns are answered, set status: awaiting-approval. -->
<!-- That durable record is the loop guard: on a later turn read it and resume at the gate instead of re-running exploration. -->
