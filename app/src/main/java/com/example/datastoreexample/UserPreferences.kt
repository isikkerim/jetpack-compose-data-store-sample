// Gerekli kütüphaneleri içe aktar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Jetpack DataStore kullanarak kullanıcı tercihlerini yönetmek için bir yardımcı sınıf.
 *
 * @param dataStore Tercihleri saklamak için kullanılan DataStore örneği.
 */
class UserPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        // Kullanıcı adını DataStore'da saklamak için anahtar
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    /**
     * Kullanıcı adını DataStore'a kaydeder.
     *
     * @param username Kaydedilecek kullanıcı adı.
     */
    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    /**
     * Kullanıcı adını DataStore'dan bir Flow olarak alır.
     *
     * @return Kaydedilmiş kullanıcı adını veya henüz ayarlanmadıysa null döner.
     */
    val usernameFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[USERNAME_KEY]
        }
}
