### README.md

# Kullanıcı Tercihleri Uygulaması (Kotlin & Jetpack Compose)

Bu proje, Jetpack Compose ve DataStore kullanarak kullanıcı tercihlerini saklamak ve göstermek için geliştirilmiştir. Uygulama, kullanıcıdan alınan bir kullanıcı adını **DataStore** ile kaydeder ve tekrar yükler.

---

## Özellikler

- Kullanıcı adını kaydetme ve geri yükleme.
- Jetpack Compose ile modern kullanıcı arayüzü.
- DataStore API'si ile kalıcı veri saklama.

---

## Gerekli Bağımlılıklar

Projenize aşağıdaki bağımlılıkları ekleyin:

```gradle
// DataStore için
implementation "androidx.datastore:datastore-preferences:1.0.0"
// Jetpack Compose için (örn. Material bileşenleri)
implementation "androidx.compose.material:material:1.4.3"
```

---

## Proje Yapısı

1. **UserPreferences Sınıfı**:
   DataStore üzerinden kullanıcı adını kaydetmek ve geri almak için yardımcı bir sınıf sağlar.

2. **MainActivity**:
   Kullanıcı arayüzünü oluşturur ve kullanıcı adı işlemlerini yönetir.

---

## Kullanım

1. Uygulamayı başlatın.
2. Kullanıcı adı girin ve **"Save Username"** butonuna tıklayın.
3. Kaydedilen kullanıcı adı ekranda görünecektir.

---

## Örnek Kod

**UserPreferences Sınıfı**:

```kotlin
class UserPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    val usernameFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }
}
```

**MainActivity**:

```kotlin
class MainActivity : ComponentActivity() {
    private val userPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserPreferencesScreen(userPreferences)
        }
    }
}
```

**UserPreferencesScreen**: Kullanıcıdan veri alır ve DataStore ile gösterir.

---

## Lisans

Bu proje açık kaynaklıdır ve herhangi bir lisans sınırlaması bulunmamaktadır.